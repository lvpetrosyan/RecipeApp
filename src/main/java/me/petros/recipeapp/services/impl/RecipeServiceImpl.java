package me.petros.recipeapp.services.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import me.petros.recipeapp.exception.FileException;
import me.petros.recipeapp.model.Recipe;
import me.petros.recipeapp.services.FilesService;
import me.petros.recipeapp.services.RecipeService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.*;

@Service
public class RecipeServiceImpl implements RecipeService {
    private final FilesService filesService;

    private Map<Integer, Recipe> allRecipeMap = new HashMap<>();
    private static int id = 0;

    public RecipeServiceImpl(@Qualifier("recipeFileService") FilesService filesService) {
        this.filesService = filesService;
    }

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
    private void saveFileRecipe(){
        try {
            String json = new ObjectMapper().writeValueAsString(allRecipeMap);
            filesService.saveToFile(json);
        } catch (JsonProcessingException e) {
            throw new FileException("Не удалось сохранить файл");
        }
    }
    private void readFromFile(){
        try {
            String json= filesService.readFromFile();
            allRecipeMap= new ObjectMapper().readValue(json, new TypeReference<HashMap<Integer, Recipe>>() {
            });
        } catch (JsonProcessingException e) {
            throw new FileException("Не удалось прочитать файл");
        }
    }
    @PostConstruct
    private void initRecipe(){
        readFromFile();
    }
}
