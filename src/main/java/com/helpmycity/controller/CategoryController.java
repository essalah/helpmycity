package com.helpmycity.controller;

import com.helpmycity.model.Category;
import com.helpmycity.payload.ApiResponse;
import com.helpmycity.repository.CategoryRepository;
import com.helpmycity.util.FileTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import static com.helpmycity.Config.UPLOADED_FOLDER;

@RestController
@RequestMapping(path = "api/category")
public class CategoryController {

    @Autowired
    private CategoryRepository categoryRepository;

    @GetMapping("/categories")
    public Object getCategories() {

        return categoryRepository.findAll();

    }

    @PostMapping("/delete")
    public Object delete(@RequestParam Long id) {
        try {
            categoryRepository.deleteById(id);
        } catch (Exception e) {
            return new ApiResponse(false, "Internal error " + e.getMessage());
        }

        return new ApiResponse(true, "Category SUCCESSFULLY DELETED");
    }

    @PostMapping("/add")
    public Object addCategories(@RequestParam Long id, @RequestParam String title, @RequestParam String description, @RequestParam("photo") MultipartFile photo) {

        UUID uuid = UUID.randomUUID();
        String photoName = uuid.toString() + "." + FileTools.getFileExtension(photo.getOriginalFilename());

        // Get the file and save it somewhere
        byte[] bytes = new byte[0];
        try {
            bytes = photo.getBytes();
            Path path = Paths.get(UPLOADED_FOLDER + photoName);
            Files.write(path, bytes);
        } catch (IOException e) {
            e.printStackTrace();
            return new ApiResponse(false, e.getMessage());
        }
        // the file has been saved successfully

        try {
            Category category = new Category();
            if (id > 0)
                category.setCategoryID(id);
            category.setCategoryTitle(title);
            category.setCategoryDescription(description);
            category.setCategoryImage(photoName);

            categoryRepository.save(category);

        } catch (Exception e) {
            return new ApiResponse(false, "Internal error " + e.getMessage());
        }

        return new ApiResponse(true, "Category SUCCESSFULLY ADDED");
    }

}
