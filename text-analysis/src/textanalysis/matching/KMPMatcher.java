package textanalysis.matching;

import java.util.ArrayList;
import java.util.List;

public final class KMPMatcher implements Matcher {
    private long comparisons;


    static int[] buildLps(String p) {
        int m = p.length();
        int[] lps = new int[m];
        int len = 0;
        for (int i = 1; i < m; ) {
            if (p.charAt(i) == p.charAt(len)) {
                lps[i++] = ++len;
            } else if (len > 0) {
                len = lps[len - 1];
            } else {
                lps[i++] = 0;
            }
        }
        return lps;
    }

    @Override
    public List<Integer> search(String text, String pattern) {
        comparisons = 0;
        List<Integer> res = new ArrayList<>();
        int n = text.length(), m = pattern.length();
        if (m == 0 || m > n) return res;
        int[] lps = buildLps(pattern);
        int j = 0;
        for (int i = 0; i < n; i++) {
            while (true) {
                comparisons++;
                if (text.charAt(i) == pattern.charAt(j)) { j++; break; }
                if (j == 0) break;
                j = lps[j - 1];
            }
            if (j == m) {
                res.add(i - m + 1);
                j = lps[j - 1];
            }
        }
        return res;
    }

    @Override public long comparisons() { return comparisons; }
    @Override public String name() { return "KMP"; }
}
