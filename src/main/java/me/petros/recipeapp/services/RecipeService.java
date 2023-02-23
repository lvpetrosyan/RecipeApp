package me.petros.recipeapp.services;

import me.petros.recipeapp.model.Recipe;

import java.util.List;
import java.util.Map;

public interface RecipeService {
    Recipe getRecipe(int a);

    Recipe getRecipe();

    int addRecipe(Recipe recipe);

    Recipe editeRecipe(int id, Recipe recipe);

    boolean deleteRecipe(int id);
    Map<Integer, Recipe> getRecipeMap();

}
