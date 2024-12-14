package com.example.searchandsend.Service;
import com.example.searchandsend.Model.Image;
import com.example.searchandsend.Repository.ImageRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ImageService {

    private final ImageRepository imageRepository;

    public ImageService(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    public Optional<Image> findByName(String name) {
        return imageRepository.findByName(name);
    }

    public Optional<Image> findById(long id) {
        return imageRepository.findById(id);
    }
}