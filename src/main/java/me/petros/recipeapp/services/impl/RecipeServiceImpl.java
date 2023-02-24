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
import org.webjars.NotFoundException;

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
        if (!allRecipeMap.containsKey(id)) {
            throw new NotFoundException("Рецепт с таким id не найден");
        }
        return allRecipeMap.get(id);
    }

    @Override
    public Recipe getRecipe() {
        return (Recipe) allRecipeMap.values();
    }

    @Override
    public int addRecipe(Recipe recipe) {
        allRecipeMap.put(id++, recipe);
        saveFileRecipe();
        return id;
    }

    @Override
    public Recipe editeRecipe(int id, Recipe recipe) {
        if (!allRecipeMap.containsKey(id)) {
            throw new NotFoundException("Рецепт с таким id не найден");
        }
        allRecipeMap.put(id, recipe);
        saveFileRecipe();
        return recipe;
    }

    @Override
    public boolean deleteRecipe(int id) {
        if (!allRecipeMap.containsKey(id)) {
            throw new NotFoundException("Рецепт с таким id не найден");
        }
        allRecipeMap.remove(id);
        saveFileRecipe();
        return true;
    }

    @Override
    public Map<Integer, Recipe> getRecipeMap() {
        return allRecipeMap;
    }


    private void saveFileRecipe() {
        try {
            Map <String, Object> map= new HashMap<>();
            map.put("lastid", id);
            map.put("recipe", allRecipeMap);
            String json = new ObjectMapper().writeValueAsString(map);
            filesService.saveToFile(json);
        } catch (JsonProcessingException e) {
            throw new FileException("Не удалось сохранить файл");
        }
    }

    private void readFromFile() {
        try {
            String json = filesService.readFromFile();
            allRecipeMap = new ObjectMapper().readValue(json, new TypeReference<HashMap<Integer, Recipe>>() {
            });
        } catch (JsonProcessingException e) {
            throw new FileException("Не удалось прочитать файл");
        }
    }

    @PostConstruct
    private void initRecipe() {
        try{
        readFromFile();}
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
