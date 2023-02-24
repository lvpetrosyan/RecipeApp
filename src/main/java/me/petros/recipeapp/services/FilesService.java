package me.petros.recipeapp.services;

import me.petros.recipeapp.model.Ingredient;
import me.petros.recipeapp.model.Recipe;
import org.springframework.core.io.InputStreamResource;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;

public interface FilesService {
    boolean saveToFile(String json);
    String readFromFile();
    boolean cleanDataFile();

    File getDataFile();

    InputStreamResource exportFile() throws FileNotFoundException;

    void importFile(MultipartFile file) throws FileNotFoundException;

    InputStreamResource exportFileRecipeTxt(Map<Integer, Recipe> allRecipeMap) throws IOException;

    InputStreamResource exportFileIngredientTxt(Map<Integer, Ingredient> allIngredientsMap) throws IOException;
}
