package me.petros.recipeapp.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import me.petros.recipeapp.model.Ingredient;
import me.petros.recipeapp.model.Recipe;
import me.petros.recipeapp.services.IngredientsService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequestMapping("/ingredient")
@Controller
@Tag(name = "Ингредиенты", description = "CRUD-операции и другие эндпоинты для работы c ингридиентами для рецепта")
public class IngredientsController {
    IngredientsService ingredientsService;

    public IngredientsController(IngredientsService ingredients) {
        this.ingredientsService = ingredients;
    }

    @PostMapping
    @Operation(summary = "Добавление ингридиента")
    @Parameters(value = {@Parameter(name = "ingredient", example = "Морковка")})
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Ингридиент добавлен",
            content = {
                    @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = Ingredient.class))
                    )
            }
    )
    }
    )
    public ResponseEntity<Ingredient> addIngredient(@Valid @RequestBody Ingredient ingredient) {
        Ingredient ingredient1 = (Ingredient) ingredientsService.addIngredients(ingredient);
        return ResponseEntity.ok(ingredient1);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Поиск ингридиента по id")
    @Parameters(value = {@Parameter(name = "id", example = "1"),})
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Ингридиент найден",
            content = {
                    @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = Ingredient.class))
                    )
            }
    )
    }
    )
    public ResponseEntity<Ingredient> getIngredient(@PathVariable int id) {
        Ingredient ingredient1 = ingredientsService.getIngredients(id);
        if (ingredient1 == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(ingredient1);
    }

    @GetMapping()
    @Operation(summary="Поиск всех ингридиентов", description = "Получение списка всех добавленных ингридиентов, без учета id")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Получен список ингридиентов",
            content = {
                    @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = Ingredient.class))
                    )
            }
    )
    }
    )
    public ResponseEntity<Ingredient> getAllIngredients() {
        ingredientsService.getAllIngredients();
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    @Operation(summary = "Изменение ингридиента по id")
    @Parameters(value = {@Parameter(name = "id", example = "1"),
                         @Parameter(name = "recipe",example = "Молоко")})
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Ингридиент изменен",
            content = {
                    @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = Ingredient.class))
                    )
            }
    )
    }
    )
    public ResponseEntity<Ingredient> editIngredient(@PathVariable int id, @RequestBody Ingredient ingredient) {
        Ingredient ingredient1 = ingredientsService.editeIngredient(id, ingredient);
        if (ingredient == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(ingredient1);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удаление ингридиента")
    @Parameters(value = {@Parameter(name = "id", example = "1")})
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Ингридиент удален",
            content = {
                    @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = Ingredient.class))
                    )
            }
    )
    }
    )
    public ResponseEntity<Void> deleteIngredients(@PathVariable int id) {
        if (ingredientsService.deleteIngredients(id)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}

