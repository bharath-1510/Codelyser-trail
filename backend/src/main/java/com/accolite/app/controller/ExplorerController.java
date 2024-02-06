package com.accolite.app.controller;// ExplorerController.java

import com.accolite.app.dto.ExplorerItem;
import com.accolite.app.entity.Question;
import com.accolite.app.repo.QuestionRepository;
import com.accolite.app.service.ConverterService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class ExplorerController {
    private final ConverterService converterService;
    @Autowired
    QuestionRepository questionRepository;
    @GetMapping("/explorer")
    public ResponseEntity<List<ExplorerItem>> getExplorerData(@RequestParam Long id) {
        Question question = questionRepository.findById(id).orElse(null);
        if(question==null)
            return ResponseEntity.status(500).body(null);
        String type = question.getType();
        String title = question.getTitle();
        String directoryPath = "C:\\Users\\bharath.m\\Desktop\\Accolite-Project\\ExtractedQuestions\\"+type+"\\"+title;
        List<ExplorerItem> explorerData = converterService.fetchDataFromDirectory(new File(directoryPath));

        return ResponseEntity.ok(explorerData);
    }

    @PostMapping("explorer/file-content")
    public ResponseEntity<Map<String, String>> getFileContent(@RequestBody Map<String, String> requestData) {
        String absolutePath = requestData.get("absolutePath");
        try {
            File file = new File(absolutePath);
            String content = new String(Files.readAllBytes(file.toPath()));
            Map<String, String> response = new HashMap<>();
            response.put("content", content);
            return ResponseEntity.ok(response);
        } catch (IOException e) {
            e.printStackTrace();
            Map<String, String> response = new HashMap<>();
            response.put("content", "Error reading file content.");
            return ResponseEntity.status(500).body(response);
        }
    }


}
