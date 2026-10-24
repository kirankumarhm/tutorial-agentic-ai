package com.tutorial.memory.controller;

import com.tutorial.memory.service.MemoryAgentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/memory")
public class MemoryAgentController {

    private final MemoryAgentService memoryAgentService;

    public MemoryAgentController(MemoryAgentService memoryAgentService) {
        this.memoryAgentService = memoryAgentService;
    }

    @GetMapping("/chat")
    public ResponseEntity<Map<String, Object>> chat(
            @RequestParam(defaultValue = "user-alice-session-1") String conversationId,
            @RequestParam(defaultValue = "what is the price of wireless headphones") String message) {
        String answer = memoryAgentService.chat(conversationId, message);
        return ResponseEntity.ok(Map.of(
                "conversationId", conversationId,
                "message", message,
                "response", answer
        ));
    }

    @PostMapping("/clear")
    public ResponseEntity<Map<String, Object>> clear(@RequestParam(defaultValue = "user-alice-session-1") String conversationId) {
        memoryAgentService.clearConversation(conversationId);
        return ResponseEntity.ok(Map.of(
                "status", "success",
                "message", "Conversation memory cleared for " + conversationId
        ));
    }

    @GetMapping("/catalog")
    public ResponseEntity<Map<String, Object>> getCatalog() {
        return ResponseEntity.ok(Map.of(
                "products", memoryAgentService.getProductTools().getAllProducts(),
                "reviews", memoryAgentService.getProductTools().getAllReviews()
        ));
    }
}
