package com.example.tripplanner;

import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

public class UIManager {
    // Converts dp to pixels so that DP can be programmatically assigned to a View element.
    // Without this method, you can only use pixels
    public static int calcDP(View view, int dp) {
        float scale = view.getContext().getResources().getDisplayMetrics().density;
        return (int)(dp * scale + 0.5f);
    }

    // Creates a LinearLayout
    public static LinearLayout createLinearLayout(AppCompatActivity activity) {
        LinearLayout newLayout = new LinearLayout(activity);

        // Add attributes to the LinearLayout
        newLayout.setOrientation(LinearLayout.HORIZONTAL);
        newLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));

        return newLayout;
    }

    // Creates an EditText
    public static EditText createEditText(AppCompatActivity activity) {
        EditText newEditText = new EditText(activity);

        // Add attributes to the EditText
        final int kEditText_Width = 250;
        newEditText.setLayoutParams(new LinearLayout.LayoutParams(calcDP(newEditText, kEditText_Width),
                                                                  ViewGroup.LayoutParams.WRAP_CONTENT));
        newEditText.setText(R.string.dt_msg);

        return newEditText;
    }
}
