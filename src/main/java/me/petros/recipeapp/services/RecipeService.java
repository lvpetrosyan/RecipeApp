package me.petros.recipeapp.services;

import me.petros.recipeapp.model.Recipe;

public interface RecipeService {
    Recipe getRecipe(int a);

    Recipe getRecipe();

    int addRecipe(Recipe recipe);

    Recipe editeRecipe(int id, Recipe recipe);

    boolean deleteRecipe(int id);
}
