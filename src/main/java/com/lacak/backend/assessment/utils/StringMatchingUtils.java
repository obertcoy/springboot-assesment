package com.lacak.backend.assessment.utils;

import com.lacak.backend.assessment.enums.ScoreTypeEnum;

public class StringMatchingUtils {

    private StringMatchingUtils() {
    }

    public static double calculateLevenshteinDistance(String query, String target, ScoreTypeEnum scoreTypeEnum) {

        int lenStr1 = query.length();
        int lenStr2 = target.length();

        int[][] dp = new int[lenStr1 + 1][lenStr2 + 1];

        for (int i = 0; i <= lenStr1; i++) {
            for (int j = 0; j <= lenStr2; j++) {

                if (i == 0) {
                    dp[i][j] = j;
                } else if (j == 0) {
                    dp[i][j] = i;
                } else {
                    int cost = (query.charAt(i - 1) == target.charAt(j - 1)) ? 0 : 1;
                    dp[i][j] = Math.min(Math.min(dp[i - 1][j] + 1, dp[i][j - 1] + 1), dp[i - 1][j - 1] + cost);
                }

            }
        }

        double distance = dp[lenStr1][lenStr2];

        switch (scoreTypeEnum) {
            case ScoreTypeEnum.RAW:
                
                break;
        
            case ScoreTypeEnum.NORMALIZED:
              
                distance = 1.0 - ((double) distance / Math.max(query.length(), target.length()));

                break;
        }

        return distance;
    }

}
