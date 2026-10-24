package com.tutorial.agent.controller;

import com.tutorial.agent.service.SingleAgentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/agent")
public class AgentController {

    private final SingleAgentService agentService;

    public AgentController(SingleAgentService agentService) {
        this.agentService = agentService;
    }

    @GetMapping("/ask")
    public ResponseEntity<Map<String, Object>> ask(@RequestParam(defaultValue = "what is the price and reviews of smart watch") String question) {
        String answer = agentService.ask(question);
        return ResponseEntity.ok(Map.of(
                "question", question,
                "response", answer
        ));
    }

    @GetMapping("/catalog")
    public ResponseEntity<Map<String, Object>> getCatalog() {
        return ResponseEntity.ok(Map.of(
                "products", agentService.getProductTools().getAllProducts(),
                "reviews", agentService.getProductTools().getAllReviews()
        ));
    }
}
