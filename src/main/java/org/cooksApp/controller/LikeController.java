package org.cooksApp.controller;


import lombok.RequiredArgsConstructor;
import org.cooksApp.service.like.ILikeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/likes")
public class LikeController {
    private final ILikeService likeService;

    @PostMapping("/recipe/{recipeId}/like")
    public ResponseEntity<Integer> likeRecipe(@PathVariable Long recipeId){
        int like = likeService.likeRecipe(recipeId);
        return ResponseEntity.ok(like);
    }

    @PutMapping("/recipe/{recipeId}/unLike")
    public ResponseEntity<Integer> unLikeRecipe(@PathVariable Long recipeId){
        int like = likeService.unLikeRecipe(recipeId);
        return ResponseEntity.ok(like);
    }

    @GetMapping("/recipe/{recipeId}/like-count")
    public ResponseEntity<Long> getLikesCount(@PathVariable Long recipeId){
        Long count = likeService.getLikesCount(recipeId);
        return ResponseEntity.ok(count);
    }

}
