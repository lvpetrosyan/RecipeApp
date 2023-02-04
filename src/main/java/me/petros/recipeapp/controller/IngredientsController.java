package me.petros.recipeapp.controller;

import me.petros.recipeapp.model.Ingredients;
import me.petros.recipeapp.model.Recipe;
import me.petros.recipeapp.services.IngredientsService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/ingredient")
@Controller
public class IngredientsController {
    IngredientsService ingredientsService;

    public IngredientsController(IngredientsService ingredients) {
        this.ingredientsService = ingredients;
    }

    @PostMapping
    public ResponseEntity<Ingredients> addIngredient(@RequestBody Ingredients ingredients) {
        Ingredients ingredients1 = (Ingredients) ingredientsService.addIngredients(ingredients);
        return ResponseEntity.ok(ingredients1);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Ingredients> getIngredient(@PathVariable int id) {
        Ingredients ingredients1 = ingredientsService.getIngredients(id);
        if (ingredients1 == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(ingredients1);
    }

    @GetMapping()
    public ResponseEntity<Ingredients> getAllIngredients() {
        ingredientsService.getAllIngredients();
        return ResponseEntity.ok().build();

    }

    @PutMapping("/{id}")
    public ResponseEntity<Ingredients> editIngredient(@PathVariable int id, Ingredients ingredients) {
        Ingredients ingredients1 = ingredientsService.editeIngredient(id, ingredients);
        if (ingredients == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(ingredients1);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteIngredients(@PathVariable int id) {
        if (ingredientsService.deleteIngredients(id)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}

