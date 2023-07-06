package com.example.quickrecipes.recipes;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.quickrecipes.R;
import com.example.quickrecipes.databinding.FragmentSettingsBinding;
import com.example.quickrecipes.login_register.MainActivity;
import com.google.firebase.auth.FirebaseAuth;


public class SettingsFragment extends Fragment {

    private FragmentSettingsBinding binding;
    private FirebaseAuth mAuth;

    public SettingsFragment() {
        // Required empty public constructor
    }




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
// Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // ViewBinding oluşturma
        binding = FragmentSettingsBinding.inflate(inflater, container, false);

        // ViewBinding ile bağlı öğeleri kullanabilirsiniz
        View view = binding.getRoot();

        // ViewBinding'in kök görünümünü döndürün
        return view;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        Button logoutButton= view.findViewById(R.id.logoutButton);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logoutButtonClicked(view);


            }
        });
    }
    public void logoutButtonClicked(View view){
        mAuth.signOut();

        //Intent for login fragment
        Intent intent = new Intent(getActivity(), MainActivity.class);
        startActivity(intent);

    }

}