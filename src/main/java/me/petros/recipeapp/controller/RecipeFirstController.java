package me.petros.recipeapp.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RecipeFirstController {
    @GetMapping
    public String startPage() {
        return "Приложение запущено!";
    }
    @GetMapping("/info")
        public String infoPage(){
        return "Name creator: Liana.\n" +
                "Name Project: RecipeApp.\n" +
                "Date creation: 24.1.23.\n" +
                "Description project: the app collects and prints recipes.";
        }

}
