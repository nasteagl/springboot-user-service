package org.cooksApp.service.like;

public interface ILikeService {

    int likeRecipe(Long recipeId);
    int unLikeRecipe(Long recipeId);
    long getLikesCount(Long recipeId);

}
