package com.tutorial.health.controller;

import com.tutorial.health.service.HealthAnalysisService;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@RestController
@RequestMapping("/api/health")
public class HealthController {

    private final HealthAnalysisService healthService;

    public HealthController(HealthAnalysisService healthService) {
        this.healthService = healthService;
    }

    @GetMapping("/analyze-sample")
    public Map<String, String> analyzeSample() throws IOException {
        ClassPathResource resource = new ClassPathResource("blood_work.txt");
        String rawReport = resource.getContentAsString(StandardCharsets.UTF_8);
        return healthService.analyzeCompleteReport(rawReport);
    }

    @PostMapping("/analyze")
    public Map<String, String> analyzeCustomReport(@RequestBody String rawReport) {
        return healthService.analyzeCompleteReport(rawReport);
    }
}
