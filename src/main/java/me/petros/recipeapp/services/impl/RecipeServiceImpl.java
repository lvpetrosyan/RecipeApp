package me.petros.recipeapp.services.impl;

import me.petros.recipeapp.model.Recipe;
import me.petros.recipeapp.services.RecipeService;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class RecipeServiceImpl implements RecipeService {

    private Map<Integer, Recipe> allRecipeMap = new HashMap<>();
    private static int id = 0;


    @Override
    public Recipe getRecipe(int id) {
        return allRecipeMap.get(id);
    }
    @Override
    public Recipe getRecipe() {
        return (Recipe) allRecipeMap.values();
    }

    @Override
    public int addRecipe(Recipe recipe) {
        allRecipeMap.put(id++, recipe);
        return id;
    }

    @Override
    public Recipe editeRecipe(int id, Recipe recipe) {
        if (allRecipeMap.containsKey(id))
            allRecipeMap.put(id, recipe);
        return recipe;
    }

    @Override
    public boolean deleteRecipe(int id) {
        if (allRecipeMap.containsKey(id)) {
            allRecipeMap.remove(id);
            return true;
        }
        return false;
    }
}
