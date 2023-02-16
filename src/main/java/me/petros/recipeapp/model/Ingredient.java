package me.petros.recipeapp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class Ingredient {
    @NotBlank
    private String nameIngredient;
    @Positive
    private int weightIngredient;
    private String measureUnit;

}
