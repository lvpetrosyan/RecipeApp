package me.petros.recipeapp.services;

public interface FilesService {
    boolean saveToFile(String json);
    String readFromFile();
    boolean cleanDataFile();
}
