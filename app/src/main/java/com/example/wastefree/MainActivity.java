package com.example.wastefree;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Parcelable;
import android.util.Log;
import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
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
    FirebaseFirestore db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final String TAG = "Sample";

        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
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

//        itemCollectionReference.addSnapshotListener(new EventListener<QuerySnapshot>() {
//            @Override
//            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
//                itemDataList.clear();
//                for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
//                    // Log.d("TEST", String.valueOf(doc.getData().get("Province")));
//                    String itemId = (String) doc.getData().get("itemId");
//                    String itemName = (String) doc.getData().get("itemName");
//                   // String description = (String) doc.getData().get("quantity");
//                    String location = (String) doc.getData().get("location");
//                    itemDataList.add(new Item(itemId, itemName, "1300", "location"));
//                }
//                itemAdapter.notifyDataSetChanged();
//            }
//        });

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
                        //int rate = (int) doc.getData().get("Rate");
                        Item item = new Item(itemId, itemName, "100", "SDAf");
                        item.setRating(5);
                        itemDataList.add(item);
                        itemAdapter.notifyDataSetChanged();

                    }
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
                        .setMessage("Would you like to delete this song?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                db.collection("Videos").document(item.getItemID()).delete()
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