package com.tutorial.multimodal.tools;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class DietTools {

    private static final Logger log = LoggerFactory.getLogger(DietTools.class);

    private static final Map<String, Map<String, List<String>>> DIET_PLANS = Map.of(
            "high_cholesterol", Map.of(
                    "eat", List.of("fruits", "vegetables", "whole grains", "lean protein"),
                    "do_not_eat", List.of("red meat", "fried food", "full-fat dairy", "processed snacks")
            ),
            "high_sugar", Map.of(
                    "eat", List.of("vegetables", "whole grains", "legumes", "nuts"),
                    "do_not_eat", List.of("white rice", "white sugar", "junk food", "sugary drinks")
            ),
            "normal", Map.of(
                    "eat", List.of("vegetables", "fruits", "whole grains", "lean protein"),
                    "do_not_eat", List.of("excessive sugar", "processed food", "trans fats")
            )
    );

    @Tool(description = "Given a health condition, returns a structured diet plan. Condition must be one of: normal, high_cholesterol, high_sugar.")
    public Map<String, List<String>> getDietRecommendation(String condition) {
        log.info("[TOOL CALL] getDietRecommendation invoked with condition: '{}'", condition);
        String condKey = (condition != null) ? condition.toLowerCase().trim() : "normal";
        if (!DIET_PLANS.containsKey(condKey)) {
            condKey = "normal";
        }
        return DIET_PLANS.get(condKey);
    }

    public Map<String, Map<String, List<String>>> getAllDietPlans() {
        return DIET_PLANS;
    }
}
