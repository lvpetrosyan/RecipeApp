package me.petros.recipeapp.services.impl;

import me.petros.recipeapp.exception.FileException;
import me.petros.recipeapp.services.FilesService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service("ingredientFileService")

public class IngredientsFilesServiceImpl implements FilesService {
    @Value("${path.to.files}")
    private String dataFilePathIngredient;
    @Value("${name.of.ingredient.file}")
    private String dataFileNameIngredient;

    @Override
    public boolean saveToFile(String json) {
        try {
            cleanDataFile();
            Files.writeString(Path.of(dataFilePathIngredient, dataFileNameIngredient), json);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public String readFromFile() {
        try {
            return Files.readString(Path.of(dataFilePathIngredient,dataFileNameIngredient));
        } catch (IOException e) {
            throw new FileException("Не получилось прочитать файл!");
        }
    }

    @Override
    public boolean cleanDataFile() {
        try {
            Path path = Path.of(dataFilePathIngredient, dataFileNameIngredient);
            Files.createFile(path);
            Files.deleteIfExists(path);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
