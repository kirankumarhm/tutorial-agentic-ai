package com.tutorial.memory.tools;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class ProductTools {

    private static final Logger log = LoggerFactory.getLogger(ProductTools.class);

    private static final Map<String, Map<String, Object>> PRODUCTS = Map.of(
            "wireless headphones", Map.of("price", 79.99, "rating", 4.6, "description", "Over-ear Bluetooth, 30-hr battery, active noise cancellation."),
            "smart watch", Map.of("price", 199.99, "rating", 4.3, "description", "Tracks heart rate and sleep. 5-day battery, water-resistant."),
            "mechanical keyboard", Map.of("price", 129.00, "rating", 4.8, "description", "Tenkeyless, Cherry MX Brown switches, per-key RGB."),
            "laptop stand", Map.of("price", 34.99, "rating", 4.5, "description", "Adjustable aluminium, fits 11-17 inch laptops, folds flat.")
    );

    private static final Map<String, Map<String, Object>> REVIEWS = Map.of(
            "wireless headphones", Map.of("reviews", 1262, "rating", 4.6),
            "smart watch", Map.of("reviews", 340, "rating", 3.9),
            "mechanical keyboard", Map.of("reviews", 67, "rating", 4.8),
            "laptop stand", Map.of("reviews", 781, "rating", 4.5)
    );

    @Tool(description = "Look up a product by name and return its price, rating, stock, and description.")
    public String getProduct(String name) {
        log.info("[TOOL CALL] getProduct invoked with argument: '{}'", name);
        if (name == null || !PRODUCTS.containsKey(name.toLowerCase().trim())) {
            return "Product not found. Available products: " + String.join(", ", PRODUCTS.keySet());
        }
        return PRODUCTS.get(name.toLowerCase().trim()).toString();
    }

    @Tool(description = "Look up customer reviews by product name. Returns total review count and customer star rating.")
    public String getReview(String name) {
        log.info("[TOOL CALL] getReview invoked with argument: '{}'", name);
        if (name == null || !REVIEWS.containsKey(name.toLowerCase().trim())) {
            return "Review not available for this product. Available reviews: " + String.join(", ", REVIEWS.keySet());
        }
        return REVIEWS.get(name.toLowerCase().trim()).toString();
    }

    public Map<String, Map<String, Object>> getAllProducts() {
        return PRODUCTS;
    }

    public Map<String, Map<String, Object>> getAllReviews() {
        return REVIEWS;
    }
}
