package me.petros.recipeapp.controller;

import me.petros.recipeapp.model.Recipe;
import me.petros.recipeapp.services.RecipeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/recipe")
public class RecipeFirstController {
    public RecipeService recipeService;

    public RecipeFirstController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping("/start")
    public String startPage() {
        return "Приложение запущено!";
    }

    @GetMapping("/info")
    public String infoPage() {
        return "Name creator: Liana.\n" +
                "Name Project: RecipeApp.\n" +
                "Date creation: 24.1.23.\n" +
                "Description project: the app collects and prints recipes.";
    }

    @PostMapping
    public ResponseEntity<Integer> addRecipe(@RequestBody Recipe recipe) {
        int id = recipeService.addRecipe(recipe);
        return ResponseEntity.ok().body(id);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Recipe> getRecipeById(@PathVariable int id) {
        Recipe recipes = recipeService.getRecipe(id);
        if (recipes == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(recipes);
    }

}
