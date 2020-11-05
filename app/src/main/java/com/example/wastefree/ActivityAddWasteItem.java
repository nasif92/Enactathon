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

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.io.Serializable;
import java.util.HashMap;

public class ActivityAddWasteItem extends AppCompatActivity implements Serializable {

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
}