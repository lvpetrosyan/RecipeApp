package me.petros.recipeapp.services.impl;

import me.petros.recipeapp.model.Ingredients;
import me.petros.recipeapp.model.Recipe;
import me.petros.recipeapp.services.IngredientsService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
@Service
public class IngredientsServiceImpl implements IngredientsService {
    private static int id=0;
    Map<Integer, Ingredients> allIngredientsMap =new HashMap<>();
@Override
    public Ingredients getIngredients(int id) {
        Map<Integer, Ingredients> allIngredientsInRecipe= allIngredientsMap;
        for (Integer integer : allIngredientsInRecipe.keySet()) {
          Ingredients ingredients= allIngredientsInRecipe.get(id);
                if(ingredients!=null){
                    return ingredients;
            }
        }
        return null;
    }

@Override
    public Ingredients addIngredients(Ingredients ingredients) {
    for (Ingredients ingredients1 : allIngredientsMap.values()) {
        if (ingredients1.equals(ingredients)){
            return ingredients1;
        }
    }
    allIngredientsMap.put(id++,ingredients);
    return ingredients;
    }
}
