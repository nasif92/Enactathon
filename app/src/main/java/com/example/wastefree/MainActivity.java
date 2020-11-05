package com.example.wastefree;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Parcelable;
import android.util.Log;
import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.Serializable;
import java.security.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Date;


public class MainActivity extends AppCompatActivity implements Serializable {
    ListView itemList;
    ArrayList<Item> itemDataList = new ArrayList<>();
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        this.itemList = findViewById(R.id.itemList);

        db = FirebaseFirestore.getInstance();

        final CollectionReference itemCollectionReference = db.collection("item");
        final ArrayAdapter itemAdapter = new CustomArrayAdapter(this, itemDataList);
        itemList.setAdapter(itemAdapter);

        FloatingActionButton fab = findViewById(R.id.addPostButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               Intent intent = new Intent(MainActivity.this, ActivityAddWasteItem.class);
               // intent.putExtra("USER_ID", userId);
                Date d = new Date();
                //intent.putExtra("DATE", d);
                Item item = new Item();
                item.setItemUploadDate(d);
                //intent.putExtra("Item", (Serializable) item);
                //intent.putExtra("EDIT","AddingMode");
                startActivity(intent);
            }
        });
//    public Item(String ItemId, String itemName, String quantity, String location) {
        itemCollectionReference.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                itemDataList.clear();
                for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
                    // Log.d("TEST", String.valueOf(doc.getData().get("Province")));
                    String itemId = (String) doc.getData().get("itemId");
                    String itemName = (String) doc.getData().get("itemName");
                   // String description = (String) doc.getData().get("quantity");
                    String location = (String) doc.getData().get("location");
                    itemDataList.add(new Item(itemId, itemName, "1300", "location"));
                }
                itemAdapter.notifyDataSetChanged();
            }
        });

    }
}