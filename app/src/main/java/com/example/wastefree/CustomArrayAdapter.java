package com.example.wastefree;

import android.content.Context;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomArrayAdapter extends ArrayAdapter<Item> {

    private ArrayList<Item> items;
    private Context context;

    public CustomArrayAdapter(Context context, ArrayList<Item> items){
        super(context,0, items);
        this.items = items;
        this.context = context;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
//        return super.getView(position, convertView, parent);
        View view = convertView;

        if(view == null){
            view = LayoutInflater.from(context).inflate(R.layout.activity_custom_array_adapter, parent,false);
        }

        Item item = items.get(position);

        TextView itemName = view.findViewById(R.id.itemname);
        TextView location = view.findViewById(R.id.location);
        TextView quantity = view.findViewById(R.id.amount);

        itemName.setText(item.getCategory());
        location.setText(item.getLocation());
        quantity.setText(item.getItemDescription());
        return view;

    }
}