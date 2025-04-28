package org.cooksApp.service.image;

import org.cooksApp.dto.ImageDto;
import org.cooksApp.model.Image;
import org.springframework.web.multipart.MultipartFile;
public interface IImageService {
    Image getImageById(Long imageId);
    void deleteImage(Long imageId);
    void updateImage(MultipartFile file, Long imageId);
    ImageDto saveImage(Long recipeId, MultipartFile file);
}
