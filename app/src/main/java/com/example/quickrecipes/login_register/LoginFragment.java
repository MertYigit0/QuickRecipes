package com.example.quickrecipes.login_register;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import com.example.quickrecipes.R;
import com.example.quickrecipes.databinding.FragmentLoginBinding;
import com.example.quickrecipes.recipes.RecipesActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class LoginFragment extends Fragment {

    private FragmentLoginBinding binding;
    private FirebaseAuth mAuth;

    public LoginFragment() {
        // Required empty public constructor
    }




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        FirebaseUser user = mAuth.getCurrentUser();
        if (user!= null){
            Intent intent = new Intent(requireContext(), RecipesActivity.class);
            startActivity(intent);
            finishActivity();
        }

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Fragmentin görsel arayüzünü oluşturmak için inflater

        // ViewBinding oluşturma
        binding = FragmentLoginBinding.inflate(inflater, container, false);

        // ViewBinding ile bağlı öğeleri kullan
        View view = binding.getRoot();

        // ViewBinding'in kök görünümünü döndür
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        Button loginButton = view.findViewById(R.id.loginButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginClicked(view);

            }
        });

        TextView registernow = view.findViewById(R.id.registerText);
        registernow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerNowClicked(view);
            }
        });
}

        public void loginClicked(View view){

        String email = binding.editTextTextEmailAddress.getText().toString();
        String password = binding.editTextPassword.getText().toString();

        if(email.equals("")||password.equals("")){

            Toast.makeText(requireContext(), "Please fill in all the necessary fields", Toast.LENGTH_LONG).show();

        }else{
            mAuth.signInWithEmailAndPassword(email,password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                @Override
                public void onSuccess(AuthResult authResult) {
                    Intent intent = new Intent(requireContext(),RecipesActivity.class);
                    startActivity(intent);
                    finishActivity();

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(requireContext(),e.getLocalizedMessage(),Toast.LENGTH_LONG).show();
                }
            });
        }


    }

    public void registerNowClicked(View view){

        NavDirections action = LoginFragmentDirections.actionLoginFragmentToRegisterFragment();
        Navigation.findNavController(view).navigate(action);

    }
    private void finishActivity() {
        if(getActivity() != null) {
            getActivity().finish();
        }
    }



}