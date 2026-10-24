package com.tutorial.health;

import com.tutorial.health.service.HealthAnalysisService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;

import java.nio.charset.StandardCharsets;

@SpringBootApplication
public class HealthAnalysisApplication {

    private static final Logger log = LoggerFactory.getLogger(HealthAnalysisApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(HealthAnalysisApplication.class, args);
    }

    @Bean
    public CommandLineRunner runner(HealthAnalysisService healthService) {
        return args -> {
            log.info("=================================================================");
            log.info("SPRING AI — MODULE 2: TWO-STAGE CLINICAL BLOOD WORK PIPELINE");
            log.info("=================================================================");

            String apiKey = System.getenv("OPENAI_API_KEY");
            if (apiKey == null || apiKey.isBlank()) {
                log.warn("OPENAI_API_KEY is not set. REST server is up at http://localhost:8082.");
                return;
            }

            try {
                // Read sample blood work from classpath
                ClassPathResource resource = new ClassPathResource("blood_work.txt");
                String rawReport = resource.getContentAsString(StandardCharsets.UTF_8);

                log.info("Loaded sample blood report ({} chars).", rawReport.length());

                // Stage 1
                log.info("Executing Stage 1: Biomarker Extraction & Triage...");
                String extracted = healthService.extractBiomarkers(rawReport);
                log.info("=== STAGE 1: EXTRACTED BIOMARKERS ===\n{}", extracted);

                // Stage 2
                log.info("Executing Stage 2: Functional Nutrition Strategy...");
                String dietPlan = healthService.generateDietPlan(extracted);
                log.info("=== STAGE 2: HEALTH SUMMARY & DIET PLAN ===\n{}", dietPlan);
            } catch (Exception e) {
                log.error("Pipeline error: {}", e.getMessage());
            }
        };
    }
}
