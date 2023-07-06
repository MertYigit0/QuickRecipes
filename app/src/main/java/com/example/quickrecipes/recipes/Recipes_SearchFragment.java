package com.example.quickrecipes.recipes;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.quickrecipes.R;

public class Recipes_SearchFragment extends Fragment {

    private RecyclerView recylerView;
    RecyclerView.LayoutManager layoutManager;

    RecyclerViewAdapter recyclerViewAdapter;

    int []arr = {R.drawable.chocolate_sauce,R.drawable.omelet,R.drawable.fried_chicken,R.drawable.berrypie};


    public Recipes_SearchFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_recipes__search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recylerView = view.findViewById(R.id.recyclerView);
        layoutManager = new GridLayoutManager(getContext(),2);
        recylerView.setLayoutManager(layoutManager);

        recyclerViewAdapter = new RecyclerViewAdapter(arr);
        recylerView.setAdapter(recyclerViewAdapter);
        recylerView.setHasFixedSize(true);




    }
}