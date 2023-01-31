package me.petros.recipeapp.services.impl;

import me.petros.recipeapp.model.Recipe;
import me.petros.recipeapp.services.RecipeService;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class RecipeServiceImpl implements RecipeService {

    private Map<Integer, Recipe> allRecipeMap = new HashMap<>();
    private static int id=0;


    @Override
    public Recipe getRecipe(int id) {
        Map<Integer, Recipe> recipes= allRecipeMap;
        for (Integer integer : recipes.keySet()) {
            Recipe recipe=recipes.get(id);
            if (recipe!=null){
                return recipe;
            }
        }
        return null;
    }

    @Override
    public int addRecipe(Recipe recipe) {
         allRecipeMap.put(id++,recipe);
        return id;
    }
}
