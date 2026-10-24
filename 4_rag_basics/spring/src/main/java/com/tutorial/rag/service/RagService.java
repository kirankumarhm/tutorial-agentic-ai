package com.tutorial.rag.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.document.Document;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.reader.pdf.PagePdfDocumentReader;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.SimpleVectorStore;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class RagService {

    private static final Logger log = LoggerFactory.getLogger(RagService.class);

    private final ChatClient chatClient;
    private final VectorStore vectorStore;
    private boolean initialized = false;

    public RagService(ChatClient.Builder chatClientBuilder, EmbeddingModel embeddingModel) {
        this.chatClient = chatClientBuilder.build();
        this.vectorStore = SimpleVectorStore.builder(embeddingModel).build();
    }

    public synchronized void initializeKnowledgeBase() {
        if (initialized) {
            log.info("Knowledge base already initialized.");
            return;
        }

        log.info("Reading PDF: telecom_guide.pdf...");
        ClassPathResource pdfResource = new ClassPathResource("telecom_guide.pdf");
        PagePdfDocumentReader pdfReader = new PagePdfDocumentReader(pdfResource);
        List<Document> pages = pdfReader.get();
        log.info("Read {} pages from PDF.", pages.size());

        log.info("Chunking pages with TokenTextSplitter (chunkSize=600, overlap=100)...");
        TokenTextSplitter splitter = new TokenTextSplitter(600, 100, 5, 10000, true);
        List<Document> chunks = splitter.apply(pages);
        log.info("Generated {} semantic chunks.", chunks.size());

        log.info("Indexing chunks into VectorStore...");
        vectorStore.accept(chunks);
        this.initialized = true;
        log.info("RAG Knowledge Base indexing complete!");
    }

    public List<Document> retrieveRelevantChunks(String query, int topK) {
        if (!initialized) {
            initializeKnowledgeBase();
        }
        return vectorStore.similaritySearch(
                SearchRequest.builder()
                        .query(query)
                        .topK(topK)
                        .build()
        );
    }

    public Map<String, Object> askQuestion(String question) {
        List<Document> relevantChunks = retrieveRelevantChunks(question, 3);

        String context = relevantChunks.stream()
                .map(Document::getText)
                .collect(Collectors.joining("\n\n---\n\n"));

        String systemPrompt = """
                You are a helpful telecom assistant.
                Answer the question using ONLY the context provided below.
                If the context does not contain enough information, say so clearly.

                Context:
                """ + context;

        String answer = chatClient.prompt()
                .system(systemPrompt)
                .user(question)
                .call()
                .content();

        return Map.of(
                "question", question,
                "answer", answer,
                "retrievedChunksCount", relevantChunks.size(),
                "chunks", relevantChunks.stream().map(d -> Map.of(
                        "id", d.getId(),
                        "preview", d.getText().length() > 200 ? d.getText().substring(0, 200) + "..." : d.getText()
                )).toList()
        );
    }
}
