package textanalysis.matching;

import java.util.ArrayList;
import java.util.List;

public final class RabinKarpMatcher implements Matcher {
    private static final long BASE = 256;
    private static final long MOD = 1_000_000_007L;
    private long comparisons;
    private long spuriousHits;

    @Override
    public List<Integer> search(String text, String pattern) {
        comparisons = 0;
        spuriousHits = 0;
        List<Integer> res = new ArrayList<>();
        int n = text.length(), m = pattern.length();
        if (m == 0 || m > n) return res;

        long high = 1;
        for (int i = 0; i < m - 1; i++) high = high * BASE % MOD;
        long hp = 0, ht = 0;
        for (int i = 0; i < m; i++) {
            hp = (hp * BASE + pattern.charAt(i)) % MOD;
            ht = (ht * BASE + text.charAt(i)) % MOD;
        }
        for (int s = 0; s <= n - m; s++) {
            if (hp == ht) {
                int k = 0;
                while (k < m) {
                    comparisons++;
                    if (text.charAt(s + k) != pattern.charAt(k)) break;
                    k++;
                }
                if (k == m) res.add(s); else spuriousHits++;
            }
            if (s < n - m) {
                ht = (ht - text.charAt(s) * high % MOD + MOD) % MOD;
                ht = (ht * BASE + text.charAt(s + m)) % MOD;
            }
        }
        return res;
    }

    public long spuriousHits() { return spuriousHits; }
    @Override public long comparisons() { return comparisons; }
    @Override public String name() { return "Rabin-Karp"; }
}
