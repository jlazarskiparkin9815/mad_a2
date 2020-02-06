package com.example.tripplanner;

import android.view.View;

public class UIManager {

    // Converts dp to pixels so that DP can be programmatically assigned to a View element.
    // Without this method, you can only use pixels
    public static int calcDP(View view, int dp) {
        float scale = view.getContext().getResources().getDisplayMetrics().density;
        return (int)(dp * scale + 0.5f);
    }
}
