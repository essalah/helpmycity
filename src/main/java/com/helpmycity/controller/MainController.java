package com.helpmycity.controller;

import com.google.gson.Gson;
import com.helpmycity.Storage.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

@RestController
public class MainController {

    private final StorageService storageService;

    @Autowired
    public MainController(StorageService storageService) {
        this.storageService = storageService;
    }


    @GetMapping("/img/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {

        Resource file = storageService.loadAsResource(filename);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }


    @GetMapping("/api/categories")
    public String getCategories() {

        Path path = null;
        try {
            path = Paths.get(getClass().getClassLoader()
                    .getResource("categories.json").toURI());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        StringBuilder data = new StringBuilder();
        Stream<String> lines = null;
        try {
            if (path != null) {
                lines = Files.lines(path);
                lines.forEach(line -> data.append(line).append("\n"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (lines != null)
            lines.close();

        return data.toString();

    }
}
