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
        setSupportActionBar(toolbar);
        this.itemList = findViewById(R.id.itemList);

        db = FirebaseFirestore.getInstance();

        final CollectionReference itemCollectionReference = db.collection("item");
        final ArrayAdapter itemAdapter = new CustomArrayAdapter(this, itemDataList);
        itemList.setAdapter(itemAdapter);
        itemAdapter.add(new Item("sadf",
                "ASdf","sdaf"));
        itemAdapter.add(new Item("sadf",
                "ASdf","sdaf"));
        itemAdapter.add(new Item("sadf",
                "ASdf","sdaf"));
        FloatingActionButton fab = findViewById(R.id.addPostButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               // Intent intent = new Intent(MainActivity.this, ActivityAddWasteItem.class);
               // intent.putExtra("USER_ID", userId);
                Date d = new Date();
                //intent.putExtra("DATE", d);
                Item item = new Item();
                item.setItemUploadDate(d);
                //intent.putExtra("Item", (Serializable) item);
                //intent.putExtra("EDIT","AddingMode");
                //startActivity(intent);
            }
        });

        itemCollectionReference.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
               // itemDataList.clear();
                for (QueryDocumentSnapshot doc : value) {
                    Log.d("TEST", String.valueOf(doc.getData().get("itemName")));
                    String itemName = (String) doc.getData().get("itemName");
                    String quantity = (String) doc.getData().get("quantity");
                    String location = (String) doc.getData().get("location");
                    itemDataList.add(new Item(itemName, quantity, location));
                    itemAdapter.add((new Item(itemName, quantity, location)));
                }
                itemAdapter.notifyDataSetChanged();
            }
        });
        Log.d("TEST", String.valueOf(itemAdapter.getItem(1)));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}