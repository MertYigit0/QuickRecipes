package com.example.quickrecipes;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.quickrecipes.databinding.FragmentRegisterBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class RegisterFragment extends Fragment {

    private FragmentRegisterBinding binding;
    private FirebaseAuth mAuth;
    public RegisterFragment() {
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
        // Fragmentin görsel arayüzünü oluşturmak için inflater'ı kullanın

        // ViewBinding oluşturma
        binding = FragmentRegisterBinding.inflate(inflater, container, false);

        // ViewBinding ile bağlı öğeleri kullanabilirsiniz
        View view = binding.getRoot();

        // ViewBinding'in kök görünümünü döndürün
        return view;
    }






    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        Button registerButton = view.findViewById(R.id.registerButton);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerClicked(view);

            }
        });
    }
    public void registerClicked(View view){
        String email = binding.editTextTextEmailAddress2.getText().toString();
        String password = binding.editTextTextPassword.getText().toString();
        String passwordConfirm = binding.editTextTextPassword2.getText().toString();

        if(email.equals("")||password.equals("")||passwordConfirm.equals("")){

            Toast.makeText(requireContext(), "Please fill in all the necessary fields", Toast.LENGTH_LONG).show();



        }else if(password.equals(passwordConfirm)) {
            mAuth.createUserWithEmailAndPassword(email,password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                @Override
                public void onSuccess(AuthResult authResult) {
                    Intent intent = new Intent(requireContext(), RecipesActivity.class);
                    startActivity(intent);
                    finishActivity();

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(requireContext(), e.getLocalizedMessage(), Toast.LENGTH_LONG).show();

                }
            });

        }

    }





    private void finishActivity() {
        if(getActivity() != null) {
            getActivity().finish();
        }
    }
}