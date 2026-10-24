package com.tutorial.vectordb.controller;

import com.tutorial.vectordb.service.VectorDbService;
import org.springframework.ai.document.Document;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/vector")
public class VectorDbController {

    private final VectorDbService vectorService;

    public VectorDbController(VectorDbService vectorService) {
        this.vectorService = vectorService;
    }

    @PostMapping("/init")
    public Map<String, String> init() {
        vectorService.indexSampleArticles();
        return Map.of("status", "success", "message", "4 sample articles indexed successfully");
    }

    @GetMapping("/search")
    public Map<String, Object> search(
            @RequestParam(defaultValue = "This is a query related to Elon Musk") String query,
            @RequestParam(defaultValue = "2") int topK) {
        List<Document> results = vectorService.search(query, topK);
        return Map.of(
                "query", query,
                "topK", topK,
                "results", results.stream().map(d -> Map.of(
                        "id", d.getId(),
                        "content", d.getText(),
                        "metadata", d.getMetadata()
                )).toList()
        );
    }
}
