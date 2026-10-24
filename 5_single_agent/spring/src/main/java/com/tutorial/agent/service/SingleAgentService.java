package com.tutorial.agent.service;

import com.tutorial.agent.tools.ProductTools;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@Service
public class SingleAgentService {

    private static final Logger log = LoggerFactory.getLogger(SingleAgentService.class);
    private final ChatClient chatClient;
    private final ProductTools productTools;

    public SingleAgentService(ChatClient.Builder chatClientBuilder, ProductTools productTools) {
        this.productTools = productTools;
        this.chatClient = chatClientBuilder
                .defaultSystem("You are a helpful product assistant for an online tech store. " +
                        "Use the provided tools to fetch accurate product specifications and customer reviews. " +
                        "Always cite the exact price, rating, and review counts when available.")
                .defaultTools(productTools)
                .build();
    }

    public String ask(String question) {
        log.info("[AGENT INVOCATION] User query: '{}'", question);
        return chatClient.prompt()
                .user(question)
                .call()
                .content();
    }

    public ProductTools getProductTools() {
        return productTools;
    }
}
