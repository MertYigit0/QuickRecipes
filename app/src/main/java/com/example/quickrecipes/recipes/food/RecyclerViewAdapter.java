package com.example.quickrecipes.recipes.food;

import android.annotation.SuppressLint;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.quickrecipes.databinding.RecipeImageBinding;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;


public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

    private Recipes_SearchFragment fragment;
    public ArrayList<Food> foodArrayList;
    private ArrayList<Food> filteredList; // Filtrelenmiş liste
    private FirebaseFirestore firestore;

    public RecyclerViewAdapter(ArrayList<Food> foodArrayList,Recipes_SearchFragment fragment) {
        this.foodArrayList = foodArrayList;
        this.fragment = fragment;
        this.filteredList = new ArrayList<>(foodArrayList); // Filtrelenmiş listeyi başlangıçta tüm verilerle başlatın
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecipeImageBinding recipeImageBinding = RecipeImageBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);

        firestore = FirebaseFirestore.getInstance();

        return new MyViewHolder(recipeImageBinding);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.recipeImageBinding.recipeText.setText(foodArrayList.get(position).name);
       // holder.recipeImageBinding.recipeText.setText(foodArrayList.get(position).ingredients);

        Picasso.get().load(foodArrayList.get(position).downloadUrl).into(holder.recipeImageBinding.recipeImage);

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
        return foodArrayList.size();

    }

    class  MyViewHolder extends  RecyclerView.ViewHolder{

      RecipeImageBinding recipeImageBinding;
        public MyViewHolder( RecipeImageBinding recyclerRowBinding) {
            super(recyclerRowBinding.getRoot() );
            this.recipeImageBinding = recyclerRowBinding;
        }

    }

    public void filterRecyclerViewData(String query) {
        ArrayList<Food> filteredList = new ArrayList<>();

        if (TextUtils.isEmpty(query)) {
            filteredList.addAll(foodArrayList);
        } else {
            String filterPattern = query.toLowerCase().trim();

            for (Food food : foodArrayList) {
                if (food.name.toLowerCase().contains(filterPattern)) {
                    filteredList.add(food);
                }
            }
        }
        // Eski verileri temizleme
        foodArrayList.clear();
        // Yeni filtrelenmiş verileri ekleme
        foodArrayList.addAll(filteredList);
        // Eğer filtreleme işlemi sonucunda hiç veri kalmadıysa, orijinal verileri geri yükleme
        if (filteredList.isEmpty()) {
            foodArrayList.addAll(foodArrayList);
        }

        notifyDataSetChanged();
    }
}
