package com.example.quickrecipes.recipes.favorites;

import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.ActionOnlyNavDirections;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.quickrecipes.R;
import com.example.quickrecipes.databinding.FragmentFavoritesBinding;
import com.example.quickrecipes.databinding.FragmentSettingsBinding;
import com.example.quickrecipes.recipes.food.Food;
import com.example.quickrecipes.recipes.food.RecyclerViewAdapter;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


public class FavoritesFragment extends Fragment {

    private FirebaseStorage firebaseStorage ;

    private FirebaseFirestore firestore;

    FragmentFavoritesBinding binding;

    FavoriteRecyclerViewAdapter favoriteRecyclerViewAdapter;


   ArrayList<Food> favoriteFoodArrayList;

    public FavoritesFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        firebaseStorage = FirebaseStorage.getInstance();
        firestore = FirebaseFirestore.getInstance();
        favoriteFoodArrayList = new ArrayList<>();


        favoriteRecyclerViewAdapter = new FavoriteRecyclerViewAdapter(favoriteFoodArrayList ,this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentFavoritesBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getData();
        binding.favoritesRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.favoritesRecyclerView.setAdapter(favoriteRecyclerViewAdapter);


        //Grid layout for 2 collumn recyclerView
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
        binding.favoritesRecyclerView.setLayoutManager(layoutManager);


    }

    private void getData() {
        firestore.collection("Recipes").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null) {
                    Toast.makeText(getContext(), error.getLocalizedMessage().toString(), Toast.LENGTH_LONG).show();
                }
                if (value != null) {
                    favoriteFoodArrayList.clear(); // Önceki verileri temizleyin

                    for (DocumentSnapshot snapshot : value.getDocuments()) {
                        Map<String, Object> data = snapshot.getData();

                        String directions = (String) data.get("Directions");
                        String ingredients = (String) data.get("Ingredients");
                        String name = (String) data.get("Name");
                        String downloadUrl = (String) data.get("downloadurl");

                        Food food = new Food(directions, ingredients, name, downloadUrl);
                        favoriteFoodArrayList.add(food);
                    }

                    favoriteRecyclerViewAdapter.notifyDataSetChanged(); // Adapteri güncelleyin
                }
            }
        });
    }

    public void foodCardClicked(int position, View view) {
        // Create a bundle to pass the position argument
        Bundle bundle = new Bundle();
        bundle.putInt("position", position);

        // Create the NavDirections object manually
        NavDirections action = new ActionOnlyNavDirections(R.id.action_favoritesFragment_to_foodDetailFragment);
        action.getArguments().putAll(bundle);

        // Navigate to the destination fragment with the arguments
        Navigation.findNavController(view).navigate(action);


        //NavDirections action = Recipes_SearchFragmentDirections.actionRecipesSearchFragmentToFoodDetailFragment();
        //Navigation.findNavController(view).navigate(action);
    }





}