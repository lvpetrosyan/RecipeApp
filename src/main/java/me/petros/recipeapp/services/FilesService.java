package me.petros.recipeapp.services;

import org.springframework.core.io.InputStreamResource;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;

public interface FilesService {
    boolean saveToFile(String json);
    String readFromFile();
    boolean cleanDataFile();

    File getDataFile();

    InputStreamResource exportFile() throws FileNotFoundException;

    void importFile(MultipartFile file) throws FileNotFoundException;
}
