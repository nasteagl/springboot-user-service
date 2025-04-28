package org.cooksApp.dto;


import lombok.Data;

import java.util.List;

@Data
public class RecipeDto {
    private Long id;
    private String title;
    private String instruction;
    private String prepTime;
    private String cookTime;
    private String category;
    private String description;
    private List<String> ingredients;
    private String cuisine;
    private ImageDto imageDto;
    private Long likeCount;
    private double averageRating;
    private int totalRateCount;
    private UserDto user;
    private List<ReviewDto> reviews;
}
