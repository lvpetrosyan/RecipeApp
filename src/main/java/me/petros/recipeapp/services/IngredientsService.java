package me.petros.recipeapp.services;

import me.petros.recipeapp.model.Ingredient;
import me.petros.recipeapp.model.Recipe;

public interface IngredientsService {
   Object addIngredients (Ingredient ingredient);
    Ingredient getIngredients(int a);

    Ingredient editeIngredient(int id, Ingredient ingredient);

    boolean deleteIngredients(int id);

    Recipe getAllIngredients();
}
