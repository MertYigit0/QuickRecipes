package com.example.quickrecipes.recipes.food;

public class Food {

    public String directions ;
    public String ingredients;
    public String name       ;
    public String downloadUrl ;


    public Food(  String directions ,String ingredients,String name,String downloadUrl ) {
        this.directions = directions;
        this.ingredients=ingredients;
        this.name = name;
        this.downloadUrl= downloadUrl;
    }
}
