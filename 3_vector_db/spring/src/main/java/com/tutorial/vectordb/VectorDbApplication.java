package com.tutorial.vectordb;

import com.tutorial.vectordb.service.VectorDbService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.document.Document;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class VectorDbApplication {

    private static final Logger log = LoggerFactory.getLogger(VectorDbApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(VectorDbApplication.class, args);
    }

    @Bean
    public CommandLineRunner runner(VectorDbService vectorService) {
        return args -> {
            log.info("=================================================================");
            log.info("SPRING AI — MODULE 3: VECTOR DB & SEMANTIC SEARCH DEMO");
            log.info("=================================================================");

            String apiKey = System.getenv("OPENAI_API_KEY");
            if (apiKey == null || apiKey.isBlank()) {
                log.warn("OPENAI_API_KEY is not set. REST server running on port 8083.");
                return;
            }

            try {
                // 1. Index sample articles
                vectorService.indexSampleArticles();

                // 2. Query: "Elon Musk" (Matches Tesla and SpaceX without exact keywords!)
                String query1 = "This is a query related to Elon Musk";
                log.info("--- Executing Semantic Query 1: '{}' ---", query1);
                List<Document> results1 = vectorService.search(query1, 2);
                for (int i = 0; i < results1.size(); i++) {
                    Document doc = results1.get(i);
                    log.info("Match {}: [{}] {}", i + 1, doc.getId(), doc.getText());
                }

                // 3. Query: "I love iPad" (Matches Apple smartphone & fruit based on semantic proximity)
                String query2 = "I love iPad";
                log.info("--- Executing Semantic Query 2: '{}' ---", query2);
                List<Document> results2 = vectorService.search(query2, 2);
                for (int i = 0; i < results2.size(); i++) {
                    Document doc = results2.get(i);
                    log.info("Match {}: [{}] {}", i + 1, doc.getId(), doc.getText());
                }
            } catch (Exception e) {
                log.error("Vector DB error: {}", e.getMessage());
            }
        };
    }
}
