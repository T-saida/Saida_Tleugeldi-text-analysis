package textanalysis.matching;

import java.util.ArrayList;
import java.util.List;

public final class NaiveMatcher implements Matcher {
    private long comparisons;

    @Override
    public List<Integer> search(String text, String pattern) {
        comparisons = 0;
        List<Integer> res = new ArrayList<>();
        int n = text.length(), m = pattern.length();
        if (m == 0 || m > n) return res;
        for (int s = 0; s <= n - m; s++) {
            int k = 0;
            while (k < m) {
                comparisons++;
                if (text.charAt(s + k) != pattern.charAt(k)) break;
                k++;
            }
            if (k == m) res.add(s);
        }
        return res;
    }

    @Override public long comparisons() { return comparisons; }
    @Override public String name() { return "Naive"; }
}
