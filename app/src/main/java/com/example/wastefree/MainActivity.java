package com.example.wastefree;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;


public class MainActivity extends AppCompatActivity implements Serializable {
    ListView itemList;
    ArrayList<Item> itemDataList = new ArrayList<>();
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final String TAG = "Sample";

        setContentView(R.layout.activity_main);
        //Toolbar toolbar = findViewById(R.id.toolbar);
        itemList = findViewById(R.id.itemList);

        db = FirebaseFirestore.getInstance();



        final CollectionReference itemCollectionReference = db.collection("Items");
        final ArrayAdapter itemAdapter = new CustomArrayAdapter(this, itemDataList);
        itemList.setAdapter(itemAdapter);

        FloatingActionButton fab = findViewById(R.id.addPostButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               Intent intent = new Intent(MainActivity.this, ActivityAddWasteItem.class);
                Date d = new Date();
                Item item = new Item();
                item.setItemUploadDate(d);
                startActivity(intent);
            }
        });


        itemCollectionReference.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                itemDataList.clear();
                if (e!=null){
                    Log.d(TAG,"Error:"+e.getMessage());
                }
                else {
                    for (QueryDocumentSnapshot doc : queryDocumentSnapshots){
                        Log.d(TAG, String.valueOf(doc.getData().get("itemId")));
                        String itemId = (String) doc.getData().get("itemId");
                        String itemName = (String) doc.getData().get("itemName");
                        int rate =Integer.valueOf(doc.getData().get("Rate").toString());
                        String location = (String) doc.getData().get("location");
                        String quantity = (String) doc.getData().get("quantity");
                        //     public Item(String ItemId, String itemName, String quantity, int rating,String location) {
                        Item item = new Item(itemId, itemName, quantity, rate, location);

                        itemDataList.add(item);

                    }
                    itemAdapter.notifyDataSetChanged();

                }

            }
        });

        itemList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                view.setSelected(true);
                final int selectedItem = position;
                final Item item = itemDataList.get(selectedItem);

                new AlertDialog.Builder(MainActivity.this)
                        .setIcon(android.R.drawable.ic_delete)
                        .setTitle("Are you sure")
                        .setMessage("Would you like to delete this item?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                db.collection("Items").document(item.getItemID()).delete()
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                Log.d("Okay", "DocumentSnapshot sucessfully deleted");
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Log.w("Error", "Error deleting song");
                                            }
                                        });
                                itemDataList.remove(selectedItem);
                                itemAdapter.notifyDataSetChanged();
                            }
                        })
                        .setNegativeButton("No", null)
                        .show();
                return false;
            }
        });





    }
}