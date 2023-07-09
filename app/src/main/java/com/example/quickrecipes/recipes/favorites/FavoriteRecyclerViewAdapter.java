package com.example.quickrecipes.recipes.favorites;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.quickrecipes.databinding.RecipeImageBinding;
import com.example.quickrecipes.recipes.food.Food;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class FavoriteRecyclerViewAdapter extends RecyclerView.Adapter<FavoriteRecyclerViewAdapter.MyViewHolder2> {

    private FavoritesFragment fragment;
    public ArrayList<Food> favoriteFoodArrayList;

    private FirebaseFirestore firestore;

    public FavoriteRecyclerViewAdapter(ArrayList<Food> favoriteFoodArrayList,FavoritesFragment fragment) {
        this.favoriteFoodArrayList =favoriteFoodArrayList;
        this.fragment = fragment;
    }

    @NonNull
    @Override
    public MyViewHolder2 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecipeImageBinding recipeImageBinding = RecipeImageBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);

        firestore = FirebaseFirestore.getInstance();

        return new MyViewHolder2(recipeImageBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder2 holder, int position) {
        holder.recipeImageBinding.recipeText.setText(favoriteFoodArrayList.get(position).name);
        Picasso.get().load(favoriteFoodArrayList.get(position).downloadUrl).into(holder.recipeImageBinding.recipeImage);



        holder.recipeImageBinding.foodCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                int position = holder.getAdapterPosition();

                fragment.foodCardClicked(position,view);

            }
        });
    }


    @Override
    public int getItemCount() {
        return favoriteFoodArrayList.size();

    }

    class  MyViewHolder2 extends  RecyclerView.ViewHolder{

        RecipeImageBinding recipeImageBinding;
        public MyViewHolder2( RecipeImageBinding recyclerRowBinding) {
            super(recyclerRowBinding.getRoot() );
            this.recipeImageBinding = recyclerRowBinding;
        }

    }


}
