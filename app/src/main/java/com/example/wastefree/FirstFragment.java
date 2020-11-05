package com.example.wastefree;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import java.util.ArrayList;
import java.util.Date;

public class FirstFragment extends Fragment {

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_first, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ListView list = view.findViewById(R.id.list_view);
        ArrayList<Item> ItemList = new ArrayList<>();
        Item test1 = new Item("test","000","mockPhoto","This is the first test",new Date());
        Item test2 = new Item("test","001","mockPhoto","This is the second test",new Date());
        ItemList.add(test1);
        ItemList.add(test2);


        ArrayAdapter<Item> arrayAdapter = new ArrayAdapter<Item>(this,android.R.layout.simple_expandable_list_item_1,ItemList);

    }
}