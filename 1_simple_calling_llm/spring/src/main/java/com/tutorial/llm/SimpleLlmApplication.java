package com.tutorial.llm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SimpleLlmApplication {

    private static final Logger log = LoggerFactory.getLogger(SimpleLlmApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(SimpleLlmApplication.class, args);
    }

    @Bean
    public ChatClient chatClient(ChatClient.Builder builder) {
        return builder.build();
    }

    @Bean
    public CommandLineRunner runner(ChatClient chatClient) {
        return args -> {
            log.info("=================================================================");
            log.info("SPRING AI — MODULE 1: SIMPLE LLM CALLING DEMO");
            log.info("=================================================================");

            String apiKey = System.getenv("OPENAI_API_KEY");
            if (apiKey == null || apiKey.isBlank()) {
                log.warn("OPENAI_API_KEY environment variable is not set. Demonstration runs with configured endpoint.");
                log.warn("Set OPENAI_API_KEY in your environment or .env to make live API calls.");
                return;
            }

            try {
                // 1. Basic Invocation
                log.info("--- Step 1: Basic LLM Invocation ---");
                String response1 = chatClient.prompt()
                        .user("Why do parrots talk?")
                        .call()
                        .content();
                log.info("Response:\n{}", response1);

                // 2. Temperature Comparison (0.0 vs 1.0)
                log.info("--- Step 2: Temperature Tuning ---");
                String promptText = "What is the single tagline for a quirky coffee shop. only output the tagline, nothing else";

                String tempZero = chatClient.prompt()
                        .user(promptText)
                        .options(OpenAiChatOptions.builder().temperature(0.0).build())
                        .call()
                        .content();
                log.info("Deterministic (Temperature = 0.0): {}", tempZero);

                String tempOne = chatClient.prompt()
                        .user(promptText)
                        .options(OpenAiChatOptions.builder().temperature(1.0).build())
                        .call()
                        .content();
                log.info("Creative (Temperature = 1.0): {}", tempOne);

                // 3. Structured Role-based Messages (System + Human)
                log.info("--- Step 3: Role-based Prompting (System + User) ---");
                String roleResponse = chatClient.prompt()
                        .system("You are a helpful assistant that answers in one short line.")
                        .user("How many moons does Jupiter have?")
                        .call()
                        .content();
                log.info("Jupiter Moons (Short line): {}", roleResponse);
            } catch (Exception e) {
                log.error("API Call Exception: {}", e.getMessage());
            }
        };
    }
}
