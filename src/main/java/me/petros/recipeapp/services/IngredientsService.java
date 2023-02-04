package me.petros.recipeapp.services;

import me.petros.recipeapp.model.Ingredients;
import me.petros.recipeapp.model.Recipe;

public interface IngredientsService {
   Object addIngredients (Ingredients ingredients);
    Ingredients getIngredients(int a);

    Ingredients editeIngredient(int id, Ingredients ingredients);

    boolean deleteIngredients(int id);

    Recipe getAllIngredients();
}
