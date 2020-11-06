package com.example.wastefree;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Welcome_screen extends AppCompatActivity {

    public static String postCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_screen);

        final EditText postCodeInput = findViewById(R.id.postalCode);
        final Button okButton = findViewById(R.id.confirm);
        postCode = postCodeInput.getText().toString();

        final String postTest = "([A-Za-z][0-9][A-Za-z])([\\-]|[\\s])([0-9][A-Za-z][0-9])";
        Pattern postPattern = Pattern.compile(postTest);
        final Matcher postMatch = postPattern.matcher(postCode);
        okButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                    Intent main = new Intent(getApplicationContext(), MainActivity.class);
                    Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), Welcome_screen.postCode, Snackbar.LENGTH_LONG);
                    snackbar.show();
                    startActivity(main);

            }

        });


    }
}