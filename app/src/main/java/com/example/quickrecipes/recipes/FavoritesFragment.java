package com.example.quickrecipes.recipes;

import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.quickrecipes.R;
import com.example.quickrecipes.databinding.FragmentFavoritesBinding;
import com.example.quickrecipes.databinding.FragmentSettingsBinding;
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

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


public class FavoritesFragment extends Fragment {

    private FirebaseStorage firebaseStorage ;
    private FirebaseAuth firebaseAuth;

    private FirebaseFirestore firestore;

    FragmentFavoritesBinding binding;


    //Uri.imageData;


    public FavoritesFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        firebaseStorage = FirebaseStorage.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // ViewBinding oluşturma
        binding = FragmentFavoritesBinding.inflate(inflater, container, false);

        // ViewBinding ile bağlı öğeleri kullanabilirsiniz
        View view = binding.getRoot();

        // ViewBinding'in kök görünümünü döndürün
        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ImageView imageView = view.findViewById(R.id.imageView);

        getData();

    }

    private  void getData(){


        firestore.collection("Recipes").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if(error != null){
                    Toast.makeText(getContext(),error.getLocalizedMessage().toString(),Toast.LENGTH_LONG).show();
                }
                if (value != null){
                    for(DocumentSnapshot snapshot : value.getDocuments()){
                        Map<String, Object> data = snapshot.getData();

                        String directions = (String) data.get("Directions");
                        String ingredients = (String) data.get("Ingredients");
                        String name       = (String) data.get("Name");
                        String downloadUrl = (String) data.get("downloadurl");

                        System.out.println(ingredients);
                    }
                }

            }
        });

    }
}