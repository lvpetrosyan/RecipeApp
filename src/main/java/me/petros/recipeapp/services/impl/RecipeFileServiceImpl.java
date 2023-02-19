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
}
