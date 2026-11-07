package textanalysis;

import textanalysis.dp.EditDistance;
import textanalysis.dp.LCS;
import textanalysis.matching.*;
import textanalysis.suffix.Kasai;
import textanalysis.suffix.SuffixArray;

import java.util.Arrays;
import java.util.List;


public class AllTests {
    static int passed = 0, failed = 0;

    static void check(String name, Object expected, Object actual) {
        if (expected.equals(actual)) { passed++; }
        else { failed++; System.out.println("FAIL " + name + ": expected " + expected + " but got " + actual); }
    }

    static void matchAll(String name, String text, String pat, List<Integer> expected) {
        Matcher[] ms = { new NaiveMatcher(), new KMPMatcher(), new RabinKarpMatcher() };
        for (Matcher m : ms) check(name + " [" + m.name() + "]", expected, m.search(text, pat));
    }

    public static void main(String[] args) {

        check("LCS identical", 5, LCS.length("hello", "hello"));
        check("LCS different", 0, LCS.length("abc", "xyz"));
        check("LCS empty", 0, LCS.length("", "abc"));
        check("LCS both empty", 0, LCS.length("", ""));
        check("LCS classic", 4, LCS.length("ABCBDAB", "BDCABA"));
        check("LCS subsequence len", 4, LCS.subsequence("ABCBDAB", "BDCABA").length());


        check("ED identical", 0, EditDistance.distance("kitten", "kitten"));
        check("ED one empty", 3, EditDistance.distance("", "abc"));
        check("ED one empty 2", 3, EditDistance.distance("abc", ""));
        check("ED substitution", 1, EditDistance.distance("cat", "cut"));
        check("ED insertion", 1, EditDistance.distance("cat", "cart"));
        check("ED deletion", 1, EditDistance.distance("cart", "cat"));
        check("ED kitten/sitting", 3, EditDistance.distance("kitten", "sitting"));

        matchAll("begin", "abcdef", "abc", List.of(0));
        matchAll("middle", "xxabcxx", "abc", List.of(2));
        matchAll("end", "xxxabc", "abc", List.of(3));
        matchAll("multiple", "abcabcabc", "abc", List.of(0, 3, 6));
        matchAll("overlap", "aaaa", "aa", List.of(0, 1, 2));
        matchAll("absent", "abcdef", "xyz", List.of());
        matchAll("longer than text", "abc", "abcd", List.of());
        matchAll("empty pattern", "abc", "", List.of());
        matchAll("empty text", "", "a", List.of());
        matchAll("whole text", "abc", "abc", List.of(0));
        matchAll("kmp classic", "ABABDABACDABABCABAB", "ABABCABAB", List.of(10));


        java.util.Random r = new java.util.Random(1);
        for (int t = 0; t < 300; t++) {
            String text = rand(r, 1 + r.nextInt(60)), pat = rand(r, 1 + r.nextInt(4));
            List<Integer> a = new NaiveMatcher().search(text, pat);
            check("random KMP " + t, a, new KMPMatcher().search(text, pat));
            check("random RK " + t, a, new RabinKarpMatcher().search(text, pat));
        }


        String s = "banana";
        int[] sa = {5, 3, 1, 0, 4, 2};
        check("SA banana", Arrays.toString(sa), Arrays.toString(SuffixArray.naive(s)));
        check("LCP banana", Arrays.toString(new int[]{0, 1, 3, 0, 0, 2}), Arrays.toString(Kasai.buildLcp(s, sa)));

        for (int t = 0; t < 100; t++) {
            String x = rand(r, 1 + r.nextInt(40));
            int[] sax = SuffixArray.naive(x);
            int[] exp = new int[x.length()];
            for (int i = 1; i < x.length(); i++) exp[i] = lcpBrute(x.substring(sax[i - 1]), x.substring(sax[i]));
            check("Kasai random " + t, Arrays.toString(exp), Arrays.toString(Kasai.buildLcp(x, sax)));
        }

        System.out.println("passed=" + passed + " failed=" + failed);
        if (failed > 0) System.exit(1);
    }

    static String rand(java.util.Random r, int n) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n; i++) sb.append((char) ('a' + r.nextInt(3)));
        return sb.toString();
    }

    static int lcpBrute(String a, String b) {
        int k = 0;
        while (k < a.length() && k < b.length() && a.charAt(k) == b.charAt(k)) k++;
        return k;
    }
}
