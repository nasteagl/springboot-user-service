package org.cooksApp.service.recipe;
import org.cooksApp.dto.RecipeDto;
import org.cooksApp.model.Recipe;
import org.cooksApp.model.User;
import org.cooksApp.request.CreateRecipeRequest;
import org.cooksApp.request.RecipeUpdateRequest;

import java.util.*;
public interface IRecipeService {


        Recipe createRecipe(CreateRecipeRequest request);

        List<Recipe> getAllRecipes();

        Recipe getRecipeById(Long id);

        Recipe updateRecipe(RecipeUpdateRequest request, Long recipeId);

        void deleteRecipe(Long recipeId);

        Set<String> getAllRecipeCategories();

        Set<String> getAllRecipeCuisine();


        static Recipe createRecipe(CreateRecipeRequest request, User user) {
            Recipe recipe = new Recipe();
            Recipe createRequest = request.getRecipe();
            recipe.setTitle(createRequest.getTitle());
            recipe.setCuisine(createRequest.getCuisine());
            recipe.setCategory(createRequest.getCategory());
            recipe.setInstruction(createRequest.getInstruction());
            recipe.setDescription(createRequest.getDescription());
            recipe.setPrepTime(createRequest.getPrepTime());
            recipe.setCookTime(createRequest.getCookTime());
            recipe.setIngredients(createRequest.getIngredients());
            recipe.setUser(user);
            return recipe;
        }

        static Recipe updateRecipe(Recipe existingRecipe, RecipeUpdateRequest request){
            existingRecipe.setTitle(request.getTitle());
            existingRecipe.setDescription(request.getDescription());
            existingRecipe.setInstruction(request.getInstruction());
            existingRecipe.setCuisine(request.getCuisine());
            existingRecipe.setPrepTime(request.getPrepTime());
            existingRecipe.setCookTime(request.getCookTime());
            existingRecipe.setCategory(request.getCategory());
            existingRecipe.setIngredients(request.getIngredients());
            return existingRecipe;
        }

        List<RecipeDto> getConvertedRecipes(List<Recipe> recipes);

        RecipeDto convertToDto(Recipe recipe);
}

