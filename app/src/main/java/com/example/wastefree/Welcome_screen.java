package com.example.wastefree;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.snackbar.Snackbar;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.view.View.OnKeyListener;
import android.view.View;
import android.view.KeyEvent;

public class Welcome_screen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_screen);
        final EditText postCodeInput = findViewById(R.id.editTextTextPostalAddress);
        final Button okButton = findViewById(R.id.confirm);
        postCodeInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String postCode = postCodeInput.getText().toString();
                final String postTest = "([A-Za-z][0-9][A-Za-z])([\\-]|[\\s])([0-9][A-Za-z][0-9])";
                Pattern postPattern = Pattern.compile(postTest);
                Matcher postMatch = postPattern.matcher(postCode);
                if((postMatch.matches()) & (postCode.length()==7)) {

                    postCodeInput.setOnKeyListener(new OnKeyListener() {
                        public boolean onKey(View view, int keyCode, KeyEvent keyevent) {
                            //If the keyevent is a key-down event on the "enter" button
                            if ((keyevent.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                                Intent main = new Intent(getApplicationContext(), MainActivity.class);
                                main.putExtra("postCode",postCode);
                                startActivity(main);
                                return true;
                            }
                            return false;
                        }
                    });

                    okButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent main = new Intent(getApplicationContext(), MainActivity.class);
                            main.putExtra("postCode",postCode);
                            startActivity(main);
                        }
                    });
                }
                else{

                    postCodeInput.setOnKeyListener(new OnKeyListener() {
                        public boolean onKey(View view, int keyCode, KeyEvent keyevent) {
                            //If the keyevent is a key-down event on the "enter" button
                            if ((keyevent.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                                Snackbar.make(view, "Invalid Postal Code", Snackbar.LENGTH_LONG)
                                        .setAction("Try Again",null).show();
                                return true;
                            }
                            return false;
                        }
                    });

                    okButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Snackbar.make(v, "Invalid Postal Code", Snackbar.LENGTH_LONG)
                                    .setAction("Try Again",null).show();
                        }
                    });
                }
            }
        });
    }
}