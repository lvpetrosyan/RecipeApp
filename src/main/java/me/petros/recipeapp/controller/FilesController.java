package me.petros.recipeapp.controller;

import io.swagger.v3.oas.annotations.Operation;
import me.petros.recipeapp.services.FilesService;
import me.petros.recipeapp.services.IngredientsService;
import me.petros.recipeapp.services.RecipeService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;

@RestController
@RequestMapping("/files")
public class FilesController {
    private final FilesService recipeFilesService;
    private final FilesService ingredientFilesService;
    private final RecipeService recipeService;
    private final IngredientsService ingredientsService;

    public FilesController(@Qualifier("recipeFileService") FilesService recipeFilesService,
                           @Qualifier("ingredientFileService") FilesService ingredientFilesService, RecipeService recipeService, IngredientsService ingredientsService) {
        this.recipeFilesService = recipeFilesService;
        this.ingredientFilesService = ingredientFilesService;
        this.recipeService = recipeService;
        this.ingredientsService = ingredientsService;
    }

    @GetMapping("/recipe/export")
    @Operation(summary = "Экспорт файла рецептов")
    public ResponseEntity<InputStreamResource> downloadDataFileRecipe() throws IOException {
        InputStreamResource resource = recipeFilesService.exportFile();
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"Recipes.json\"")
                .contentLength(Files.size(recipeFilesService.getDataFile().toPath()))
                .body(resource);
    }

    @PostMapping(value = "/recipe/import", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Импорт файла рецептов")
    public ResponseEntity<Void> uploadDataFileRecipe(@RequestParam MultipartFile file) throws IOException {
        recipeFilesService.importFile(file);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/recipe/exporttxt")
    @Operation(summary = "Экспорт файла рецептов txt")
    public ResponseEntity<InputStreamResource> downloadFileRecipeTXT() throws IOException {
        InputStreamResource resource = recipeFilesService.exportFileRecipeTxt(recipeService.getRecipeMap());
        return ResponseEntity.ok()
                .contentType(MediaType.TEXT_PLAIN)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"Recipes.txt\"")
                .contentLength(Files.size(recipeFilesService.getDataFile().toPath()))
                .body(resource);
    }

    @GetMapping("/ingredient/export")
    @Operation(summary = "Экспорт файла ингридиентов")
    public ResponseEntity<InputStreamResource> downloadDataFileIngredient() throws IOException {
        InputStreamResource resource = ingredientFilesService.exportFile();
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"Ingredients.json\"")
                .contentLength(Files.size(ingredientFilesService.getDataFile().toPath()))
                .body(resource);
    }

    @PostMapping(value = "/ingredient/import", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Импорт файла ингридиентов")
    public ResponseEntity<Void> uploadDataFileIngredient(@RequestParam MultipartFile file) throws IOException {
        ingredientFilesService.importFile(file);
        return ResponseEntity.ok().build();
    }
    @GetMapping("/ingredient/exporttxt")
    @Operation(summary = "Экспорт файла ингридиентов txt")
    public ResponseEntity<InputStreamResource> downloadFileIngredientTXT() throws IOException {
        InputStreamResource resource = ingredientFilesService.exportFileIngredientTxt(ingredientsService.getIngredientMap());
        return ResponseEntity.ok()
                .contentType(MediaType.TEXT_PLAIN)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"Ingredients.txt\"")
                .contentLength(Files.size(recipeFilesService.getDataFile().toPath()))
                .body(resource);
    }
}

