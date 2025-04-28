package org.cooksApp.dto;

import lombok.Data;

@Data
public class ReviewDto {
    private Long id;
    private int stars;
    private String feedBack;
    private UserDto user;
}
