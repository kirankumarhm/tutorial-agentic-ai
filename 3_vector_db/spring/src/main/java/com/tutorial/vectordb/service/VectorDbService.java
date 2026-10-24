package com.tutorial.vectordb.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.document.Document;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.SimpleVectorStore;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class VectorDbService {

    private static final Logger log = LoggerFactory.getLogger(VectorDbService.class);

    private final VectorStore vectorStore;

    public VectorDbService(EmbeddingModel embeddingModel) {
        this.vectorStore = SimpleVectorStore.builder(embeddingModel).build();
    }

    public void indexSampleArticles() {
        List<Document> documents = List.of(
                new Document("id1", "Apple is leading in a smart phone game with iPhone sales up by 35%", Map.of("category", "tech", "company", "Apple")),
                new Document("id2", "Tesla booked a minor profit of 1 billion $ in Q2", Map.of("category", "business", "company", "Tesla")),
                new Document("id3", "Apples are high in fiber, vitamin C, and various antioxidants", Map.of("category", "nutrition", "fruit", "Apple")),
                new Document("id4", "SpaceX got NASA contract worth 10 billion $", Map.of("category", "aerospace", "company", "SpaceX"))
        );

        log.info("Indexing {} news articles into VectorStore...", documents.size());
        vectorStore.accept(documents);
        log.info("Indexing complete.");
    }

    public List<Document> search(String query, int topK) {
        return vectorStore.similaritySearch(
                SearchRequest.builder()
                        .query(query)
                        .topK(topK)
                        .build()
        );
    }
}
