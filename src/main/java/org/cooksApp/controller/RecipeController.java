package org.cooksApp.controller;

import lombok.RequiredArgsConstructor;
import org.cooksApp.dto.RecipeDto;
import org.cooksApp.model.Recipe;
import org.cooksApp.request.CreateRecipeRequest;
import org.cooksApp.request.RecipeUpdateRequest;
import org.cooksApp.service.recipe.IRecipeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/recipe")
public class RecipeController {
    private final IRecipeService recipeService;

    @PostMapping
    public ResponseEntity<RecipeDto> createRecipe(@RequestBody CreateRecipeRequest request){
        Recipe recipe = recipeService.createRecipe(request);
        return ResponseEntity.ok(recipeService.convertToDto(recipe));
    }

    @GetMapping
    public ResponseEntity<List<RecipeDto>> getAllRecipes(){
        List<Recipe> recipes = recipeService.getAllRecipes();
        List<RecipeDto> recipeDto = recipeService.getConvertedRecipes(recipes);
        return ResponseEntity.ok(recipeDto);
    }
    @GetMapping("/{recipeId}/recipe")
    public ResponseEntity<RecipeDto> getRecipeById(@PathVariable Long recipeId){
        RecipeDto recipe = recipeService.convertToDto(recipeService.getRecipeById(recipeId));
        return ResponseEntity.ok(recipe);
    }

    @PutMapping("/{recipeId}/update")
    public ResponseEntity<RecipeDto> updateRecipe(@PathVariable Long recipeId, @RequestBody RecipeUpdateRequest request) {
        Recipe updatedRecipe = recipeService.updateRecipe(request,recipeId);
        RecipeDto recipeDto = recipeService.convertToDto(updatedRecipe);
        return ResponseEntity.ok(recipeDto);
    }



    @DeleteMapping("/{recipeId}/delete")
    public ResponseEntity<Void> deleteRecipe(@PathVariable Long recipeId) {
        recipeService.deleteRecipe(recipeId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/categories")
    public ResponseEntity<Set<String>> getAllCategories(){
        return ResponseEntity.ok(recipeService.getAllRecipeCategories());
    }

    @GetMapping("/cuisines")
    public ResponseEntity<Set<String>> getAllCuisines(){
        return ResponseEntity.ok(recipeService.getAllRecipeCuisine());
    }

}
