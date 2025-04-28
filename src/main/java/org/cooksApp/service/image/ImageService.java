package org.cooksApp.service.image;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.cooksApp.dto.ImageDto;
import org.cooksApp.model.Image;
import org.cooksApp.model.Recipe;
import org.cooksApp.repository.ImageRepository;
import org.cooksApp.service.recipe.IRecipeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.sql.SQLException;


@Service
@RequiredArgsConstructor
public class ImageService implements IImageService {
    private final ImageRepository imageRepository;
    private final IRecipeService recipeService;

    @Override
    @Transactional(readOnly = true)
    public Image getImageById(Long imageId) {
        return imageRepository.findById(imageId)
                .orElseThrow(() -> new EntityNotFoundException("Image not found"));
    }

    @Override
    @Transactional
    public void deleteImage(Long imageId) {
        imageRepository.findById(imageId).ifPresentOrElse(imageRepository::delete, () ->{
            throw new EntityNotFoundException("Image not found");
        });

    }

    @Override
    @Transactional
    public void updateImage(MultipartFile file, Long imageId) {
        Image image = getImageById(imageId);
        try {
            image.setFileName(file.getOriginalFilename());
            image.setFileType(file.getContentType());
            image.setImage(new SerialBlob(file.getBytes()));
            imageRepository.save(image);
        }catch (IOException | SQLException e){
            throw new RuntimeException(e.getMessage());
        }
    }
    @Override
    @Transactional
    public ImageDto saveImage(Long recipeId, MultipartFile file) {
        Recipe recipe = recipeService.getRecipeById(recipeId);

        try {
            Image image = new Image();
            image.setFileName(file.getOriginalFilename());
            image.setFileType(file.getContentType());
            image.setImage(new SerialBlob(file.getBytes()));
            image.setRecipe(recipe);

            Image savedImage = imageRepository.save(image);

            String buildDownloadUrl = "/api/images/image/download/";
            savedImage.setDownloadUrl(buildDownloadUrl + savedImage.getId());
            imageRepository.save(savedImage);

            ImageDto imageDto = new ImageDto();
            imageDto.setId(savedImage.getId());
            imageDto.setFileName(savedImage.getFileName());
            imageDto.setDownloadUrl(savedImage.getDownloadUrl());
            return imageDto;
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

}
