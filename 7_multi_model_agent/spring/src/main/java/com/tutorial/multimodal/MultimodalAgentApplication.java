package com.tutorial.multimodal;

import com.tutorial.multimodal.service.MultimodalAgentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class MultimodalAgentApplication {

    private static final Logger log = LoggerFactory.getLogger(MultimodalAgentApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(MultimodalAgentApplication.class, args);
    }

    @Bean
    public CommandLineRunner runner(MultimodalAgentService agentService) {
        return args -> {
            log.info("=================================================================");
            log.info("SPRING AI — MODULE 7: MULTIMODAL CLINICAL AGENT DEMO");
            log.info("=================================================================");

            String googleKey = System.getenv("GOOGLE_API_KEY");
            String openAiKey = System.getenv("OPENAI_API_KEY");
            if ((googleKey == null || googleKey.isBlank()) && (openAiKey == null || openAiKey.isBlank())) {
                log.warn("No active AI API key found in environment (GOOGLE_API_KEY / OPENAI_API_KEY).");
                log.warn("Skipping live multimodal visual analysis. Configure .env to execute live image analysis.");
                return;
            }

            try {
                log.info("--- Analyzing Sample Image: blood_work.png ---");
                String report = agentService.analyzeSampleReport();
                log.info("Clinical Multimodal Agent Report:\n{}\n", report);
            } catch (Exception e) {
                log.error("Multimodal agent execution failed: {}", e.getMessage());
            }
        };
    }
}
