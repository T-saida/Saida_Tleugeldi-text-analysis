package textanalysis.bench;

import textanalysis.dp.EditDistance;
import textanalysis.dp.LCS;
import textanalysis.matching.*;
import textanalysis.suffix.Kasai;
import textanalysis.suffix.SuffixArray;

import java.io.PrintWriter;
import java.util.List;
import java.util.Random;
import java.util.function.Supplier;


public class Benchmark {
    static final String ALPHABET = "abcd";
    static final long SEED = 42;
    static final int RUNS = 5;
    static volatile long sink;

    static String random(Random r, int n) {
        char[] c = new char[n];
        for (int i = 0; i < n; i++) c[i] = ALPHABET.charAt(r.nextInt(ALPHABET.length()));
        return new String(c);
    }

    static final int WARMUP = 30;


    static double timeMs(Runnable task) {
        long w0 = System.nanoTime();
        for (int i = 0; i < WARMUP || System.nanoTime() - w0 < 300_000_000L; i++) task.run();
        long total = 0;
        for (int i = 0; i < RUNS; i++) {
            long t0 = System.nanoTime();
            task.run();
            total += System.nanoTime() - t0;
        }
        return total / (double) RUNS / 1e6;
    }

    public static void main(String[] args) throws Exception {
        Random rnd = new Random(SEED);
        try (PrintWriter out = new PrintWriter("results/results.csv")) {
            out.println("experiment,algorithm,size,case,avg_ms,comparisons");

            for (int n : new int[]{100, 500, 1000}) {
                String a = random(rnd, n), b = random(rnd, n);
                out.printf("dp,LCS,%d,random,%.4f,%d%n", n, timeMs(() -> sink += LCS.length(a, b)), (long) n * n);
                out.printf("dp,EditDistance,%d,random,%.4f,%d%n", n, timeMs(() -> sink += EditDistance.distance(a, b)), (long) n * n);
            }

            int m = 100;
            for (int n : new int[]{10_000, 50_000, 100_000}) {
                String text = random(rnd, n);
                int pos = n / 2;
                String present = text.substring(pos, pos + m);
                String absent = absentPattern(text, m, rnd);
                runMatch(out, "match", "random", text, present, "present", n);
                runMatch(out, "match", "random", text, absent, "absent", n);
                // adversarial case for Naive: a^n with a^(m-1)b
                String worstText = "a".repeat(n), worstPat = "a".repeat(m - 1) + "b";
                runMatch(out, "match_worst", "a^n", worstText, worstPat, "absent", n);
            }

            for (int n : new int[]{1000, 5000, 10_000}) {
                String s = random(rnd, n);
                int[] sa = SuffixArray.naive(s);
                int[] lcp = new int[1];
                out.printf("kasai,Kasai,%d,random,%.4f,0%n", n, timeMs(() -> { int[] l = Kasai.buildLcp(s, sa); sink += l[n - 1]; }));
            }
        }
        System.out.println("Wrote results/results.csv");
    }

    static String absentPattern(String text, int m, Random r) {
        while (true) {
            String p = random(r, m);
            if (new KMPMatcher().search(text, p).isEmpty()) return p;
        }
    }

    static void runMatch(PrintWriter out, String exp, String kind, String text, String pat, String which, int n) {
        Matcher[] ms = { new NaiveMatcher(), new KMPMatcher(), new RabinKarpMatcher() };
        List<Integer> ref = ms[0].search(text, pat);
        for (Matcher mt : ms) {
            if (!mt.search(text, pat).equals(ref)) throw new IllegalStateException("algorithms disagree");
            long cmp = mt.comparisons();
            double ms_ = timeMs(() -> sink += mt.search(text, pat).size());
            out.printf("%s,%s,%d,%s,%.4f,%d%n", exp, mt.name(), n, which, ms_, cmp);
        }
    }
}
