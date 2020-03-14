/*
    FILE             : UIManager
    PROJECT          : PROG3150 - Assignment 1
    PROGRAMMER       : Conor Barr, Eric Emerson, Jack Parkinson, Maxim Mikheev, Rick Bloemert
    FIRST VERSION    : 2020-02-02
    DESCRIPTION      :
        This class allows dynamically allocating UI elements. Methods are provided for creating
        the following elements: LinearLayout, EditText, and Spinner.
*/

package com.example.tripplanner;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class UIManager {
    final static int kEditText_Width = 200; // The width of EditText elements

    /*
        FUNCTION: calcDP()
        DESCRIPTION: Converts dp to pixels. This is needed so that
                     width/height can be programmatically assigned
                     to a View element using DP.
        PARAMETERS: View view: The element that's having it's width/height changed
                    int dp: The dp value
        RETURNS: int: The dp values that's been converted to pixels
    */
    public static int calcDP(View view, int dp) {
        float scale = view.getContext().getResources().getDisplayMetrics().density;
        return (int)(dp * scale + 0.5f);
    }

    /*
        FUNCTION: createLinearLayout()
        DESCRIPTION: Creates a horizontal LinearLayout.
        PARAMETERS: AppCompatActivity: The Activity that the LinearLayout is being added to
        RETURNS: LinearLayout: The LinearLayout that was created
    */
    public static LinearLayout createLinearLayout(AppCompatActivity activity) {
        LinearLayout newLayout = new LinearLayout(activity);

        // Add attributes to the LinearLayout
        newLayout.setOrientation(LinearLayout.HORIZONTAL);
        newLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));

        return newLayout;
    }


    /*
        FUNCTION: createEditText()
        DESCRIPTION: Creates a EditText UI element.
        PARAMETERS: AppCompatActivity: The Activity that the EditText is being added to
        RETURNS: EditText: The EditText that was created
    */
    public static EditText createEditText(AppCompatActivity activity) {
        EditText newEditText = new EditText(activity);

        // Add attributes to the EditText
        newEditText.setLayoutParams(new LinearLayout.LayoutParams(calcDP(newEditText, kEditText_Width),
                                                                  ViewGroup.LayoutParams.WRAP_CONTENT));
        newEditText.setHint(R.string.dt_msg);

        return newEditText;
    }


    /*
        FUNCTION: createSpinner()
        DESCRIPTION: Creates a Spinner (drop-down list) UI element.
        PARAMETERS: AppCompatActivity: The Activity that the Spinner is being added to
        RETURNS: Spinner: The Spinner that was created
    */
    public static Spinner createSpinner(AppCompatActivity activity) {
        // Create the Spinner adapter
        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(activity, R.array.dt_spinner,
                                                                                    android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Create the Spinner and assign the adapter to the Spinner
        Spinner spinner = new Spinner(activity);
        spinner.setAdapter(spinnerAdapter);

        return spinner;
    }

    public static Button createButton(AppCompatActivity activity){
        Button newButton = new Button(activity);
        newButton.setText("X");

        return newButton;
    }


    public static void navigateAndSendCode(Activity source, Class destination, String key, int code) {
        Intent intent = new Intent(source, destination);
        intent.putExtra(key, code);
        source.startActivity(intent);
    }

    public static int getNavigationCode(Activity a, String key) {
        int code = 0;

        try {
            code = a.getIntent().getExtras().getInt(key);
        }
        catch (NullPointerException e) {
            // Log
        }

        return code;
    }
}
