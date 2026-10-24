package com.tutorial.health.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class HealthAnalysisService {

    private final ChatClient chatClient;

    public HealthAnalysisService(ChatClient.Builder builder) {
        this.chatClient = builder.build();
    }

    /**
     * Stage 1: Extract and classify all biomarkers against clinical reference ranges.
     */
    public String extractBiomarkers(String rawReport) {
        String extractionPrompt = """
                You are a medical data extraction assistant.

                From the blood report below, extract ALL test values and classify each one as HIGH, LOW, or NORMAL 
                based on the reference ranges provided in the report.

                Format your response strictly as:
                - Test Name: value | Status: HIGH/LOW/NORMAL | Reference: range

                Blood Report:
                """ + rawReport;

        return chatClient.prompt()
                .user(extractionPrompt)
                .call()
                .content();
    }

    /**
     * Stage 2: Functional nutrition synthesis based on extracted biomarker triage.
     */
    public String generateDietPlan(String extractedBiomarkers) {
        String dietPrompt = """
                You are a clinical nutritionist specializing in Indian dietary habits.

                Based on the blood work analysis below, write:
                1. A short health summary in 4-5 lines explaining the patient's condition in simple language
                2. A short, practical Indian diet plan having only two sections:
                   (1) Foods to avoid
                   (2) Foods to eat more of
                   Do not include any other sections in the diet plan.

                Blood Work Analysis:
                """ + extractedBiomarkers;

        return chatClient.prompt()
                .user(dietPrompt)
                .call()
                .content();
    }

    /**
     * Chained end-to-end execution.
     */
    public Map<String, String> analyzeCompleteReport(String rawReport) {
        String stage1 = extractBiomarkers(rawReport);
        String stage2 = generateDietPlan(stage1);
        return Map.of(
                "extractedBiomarkers", stage1,
                "dietPlan", stage2
        );
    }
}
