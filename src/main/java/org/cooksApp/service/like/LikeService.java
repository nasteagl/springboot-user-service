package org.cooksApp.service.like;

import lombok.RequiredArgsConstructor;
import org.cooksApp.model.Like;
import org.cooksApp.model.Recipe;
import org.cooksApp.repository.LikeRepository;
import org.cooksApp.repository.RecipeRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LikeService  implements  ILikeService{
   private final RecipeRepository recipeRepository;
   private final LikeRepository likeRepository;
    @Override
    public int likeRecipe(Long recipeId) {
        return recipeRepository.findById(recipeId).map(
                recipe->{
                    if(!likeRepository.existsByRecipeId(recipe.getId())){
                        Like like = new Like();
                        likeRepository.save(like);
                    }
                    recipe.setLikeCount(recipe.getLikeCount()+1);
                    return recipeRepository.save(recipe).getLikeCount();
                }).orElseThrow(() -> new RuntimeException("Recipe not found"));
    }

    @Override
    public int unLikeRecipe(Long recipeId) {
             return likeRepository.findFirstByRecipeId(recipeId).map(
                     like-> {
                         Recipe recipe = recipeRepository.findById(recipeId).orElseThrow();
                         if (recipe.getLikeCount() > 0) {
                             recipe.setLikeCount(recipe.getLikeCount() - 1);
                             recipeRepository.save(recipe);
                         } else {
                             throw new IllegalArgumentException("Like count is zero");
                         }
                         return recipe.getLikeCount();
                     }).orElseThrow(() -> new RuntimeException("No likes found"));
    }

    @Override
    public long getLikesCount(Long recipeId) {
       return recipeRepository.findById(recipeId)
               .map(recipe->likeRepository.countByRecipeId(recipe.getId())).orElse(0L);
    }
}
