package me.petros.recipeapp.controller;

import me.petros.recipeapp.model.Ingredients;
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
    public ResponseEntity<Ingredients> addIngredient(@RequestBody Ingredients ingredients){
        Ingredients ingredients1=ingredientsService.addIngredients(ingredients);
        return ResponseEntity.ok(ingredients1);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Ingredients> getIngredient(@PathVariable int id){
        Ingredients ingredients1= ingredientsService.getIngredients(id);
        if (ingredients1==null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(ingredients1);
    }
}
