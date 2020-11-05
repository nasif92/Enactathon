package com.example.wastefree;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.FirebaseFirestore;

import androidx.annotation.NonNull;
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

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.io.Serializable;
import java.util.HashMap;

public class ActivityAddWasteItem extends AppCompatActivity implements Serializable, AdapterView.OnItemSelectedListener {
    String category;
    String desc;
    Integer rating;



    EditText itemName;
    FloatingActionButton saveButton;

    FirebaseFirestore db;
    


    @Override
    protected void onCreate(Bundle savedInstanceState) {



        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_waste_item);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        itemName = findViewById(R.id.getCategory);
        saveButton = findViewById(R.id.fab);
        final EditText itemInput = findViewById(R.id.getCategory);
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

        db = FirebaseFirestore.getInstance();
        FirebaseFirestore.setLoggingEnabled(true);

        /**
          Getting the important information out of the other activity
         getting the item
         */
        final Item item = (Item) getIntent().getSerializableExtra("Item");

        FloatingActionButton fab = findViewById(R.id.fab);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();


                HashMap<String, Object> data = new HashMap<>();
                String Name = itemName.getText().toString();
                String itemID = String.valueOf(Timestamp.now().hashCode());
                Item item = new Item(Name, "otherStuff" , "songName","Alberta");
                item.setItemID(itemID);
                // location is song name
                data.put("itemName", Name);
                data.put("quantity", 20);
                data.put("location", "songName");
                data.put("itemId", itemID);
                db.collection("item").document(itemID)
                        .set(data)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.d("test", "DocumentSnapshot successfully written!");
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w("test", "Error writing document", e);
                            }
                        });
                Intent MainActivity = new Intent(ActivityAddWasteItem.this, MainActivity.class);
                startActivity(MainActivity);
                finish();
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