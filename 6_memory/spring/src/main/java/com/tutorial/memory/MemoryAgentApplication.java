package com.tutorial.memory;

import com.tutorial.memory.service.MemoryAgentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class MemoryAgentApplication {

    private static final Logger log = LoggerFactory.getLogger(MemoryAgentApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(MemoryAgentApplication.class, args);
    }

    @Bean
    public CommandLineRunner runner(MemoryAgentService memoryAgentService) {
        return args -> {
            log.info("=================================================================");
            log.info("SPRING AI — MODULE 6: CONVERSATIONAL AGENT WITH MEMORY DEMO");
            log.info("=================================================================");

            String googleKey = System.getenv("GOOGLE_API_KEY");
            String openAiKey = System.getenv("OPENAI_API_KEY");
            if ((googleKey == null || googleKey.isBlank()) && (openAiKey == null || openAiKey.isBlank())) {
                log.warn("No active AI API key found in environment (GOOGLE_API_KEY / OPENAI_API_KEY).");
                log.warn("Skipping live agent queries. Configure .env to execute live conversational memory calls.");
                return;
            }

            try {
                String sessionId = "user-alice-session-1";

                // Turn 1: Ask about wireless headphones
                log.info("--- Turn 1 (Session: {}) ---", sessionId);
                String q1 = "what is the price of wireless headphones";
                log.info("User: {}", q1);
                String a1 = memoryAgentService.chat(sessionId, q1);
                log.info("Agent:\n{}\n", a1);

                // Turn 2: Follow-up using pronoun "this product" (requires conversational memory)
                log.info("--- Turn 2 (Session: {}) ---", sessionId);
                String q2 = "what are the reviews on this product";
                log.info("User: {}", q2);
                String a2 = memoryAgentService.chat(sessionId, q2);
                log.info("Agent:\n{}\n", a2);

            } catch (Exception e) {
                log.error("Memory agent execution failed: {}", e.getMessage());
            }
        };
    }
}
