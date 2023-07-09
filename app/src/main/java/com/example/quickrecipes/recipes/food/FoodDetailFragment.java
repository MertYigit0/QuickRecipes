package com.example.quickrecipes.recipes.food;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.accessibility.AccessibilityViewCommand;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quickrecipes.R;
import com.example.quickrecipes.databinding.FragmentFoodDetailBinding;
import com.example.quickrecipes.databinding.FragmentRecipesSearchBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class FoodDetailFragment extends Fragment {

    private FirebaseFirestore firestore;

    FragmentFoodDetailBinding binding;

    ArrayList<Food> foodArrayList;
    RecyclerViewAdapter recyclerViewAdapter;
    private int position;

    private TextView nameText;
    private ImageView imageView;

    public FoodDetailFragment() {
        // Required empty public constructor

    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        foodArrayList = new ArrayList<>();
        firestore = FirebaseFirestore.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // ViewBinding oluşturma
        binding = FragmentFoodDetailBinding.inflate(inflater, container, false);

        // ViewBinding ile bağlı öğeleri kullanabilirsiniz
        View view = binding.getRoot();
        if (container != null) {
            container.removeAllViews();
        }


        // ViewBinding'in kök görünümünü döndürün
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            position = bundle.getInt("position"); // Değişiklik burada
            System.out.println(position);
            getData();
        }


        //ADD FAVORITE BUTTON
        Button favoriteButton = view.findViewById(R.id.favoriteButton);
        favoriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                addFavoriteClicked(position);
            }
        });


    }

    private void getData() {
        firestore.collection("Recipes").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    QuerySnapshot querySnapshot = task.getResult();
                    if (querySnapshot != null) {
                        List<DocumentSnapshot> documents = querySnapshot.getDocuments();
                        if (position >= 0 && position < documents.size()) {
                            DocumentSnapshot snapshot = documents.get(position);
                            Map<String, Object> data = snapshot.getData();

                            String directions = (String) data.get("Directions");
                            String ingredients = (String) data.get("Ingredients");
                            String name = (String) data.get("Name");
                            String downloadUrl = (String) data.get("downloadurl");

                            binding.nameText.setText(name);
                            binding.directionsText.setText(directions);
                            binding.ingredientsText.setText(ingredients);

                            Picasso.get().load(downloadUrl).into(binding.imageView);

                            Food food = new Food(directions, ingredients, name, downloadUrl);
                            foodArrayList.add(food);
                        }
                    }
                } else {
                    Toast.makeText(getContext(), "Error getting data: " + task.getException().getLocalizedMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }


    public void addFavoriteClicked(int position){
        Toast.makeText(getContext(), "EKLENDI FAVORILERE " + position, Toast.LENGTH_SHORT).show();
    }








}