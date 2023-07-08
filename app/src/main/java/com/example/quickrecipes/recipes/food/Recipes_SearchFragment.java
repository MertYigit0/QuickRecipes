package com.example.quickrecipes.recipes.food;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.transition.Slide;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.quickrecipes.R;
import com.example.quickrecipes.databinding.FragmentFavoritesBinding;
import com.example.quickrecipes.databinding.FragmentRecipesSearchBinding;
import com.example.quickrecipes.login_register.LoginFragmentDirections;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;
import java.util.Map;

public class Recipes_SearchFragment extends Fragment {

    private FirebaseStorage firebaseStorage ;
    private FirebaseAuth firebaseAuth;

    private FirebaseFirestore firestore;

    FragmentRecipesSearchBinding binding;


    ArrayList<Food> foodArrayList;

    RecyclerViewAdapter recyclerViewAdapter;



    public Recipes_SearchFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        firebaseStorage = FirebaseStorage.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();

        foodArrayList = new ArrayList<>();
        recyclerViewAdapter = new RecyclerViewAdapter(foodArrayList ,this);



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // ViewBinding oluşturma
        binding = FragmentRecipesSearchBinding.inflate(inflater, container, false);

        // ViewBinding ile bağlı öğeleri kullanabilirsiniz
        View view = binding.getRoot();



        // ViewBinding'in kök görünümünü döndürün
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);



        getData();

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recyclerView.setAdapter(recyclerViewAdapter);


        //Grid layout for 2 collumn recyclerView
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
        binding.recyclerView.setLayoutManager(layoutManager);








    }

    private  void getData() {
        if (foodArrayList.isEmpty()) {

            firestore.collection("Recipes").addSnapshotListener(new EventListener<QuerySnapshot>() {
                @Override
                public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                    if (error != null) {
                        Toast.makeText(getContext(), error.getLocalizedMessage().toString(), Toast.LENGTH_LONG).show();
                    }
                    if (value != null) {
                        for (DocumentSnapshot snapshot : value.getDocuments()) {
                            Map<String, Object> data = snapshot.getData();

                            String directions = (String) data.get("Directions");
                            String ingredients = (String) data.get("Ingredients");
                            String name = (String) data.get("Name");
                            String downloadUrl = (String) data.get("downloadurl");

                            Food food = new Food(directions, ingredients, name, downloadUrl);
                            foodArrayList.add(food);


                        }
                        recyclerViewAdapter.notifyDataSetChanged();
                    }

                }
            });

        }
    }







    public void foodCardClicked(int position ,View view) {
        Bundle bundle = new Bundle();
        bundle.putInt("position", position);
        FoodDetailFragment foodDetailFragment = new FoodDetailFragment();
        foodDetailFragment.setArguments(bundle);


        NavDirections action = Recipes_SearchFragmentDirections.actionRecipesSearchFragmentToFoodDetailFragment();
        Navigation.findNavController(view).navigate(action);
    }










}