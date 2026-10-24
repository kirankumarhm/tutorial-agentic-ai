package com.tutorial.agent;

import com.tutorial.agent.service.SingleAgentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SingleAgentApplication {

    private static final Logger log = LoggerFactory.getLogger(SingleAgentApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(SingleAgentApplication.class, args);
    }

    @Bean
    public CommandLineRunner runner(SingleAgentService agentService) {
        return args -> {
            log.info("=================================================================");
            log.info("SPRING AI — MODULE 5: SINGLE AGENT & TOOL CALLING DEMO");
            log.info("=================================================================");

            String googleKey = System.getenv("GOOGLE_API_KEY");
            String openAiKey = System.getenv("OPENAI_API_KEY");
            if ((googleKey == null || googleKey.isBlank()) && (openAiKey == null || openAiKey.isBlank())) {
                log.warn("No active AI API key found in environment (GOOGLE_API_KEY / OPENAI_API_KEY).");
                log.warn("Skipping live agent queries. Configure .env to execute live tool calling.");
                return;
            }

            try {
                // Test Query 1: Single tool call (getProduct)
                log.info("--- Test 1: Single Tool Call (getProduct) ---");
                String q1 = "what is the price of wireless headphones";
                log.info("Prompt: {}", q1);
                String a1 = agentService.ask(q1);
                log.info("Agent Output:\n{}\n", a1);

                // Test Query 2: Single tool call (getReview)
                log.info("--- Test 2: Single Tool Call (getReview) ---");
                String q2 = "how do people like smart watch";
                log.info("Prompt: {}", q2);
                String a2 = agentService.ask(q2);
                log.info("Agent Output:\n{}\n", a2);

                // Test Query 3: Multi-hop Tool Calls (getProduct AND getReview)
                log.info("--- Test 3: Multi-Hop Tool Calling (getProduct + getReview) ---");
                String q3 = "what is the price and reviews of smart watch";
                log.info("Prompt: {}", q3);
                String a3 = agentService.ask(q3);
                log.info("Agent Output:\n{}\n", a3);

            } catch (Exception e) {
                log.error("Agent execution failed: {}", e.getMessage());
            }
        };
    }
}
