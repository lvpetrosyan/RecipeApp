package me.petros.recipeapp.services.impl;

import me.petros.recipeapp.exception.FileException;
import me.petros.recipeapp.model.Ingredient;
import me.petros.recipeapp.model.Recipe;
import me.petros.recipeapp.services.FilesService;
import me.petros.recipeapp.services.RecipeService;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Map;

@Service("recipeFileService")

public class RecipeFileServiceImpl implements FilesService {
    @Value("${path.to.files}")
    private String dataFilePathRecipe;
    @Value("${name.of.recipe.file}")
    private String dataFileNameRecipe;


    @Override
    public boolean saveToFile(String json) {
        try {
            cleanDataFile();
            Files.writeString(Path.of(dataFilePathRecipe, dataFileNameRecipe), json);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public String readFromFile() {
        try {
            return Files.readString(Path.of(dataFilePathRecipe, dataFileNameRecipe));
        } catch (IOException e) {
            throw new FileException("Не получилось прочитать файл!");
        }
    }

    @Override
    public boolean cleanDataFile() {
        try {
            Path path = Path.of(dataFilePathRecipe, dataFileNameRecipe);
            Files.deleteIfExists(path);
            Files.createFile(path);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public File getDataFile() {
        return new File(dataFilePathRecipe + "/" + dataFileNameRecipe);
    }

    @Override
    public InputStreamResource exportFile() throws FileNotFoundException {
        File file = getDataFile();
        if (file.exists()) {
            return new InputStreamResource(new FileInputStream(file));
        } else {
            return null;
        }
    }

    @Override
    public void importFile(MultipartFile file) throws FileNotFoundException {
        cleanDataFile();
        File file1 = getDataFile();
        FileOutputStream fos = new FileOutputStream(file1);
        try {
            IOUtils.copy(file.getInputStream(), fos);
        } catch (IOException e) {
            throw new FileException("не удалось загрузить файл");
        }
    }

    public void importFileTxt(InputStream inputStream) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = reader.readLine()) != null) ;
            String[] array = StringUtils.split(line, '|');
            Recipe recipe = new Recipe(String.valueOf(array[0]), Integer.valueOf(array[1]), String.valueOf(array[2]), Ingredient.valueOf(objects1[3]));
            String[] objects = (String[]) recipe.getCookingSteps().toArray();
            String[] objects1 = (String[]) recipe.getIngredients().toArray();
        }
    }

    private Path createAllRecipeFileForTXT(String suf) throws IOException {
        if (Files.exists(Path.of(dataFilePathRecipe, suf))) {
            Files.delete(Path.of(dataFilePathRecipe, suf));
            Files.createFile(Path.of(dataFilePathRecipe, suf));
            return Path.of(dataFilePathRecipe, suf);
        }
        return Files.createFile(Path.of(dataFilePathRecipe, suf));
    }

    @Override
    public InputStreamResource exportFileRecipeTxt(Map<Integer, Recipe> allRecipeMap) throws IOException {
        Path path = createAllRecipeFileForTXT("allRecipes");
        for (Recipe recipe : allRecipeMap.values()) {
            try (BufferedWriter writer = Files.newBufferedWriter(path, StandardOpenOption.APPEND)) {
                writer.append("Название рецепта: ");
                writer.append(recipe.getNameRecipe());
                writer.append("\n Время приготовления: ");
                writer.append(String.valueOf(recipe.getTimeCooking()));
                writer.append("\n Ингридиенты");
                writer.append(String.valueOf(recipe.getIngredients()));
                writer.append("\n Шаги приготовления");
                writer.append(String.valueOf(recipe.getCookingSteps()));
            }
        }
        File file = path.toFile();
        return new InputStreamResource(new FileInputStream(file));
    }

    @Override
    public InputStreamResource exportFileIngredientTxt(Map<Integer, Ingredient> allIngredientsMap) throws IOException {
        return null;
    }
}

