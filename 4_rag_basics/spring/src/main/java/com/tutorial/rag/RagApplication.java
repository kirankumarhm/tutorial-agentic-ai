package com.tutorial.rag;

import com.tutorial.rag.service.RagService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.document.Document;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;
import java.util.Map;

@SpringBootApplication
public class RagApplication {

    private static final Logger log = LoggerFactory.getLogger(RagApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(RagApplication.class, args);
    }

    @Bean
    public CommandLineRunner runner(RagService ragService) {
        return args -> {
            log.info("=================================================================");
            log.info("SPRING AI — MODULE 4: RETRIEVAL-AUGMENTED GENERATION (RAG) DEMO");
            log.info("=================================================================");

            String apiKey = System.getenv("OPENAI_API_KEY");
            if (apiKey == null || apiKey.isBlank()) {
                log.warn("OPENAI_API_KEY is not set. REST server running on port 8084.");
                return;
            }

            try {
                // Initialize PDF reader & indexing
                ragService.initializeKnowledgeBase();

                // 1. Test Retriever
                String testQuery = "What is VoLTE and how does it improve call quality";
                log.info("--- Testing Retriever for Query: '{}' ---", testQuery);
                List<Document> chunks = ragService.retrieveRelevantChunks(testQuery, 3);
                log.info("Retrieved {} chunks.", chunks.size());
                for (int i = 0; i < chunks.size(); i++) {
                    log.info("Chunk {}: {}", i + 1, chunks.get(i).getText().substring(0, Math.min(150, chunks.get(i).getText().length())));
                }

                // 2. Full RAG synthesis
                String ragQuestion = "How does international roaming work and what charges should I expect?";
                log.info("--- Executing End-to-End RAG: '{}' ---", ragQuestion);
                Map<String, Object> result = ragService.askQuestion(ragQuestion);
                log.info("Q: {}", ragQuestion);
                log.info("A:\n{}", result.get("answer"));
            } catch (Exception e) {
                log.error("RAG pipeline error: {}", e.getMessage());
            }
        };
    }
}
