package me.petros.recipeapp.services.impl;

import me.petros.recipeapp.model.Ingredient;
import me.petros.recipeapp.model.Recipe;
import me.petros.recipeapp.services.IngredientsService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class IngredientsServiceImpl implements IngredientsService {
    private static int id = 0;
    Map<Integer, Ingredient> allIngredientsMap = new HashMap<>();

    @Override
    public Ingredient getIngredients(int id) {
        return allIngredientsMap.get(id);
    }

    @Override
    public Ingredient addIngredients(Ingredient ingredient) {
        for (Ingredient ingredient1 : allIngredientsMap.values()) {
            if (ingredient1.equals(ingredient)) {
                return ingredient1;
            }
        }
        allIngredientsMap.put(id++, ingredient);
        return ingredient;
    }
    @Override
    public Ingredient editeIngredient(int id, Ingredient ingredient) {
        if (allIngredientsMap.containsKey(id))
            allIngredientsMap.put(id, ingredient);
        return ingredient;
    }

    @Override
    public boolean deleteIngredients(int id) {
        if (allIngredientsMap.containsKey(id)) {
            allIngredientsMap.remove(id);
            return true;
        }
        return false;
    }
    @Override
    public Recipe getAllIngredients() {
        return (Recipe) allIngredientsMap.values();
    }
}
