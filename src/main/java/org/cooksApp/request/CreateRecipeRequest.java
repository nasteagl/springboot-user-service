package org.cooksApp.request;

import lombok.Data;
import org.cooksApp.model.Recipe;
import org.cooksApp.model.User;

@Data
public class CreateRecipeRequest {
    private Recipe recipe;
    private User user;
}
