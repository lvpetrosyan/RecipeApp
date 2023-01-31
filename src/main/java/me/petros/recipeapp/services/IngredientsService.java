package me.petros.recipeapp.services;

import me.petros.recipeapp.model.Ingredients;

public interface IngredientsService {
   Object addIngredients (Ingredients ingredients);
    Ingredients getIngredients(int a);
}
