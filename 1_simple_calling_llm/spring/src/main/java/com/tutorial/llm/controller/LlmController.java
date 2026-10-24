package com.tutorial.llm.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/llm")
public class LlmController {

    private final ChatClient chatClient;

    public LlmController(ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    @GetMapping("/ask")
    public Map<String, String> ask(@RequestParam(defaultValue = "Why do parrots talk?") String query) {
        String answer = chatClient.prompt()
                .user(query)
                .call()
                .content();
        return Map.of("query", query, "answer", answer);
    }

    @GetMapping("/tagline")
    public Map<String, Object> tagline(@RequestParam(defaultValue = "0.0") Double temperature) {
        String prompt = "What is the single tagline for a quirky coffee shop. only output the tagline, nothing else";
        String tagline = chatClient.prompt()
                .user(prompt)
                .options(OpenAiChatOptions.builder().temperature(temperature).build())
                .call()
                .content();
        return Map.of("temperature", temperature, "tagline", tagline);
    }

    @GetMapping("/short-answer")
    public Map<String, String> shortAnswer(@RequestParam(defaultValue = "How many moons does Jupiter have?") String question) {
        String answer = chatClient.prompt()
                .system("You are a helpful assistant that answers in one short line.")
                .user(question)
                .call()
                .content();
        return Map.of("question", question, "answer", answer);
    }
}
