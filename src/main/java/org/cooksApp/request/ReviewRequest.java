package org.cooksApp.request;

import lombok.Data;

@Data
public class ReviewRequest {
    private Long userId;
    private int stars;
    private String feedBack;
}
