package com.example.demo.controller;

import com.example.demo.advice.exception.ResourceNotFoundException;
import com.example.demo.service.ImageService;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.io.InputStream;

@RestController
@RequestMapping("/api/v1")
@Log4j2
public class ImageController {
    private final ImageService imageService;
    @Autowired
    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }
    @RequestMapping(
            value = {"/image/{id}"},
            method = {RequestMethod.GET},
            produces = MediaType.IMAGE_JPEG_VALUE
    )
    @ResponseBody
    public byte[] getImageWithMediaType(@PathVariable Integer id) throws Exception {
        var image = imageService.getImageById(id);
        if (image.isPresent()) {
            try {
                var path = image.get().getPath();
                final InputStream in = this.getClass().getResourceAsStream(path);
                return IOUtils.toByteArray(in);
            } catch (Exception exception){
                throw new Exception();
            }
        }
        throw new ResourceNotFoundException("Image not Found");
    }
}