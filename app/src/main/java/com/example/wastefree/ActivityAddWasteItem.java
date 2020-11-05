package com.example.wastefree;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Locale;

public class ActivityAddWasteItem extends AppCompatActivity implements Serializable, AdapterView.OnItemSelectedListener {
    String category;
    String desc;
    Integer rating;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_waste_item);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final EditText descInput = findViewById(R.id.getDescription);
        final TextView userLoc = findViewById(R.id.location);
        final Spinner rating = findViewById(R.id.rating);
        final Button galleryLaunch = findViewById(R.id.gallery);
        final Button cameraLaunch = findViewById(R.id.camera);

        ArrayList<Integer> ratings = new ArrayList<>();
        ratings.add(1);
        ratings.add(2);
        ratings.add(3);
        ratings.add(4);
        ratings.add(5);

        ArrayAdapter ratelist = new ArrayAdapter(this,android.R.layout.simple_spinner_item,ratings);
        rating.setAdapter(ratelist);
        rating.setOnItemSelectedListener(this);


        descInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                desc = descInput.getText().toString();
            }
        });

        cameraLaunch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                try {
//                    startActivityForResult(camera, REQUEST_IMAGE_CAPTURE);
//                }
            }
        });

        /**
          Getting the important information out of the other activity
         getting the item
         */
        final Item item = (Item) getIntent().getSerializableExtra("Item");

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });




    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        rating = (Integer) parent.getItemAtPosition(position);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}