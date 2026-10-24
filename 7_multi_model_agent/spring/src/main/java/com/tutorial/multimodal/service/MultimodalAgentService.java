package com.tutorial.multimodal.service;

import com.tutorial.multimodal.tools.DietTools;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.MimeType;
import org.springframework.util.MimeTypeUtils;

@Service
public class MultimodalAgentService {

    private static final Logger log = LoggerFactory.getLogger(MultimodalAgentService.class);

    private static final String SYSTEM_PROMPT = """
            You are a helpful medical and nutrition assistant.
            For the input blood work image, extract the test numbers and their reference ranges,
            then categorize the condition as one of: normal, high_cholesterol, high_sugar.
            Then call the getDietRecommendation tool to retrieve and present the clinical diet plan.
            """;

    private final ChatClient chatClient;
    private final DietTools dietTools;

    public MultimodalAgentService(ChatClient.Builder chatClientBuilder, DietTools dietTools) {
        this.dietTools = dietTools;
        this.chatClient = chatClientBuilder
                .defaultSystem(SYSTEM_PROMPT)
                .defaultTools(dietTools)
                .build();
    }

    public String analyzeImage(Resource imageResource, MimeType mimeType, String prompt) {
        log.info("[MULTIMODAL ANALYSIS] Analyzing image resource with prompt: '{}'", prompt);
        return chatClient.prompt()
                .user(userSpec -> userSpec
                        .text(prompt != null && !prompt.isBlank() ? prompt : "Analyse this blood work report and suggest a diet plan.")
                        .media(mimeType != null ? mimeType : MimeTypeUtils.IMAGE_PNG, imageResource)
                )
                .call()
                .content();
    }

    public String analyzeSampleReport() {
        Resource sampleImage = new ClassPathResource("blood_work.png");
        return analyzeImage(sampleImage, MimeTypeUtils.IMAGE_PNG, "Analyse this blood work report and suggest a diet plan.");
    }

    public DietTools getDietTools() {
        return dietTools;
    }
}
