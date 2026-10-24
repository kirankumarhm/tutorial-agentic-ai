package com.tutorial.multimodal.controller;

import com.tutorial.multimodal.service.MultimodalAgentService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MimeType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/api/multimodal")
public class MultimodalAgentController {

    private final MultimodalAgentService agentService;

    public MultimodalAgentController(MultimodalAgentService agentService) {
        this.agentService = agentService;
    }

    @GetMapping("/analyze-sample")
    public ResponseEntity<Map<String, Object>> analyzeSample() {
        String analysis = agentService.analyzeSampleReport();
        return ResponseEntity.ok(Map.of(
                "image", "blood_work.png (bundled clinical sample)",
                "analysis", analysis
        ));
    }

    @PostMapping(value = "/analyze", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Map<String, Object>> analyzeUploadedImage(
            @RequestParam("file") MultipartFile file,
            @RequestParam(value = "prompt", defaultValue = "Analyse this blood work report and suggest a diet plan.") String prompt) throws IOException {

        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("error", "Uploaded file is empty"));
        }

        String contentType = file.getContentType();
        MimeType mimeType = (contentType != null) ? MimeType.valueOf(contentType) : org.springframework.util.MimeTypeUtils.IMAGE_PNG;

        String result = agentService.analyzeImage(file.getResource(), mimeType, prompt);

        return ResponseEntity.ok(Map.of(
                "filename", file.getOriginalFilename() != null ? file.getOriginalFilename() : "unknown",
                "contentType", contentType != null ? contentType : "unknown",
                "sizeBytes", file.getSize(),
                "prompt", prompt,
                "analysis", result
        ));
    }

    @GetMapping("/diets")
    public ResponseEntity<Map<String, Object>> getDiets() {
        return ResponseEntity.ok(Map.of(
                "dietPlans", agentService.getDietTools().getAllDietPlans()
        ));
    }
}
