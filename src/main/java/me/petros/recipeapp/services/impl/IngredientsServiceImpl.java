package me.petros.recipeapp.services.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import me.petros.recipeapp.exception.FileException;
import me.petros.recipeapp.model.Ingredient;
import me.petros.recipeapp.model.Recipe;
import me.petros.recipeapp.services.FilesService;
import me.petros.recipeapp.services.IngredientsService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

@Service
public class IngredientsServiceImpl implements IngredientsService {
    private final FilesService filesService;
    private static int id = 0;
    Map<Integer, Ingredient> allIngredientsMap = new HashMap<>();

    public IngredientsServiceImpl(@Qualifier("ingredientFileService") FilesService filesService) {
        this.filesService = filesService;
    }

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
        saveToFile();
        return ingredient;
    }
    @Override
    public Ingredient editeIngredient(int id, Ingredient ingredient) {
        if (allIngredientsMap.containsKey(id))
            allIngredientsMap.put(id, ingredient);
        saveToFile();
        return ingredient;
    }

    @Override
    public boolean deleteIngredients(int id) {
        if (allIngredientsMap.containsKey(id)) {
            allIngredientsMap.remove(id);
            saveToFile();
            return true;
        }
        return false;
    }
    @Override
    public Recipe getAllIngredients() {
        return (Recipe) allIngredientsMap.values();
    }
    private void saveToFile(){
        try {
            String json = new ObjectMapper().writeValueAsString(allIngredientsMap);
            filesService.saveToFile(json);
        } catch (JsonProcessingException e) {
            throw new FileException("Файл не получилось сохранить");
        }
    }
    private void readFromFile(){
        try {
            String json= filesService.readFromFile();
            allIngredientsMap= new ObjectMapper().readValue(json, new TypeReference<HashMap<Integer, Ingredient>>(){});
        } catch (JsonProcessingException e) {
            throw new FileException("Файл не получилось прочитать");
        }
    }
    @PostConstruct
    private void initIngredients(){
        readFromFile();
    }
}
