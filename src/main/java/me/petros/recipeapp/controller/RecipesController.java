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
import me.petros.recipeapp.model.Recipe;
import me.petros.recipeapp.services.RecipeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/recipe")
@Tag(name = "Рецепты", description = "CRUD-операции и другие эндпоинты для работы c рецептами")
public class RecipesController {
    public RecipeService recipeService;

    public RecipesController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping("/info")
    @Operation(summary = "Информация о проекте",
               description = "Основная информация о проекте и создателе, вклчючающая: " +
                             "имя автора и проекта, дата создания проекта и его описание")
    public String infoPage() {
        return  "Name creator: Liana.\n" +
                "Name Project: RecipeApp.\n" +
                "Date creation: 24.1.23.\n" +
                "Description project: the app collects and prints recipes.";
    }

    @PostMapping
    @Operation(summary = "Добавление рецепта")
    @Parameters(value = {@Parameter(name = "recipe", example = "Оливье")})
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Рецепт добавлен",
            content = {
                    @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = Recipe.class))
                    )
            }
    )
    }
    )
    public ResponseEntity<Integer> addRecipe(@Valid @RequestBody Recipe recipe) {
        int id = recipeService.addRecipe(recipe);
        return ResponseEntity.ok().body(id);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Поиск рецепта по id")
    @Parameters(value = {@Parameter(name = "id", example = "1"),})
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Рецепт найден",
            content = {
                    @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = Recipe.class))
                    )
            }
    )
    }
    )
    public ResponseEntity<Recipe> getRecipeById(@PathVariable int id) {
        Recipe recipes = recipeService.getRecipe(id);
        if (recipes == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(recipes);
    }

    @GetMapping()
    @Operation(summary = "Поиск всех рецептов",
               description = "Получение списка всех добавленных рецептов, без учета id")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Получен список рецептов",
            content = {
                    @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = Recipe.class))
                    )
            }
    )
    }
    )
    public ResponseEntity<Recipe> getAllRecipe() {
        recipeService.getRecipe();
        return ResponseEntity.ok().build();

    }

    @PutMapping("/{id}")
    @Operation(summary = "Изменение рецепта")
    @Parameters(value = {@Parameter(name = "id", example = "1"),
                         @Parameter(name = "recipe",example = "Мимоза")})
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Рецепт изменен",
            content = {
                    @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = Recipe.class))
                    )
            }
    )
    }
    )
    public ResponseEntity<Recipe> editRecipe(@PathVariable int id, @RequestBody Recipe recipe) {
        Recipe recipe1 = recipeService.editeRecipe(id, recipe);
        if (recipe == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(recipe1);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удаление рецепта")
    @Parameters(value = {@Parameter(name = "id", example = "1")})
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Рецепт удален",
            content = {
                    @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = Recipe.class))
                    )
            }
    )
    }
    )
    public ResponseEntity<Void> deleteRecipe(@PathVariable int id) {
        if (recipeService.deleteRecipe(id)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
