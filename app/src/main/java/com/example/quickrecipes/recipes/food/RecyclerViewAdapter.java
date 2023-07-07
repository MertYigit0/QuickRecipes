package com.example.quickrecipes.recipes.food;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quickrecipes.R;
import com.example.quickrecipes.databinding.RecipeImageBinding;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {


    public ArrayList<Food> foodArrayList;
    public RecyclerViewAdapter(ArrayList<Food> foodArrayList) {
        this.foodArrayList = foodArrayList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecipeImageBinding recipeImageBinding = RecipeImageBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);


        return new MyViewHolder(recipeImageBinding);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.recipeImageBinding.recipeText.setText(foodArrayList.get(position).name);
       // holder.recipeImageBinding.recipeText.setText(foodArrayList.get(position).ingredients);


        Picasso.get().load(foodArrayList.get(position).downloadUrl).into(holder.recipeImageBinding.recipeImage);

    }

    @Override
    public int getItemCount() {
        return foodArrayList.size();
    }

    class  MyViewHolder extends  RecyclerView.ViewHolder{

      RecipeImageBinding recipeImageBinding;
        public MyViewHolder( RecipeImageBinding recyclerRowBinding) {
            super(recyclerRowBinding.getRoot() );
            this.recipeImageBinding = recyclerRowBinding;
        }




    }
}
