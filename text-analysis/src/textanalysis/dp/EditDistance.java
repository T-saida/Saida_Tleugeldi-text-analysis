package textanalysis.dp;


public final class EditDistance {
    private EditDistance() {}


    public static int[][] table(String a, String b) {
        int n = a.length(), m = b.length();
        int[][] dp = new int[n + 1][m + 1];
        for (int i = 0; i <= n; i++) dp[i][0] = i;
        for (int j = 0; j <= m; j++) dp[0][j] = j;
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {
                int sub = dp[i - 1][j - 1] + (a.charAt(i - 1) == b.charAt(j - 1) ? 0 : 1);
                int del = dp[i - 1][j] + 1;
                int ins = dp[i][j - 1] + 1;
                dp[i][j] = Math.min(sub, Math.min(del, ins));
            }
        }
        return dp;
    }

    public static int distance(String a, String b) {
        return table(a, b)[a.length()][b.length()];
    }
}
