package me.petros.recipeapp.services.impl;

import me.petros.recipeapp.exception.FileException;
import me.petros.recipeapp.services.FilesService;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
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
            return Files.readString(Path.of(dataFilePathIngredient, dataFileNameIngredient));
        } catch (IOException e) {
            throw new FileException("Не получилось прочитать файл!");
        }
    }

    @Override
    public boolean cleanDataFile() {
        try {
            Path path = Path.of(dataFilePathIngredient, dataFileNameIngredient);
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
        return new File(dataFilePathIngredient + "/" + dataFileNameIngredient);
    }

    @Override
    public InputStreamResource exportFile() throws FileNotFoundException {
        File fileIngredient = getDataFile();
        if (fileIngredient.exists()) {
            return new InputStreamResource(new FileInputStream(fileIngredient));
        } else {
            return null;
        }
    }

    @Override
    public void importFile(MultipartFile file) throws FileNotFoundException {
        cleanDataFile();
        File dataFile = getDataFile();
        FileOutputStream fos = new FileOutputStream(dataFile);
        try {
            IOUtils.copy(file.getInputStream(), fos);
        } catch (IOException e) {
            throw new FileException("не удалось загрузить файл");
        }
    }
}
