package com.helpmycity.controller;

import com.helpmycity.Storage.StorageService;
import com.helpmycity.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {

    private final StorageService storageService;
    @Autowired
    private CategoryRepository categoryRepository;

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
    public Object getCategories() {

        return categoryRepository.findAll();

        /*Path path = null;
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

        return data.toString();*/

    }
}
