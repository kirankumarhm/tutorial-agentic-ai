package com.tutorial.rag.controller;

import com.tutorial.rag.service.RagService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/rag")
public class RagController {

    private final RagService ragService;

    public RagController(RagService ragService) {
        this.ragService = ragService;
    }

    @GetMapping("/ask")
    public Map<String, Object> ask(
            @RequestParam(defaultValue = "How does international roaming work and what charges should I expect?") String question) {
        return ragService.askQuestion(question);
    }
}
