package me.petros.recipeapp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class Ingredients {
    private String nameIngredient;
    private int weightIngredient;
    private String measureUnit;

}
