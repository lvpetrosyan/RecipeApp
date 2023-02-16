package me.petros.recipeapp.services.impl;

import me.petros.recipeapp.exception.FileException;
import me.petros.recipeapp.services.FilesService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

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
            Files.writeString(Path.of(dataFilePathRecipe,dataFileNameRecipe),json);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public String readFromFile() {
        try {
            return Files.readString(Path.of(dataFilePathRecipe,dataFileNameRecipe));
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
}
