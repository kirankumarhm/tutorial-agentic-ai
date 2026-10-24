package com.tutorial.memory.service;

import com.tutorial.memory.tools.ProductTools;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.AbstractChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.InMemoryChatMemory;
import org.springframework.stereotype.Service;

@Service
public class MemoryAgentService {

    private static final Logger log = LoggerFactory.getLogger(MemoryAgentService.class);
    private final ChatClient chatClient;
    private final ChatMemory chatMemory;
    private final ProductTools productTools;

    public MemoryAgentService(ChatClient.Builder chatClientBuilder, ProductTools productTools) {
        this.productTools = productTools;
        this.chatMemory = new InMemoryChatMemory();
        this.chatClient = chatClientBuilder
                .defaultSystem("You are a helpful product assistant for an online tech store. " +
                        "Maintain conversational continuity across turns. When the user refers to 'this product' or " +
                        "'it', resolve the reference from previous messages in the conversation history. " +
                        "Use the provided tools to fetch accurate product details and reviews.")
                .defaultTools(productTools)
                .defaultAdvisors(new MessageChatMemoryAdvisor(this.chatMemory))
                .build();
    }

    public String chat(String conversationId, String message) {
        String sessionId = (conversationId != null && !conversationId.isBlank()) ? conversationId : "default-session";
        log.info("[CHAT TURN] conversationId='{}', message='{}'", sessionId, message);
        return chatClient.prompt()
                .advisors(advisorSpec -> advisorSpec.param(
                        AbstractChatMemoryAdvisor.CHAT_MEMORY_CONVERSATION_ID_KEY,
                        sessionId
                ))
                .user(message)
                .call()
                .content();
    }

    public void clearConversation(String conversationId) {
        String sessionId = (conversationId != null && !conversationId.isBlank()) ? conversationId : "default-session";
        log.info("[CLEAR CONVERSATION] clearing memory for conversationId='{}'", sessionId);
        chatMemory.clear(sessionId);
    }

    public ProductTools getProductTools() {
        return productTools;
    }
}
