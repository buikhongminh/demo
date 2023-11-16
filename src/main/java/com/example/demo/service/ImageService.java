package com.example.demo.service;

import com.example.demo.entity.Image;

import java.util.Optional;

public interface ImageService {
    Optional<Image> getImageById(Integer id);
}
