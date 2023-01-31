package me.petros.recipeapp.services;

import me.petros.recipeapp.model.Recipe;

public interface RecipeService {
    Recipe getRecipe(int a);
    int addRecipe(Recipe recipe);
}
