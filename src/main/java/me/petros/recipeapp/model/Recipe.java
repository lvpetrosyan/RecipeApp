package me.petros.recipeapp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Recipe {
    @NotBlank
    private String nameRecipe;
    @Positive
    private int timeCooking;
    @NotEmpty
    private List<String> cookingSteps;
    private List<Ingredient> ingredients;
}
