package com.example.wastefree;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firestore.v1.WriteResult;

import java.io.Serializable;
import java.util.ArrayList;

import java.util.Collections;

import java.util.HashMap;
import java.util.Map;

public class ActivityAddWasteItem extends AppCompatActivity implements Serializable, AdapterView.OnItemSelectedListener {
    String category;
    String desc;
    Integer rating;
    String image;

    private Context context;
    public static final int CAMERA_ACCESS = 1;
    public static final int RESULT_OK = -1;
    public static final int GALLERY_ACCESS = 9999;

    ImageView picItem;

    EditText itemName, quantity,location;
    Button saveButton;


    FirebaseFirestore db;
    


    @Override
    protected void onCreate(Bundle savedInstanceState) {



        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_activity_add_waste_item);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        itemName = findViewById(R.id.getCategory);

        saveButton = findViewById(R.id.saveButton);
        final EditText itemInput = findViewById(R.id.getCategory);
        final EditText descInput = findViewById(R.id.getDescription);
        final TextView userLoc = findViewById(R.id.location);
        final Spinner rating = findViewById(R.id.rating);
        final Button galleryLaunch = findViewById(R.id.gallery);
        final Button cameraLaunch = findViewById(R.id.camera);
        picItem = findViewById(R.id.itemPic);

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
                try {
                    startActivityForResult(camera, CAMERA_ACCESS);
                }catch (ActivityNotFoundException e){
                    Snackbar.make(v, "No camera detected", Snackbar.LENGTH_LONG)
                            .setAction("Try Again",null).show();
                }
            }
        });


        saveButton = findViewById(R.id.saveButton);
        quantity = findViewById(R.id.getDescription);
        location = findViewById(R.id.Location);


        db = FirebaseFirestore.getInstance();
        FirebaseFirestore.setLoggingEnabled(true);

        /**
          Getting the important information out of the other activity
         getting the item
         */
        //final Item item = (Item) getIntent().getSerializableExtra("Item");


        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();


                HashMap<String, Object> data = new HashMap<>();
                String Name = itemName.getText().toString();
                String loc = location.getText().toString();
                String quan = quantity.getText().toString();
                String itemID = String.valueOf(Timestamp.now().hashCode());
                int rate = rating.getSelectedItemPosition();
                Item item = new Item(Name, Name , quan,loc);
                item.setItemID(itemID);
                // location is song name
                data.put("itemName", Name);
                data.put("quantity", quan);
                data.put("location", loc);
                data.put("itemId", itemID);
                data.put("Rate", rate);
                db.collection("Items").document(itemID)
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


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode,requestCode,data);
        if (requestCode == CAMERA_ACCESS && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            picItem.setImageBitmap(imageBitmap);
        }
    }



//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        context = getApplicationContext();
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if(requestCode==CAMERA_ACCESS && resultCode == RESULT_OK)    {
//            Bitmap bitmap= (Bitmap) data.getExtras().get("data");
//            // todo: working on image compression
//            ByteArrayOutputStream baos=new ByteArrayOutputStream();
//            bitmap.compress(Bitmap.CompressFormat.PNG,100, baos);
//            byte [] b =baos.toByteArray();
//            String temp= Base64.encodeToString(b, Base64.DEFAULT);
//            image = temp;
//            picItem.setImageBitmap(bitmap);
//        }
//
//        else if(requestCode==GALLERY_ACCESS) {
//            try {
//                final Uri imageUri = data.getData();
//                final InputStream imageStream = getContentResolver().openInputStream(imageUri);
//                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
//                imageStream.close();
//
//                picItem.setImageBitmap(selectedImage);
//                ByteArrayOutputStream baos=new ByteArrayOutputStream();
//                selectedImage.compress(Bitmap.CompressFormat.JPEG,100, baos);
//                byte [] b =baos.toByteArray();
//                String temp=Base64.encodeToString(b, Base64.DEFAULT);
//                image = temp;
//
//            } catch (FileNotFoundException e) {
//                e.printStackTrace();
//                Toast.makeText(context, "Something went wrong", Toast.LENGTH_LONG).show();
//            } catch (IOException e){
//                e.printStackTrace();
//            }
//        }
//
//        else {
//            Toast.makeText(context, "You haven't picked Image",Toast.LENGTH_LONG).show();
//        }
//    }







}