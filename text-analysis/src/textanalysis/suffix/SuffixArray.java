package textanalysis.suffix;

import java.util.Arrays;

public final class SuffixArray {
    private SuffixArray() {}

    public static int[] naive(String s) {
        Integer[] idx = new Integer[s.length()];
        for (int i = 0; i < idx.length; i++) idx[i] = i;
        Arrays.sort(idx, (x, y) -> s.substring(x).compareTo(s.substring(y)));
        int[] sa = new int[idx.length];
        for (int i = 0; i < sa.length; i++) sa[i] = idx[i];
        return sa;
    }
}
