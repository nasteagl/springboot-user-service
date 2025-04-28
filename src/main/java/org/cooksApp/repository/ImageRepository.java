package org.cooksApp.repository;

import jakarta.transaction.Transactional;
import org.cooksApp.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;

@Transactional
public interface ImageRepository  extends JpaRepository<Image, Long> {
    Image findByRecipeId(Long recipeId);
}
