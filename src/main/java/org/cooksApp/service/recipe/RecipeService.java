package org.cooksApp.service.recipe;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.cooksApp.dto.ImageDto;
import org.cooksApp.dto.RecipeDto;
import org.cooksApp.dto.ReviewDto;
import org.cooksApp.dto.UserDto;
import org.cooksApp.model.Image;
import org.cooksApp.model.Recipe;
import org.cooksApp.model.User;
import org.cooksApp.repository.ImageRepository;
import org.cooksApp.repository.RecipeRepository;
import org.cooksApp.repository.ReviewRepository;
import org.cooksApp.repository.UserRepository;
import org.cooksApp.request.CreateRecipeRequest;
import org.cooksApp.request.RecipeUpdateRequest;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.cooksApp.service.recipe.RecipeService;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class RecipeService implements IRecipeService {
    private final RecipeRepository recipeRepository;
    private final UserRepository userRepository;
    private  final  ModelMapper modelMapper;
    private final ImageRepository imageRepository;
    private final ReviewRepository reviewRepository;

    @Override
    public Recipe createRecipe(CreateRecipeRequest request) {
        if(request == null || request.getUser() == null){
            throw new IllegalArgumentException("Invalid request");
        }
        User user = Optional.ofNullable(userRepository.findByUsername(request.getUser().getUsername()))
                .map(existingUser -> {
                    existingUser.getRecipe().add(request.getRecipe());
                    return existingUser;
                }).orElseGet(() -> {
                    User newUser = new User(request.getUser().getUsername());
                    userRepository.save(newUser);
                    return newUser;
                });
        Recipe recipe = IRecipeService.createRecipe(request, user);
        return recipeRepository.save(recipe);
    }

    @Override
    public Recipe updateRecipe(RecipeUpdateRequest request, Long recipeId) {
        Recipe recipe = getRecipeById(recipeId);
        Recipe theRecipe = IRecipeService.updateRecipe(recipe, request);
        return recipeRepository.save(theRecipe);
    }

    @Override
    public List<Recipe> getAllRecipes() {
        return recipeRepository.findAll();
    }

    @Override
    public Recipe getRecipeById(Long id) {
        return recipeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Recipe not found"));
    }

    @Override
    public void deleteRecipe(Long recipeId) {
        recipeRepository.deleteById(recipeId);

    }

    @Override
    public Set<String> getAllRecipeCategories() {
        return recipeRepository.findAll()
                .stream()
                .map(Recipe :: getCategory)
                .collect(Collectors.toSet());
    }

    @Override
    public Set<String> getAllRecipeCuisine() {
        return recipeRepository.findAll()
                .stream()
                .map(Recipe :: getCuisine)
                .collect(Collectors.toSet());
    }
    @Override
    public List<RecipeDto> getConvertedRecipes(List<Recipe> recipes){
        return recipes.stream().map(this :: convertToDto).toList();
    }

    @Override
    public RecipeDto convertToDto(Recipe recipe){
        RecipeDto recipeDto = modelMapper.map(recipe, RecipeDto.class);
        UserDto userDto = modelMapper.map(recipe.getUser(), UserDto.class);
        Optional<Image> image  = Optional.ofNullable(imageRepository.findByRecipeId(recipe.getId()));
        image.map(img -> modelMapper.map(img, ImageDto.class)).ifPresent(recipeDto ::setImageDto);
        List<ReviewDto> reviews = reviewRepository.findAllByRecipeId(recipe.getId())
                .stream()
                .map(review -> modelMapper.map(review, ReviewDto.class)).toList();

        //  double averageReviews = recipe.calculateAverageRatings();
        // int totalRateCount = recipe.getTotalRateCount();

        recipeDto.setTotalRateCount(recipe.getTotalRateCount());
        recipeDto.setAverageRating(recipe.calculateAverageRatings());

        recipeDto.setUser(userDto);
        recipeDto.setReviews(reviews);
        return recipeDto;
    }
}
