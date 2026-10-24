# 📚 Module 4 — RAG Pipeline (Spring Boot 3.4 & Spring AI)

Enterprise Java implementation of **Retrieval-Augmented Generation (RAG)** over PDF documents using `PagePdfDocumentReader`, `TokenTextSplitter`, and `VectorStore`.

---

## 🏗️ Architecture

```
┌─────────────────────────────────┐
│       telecom_guide.pdf         │
└────────────────┬────────────────┘
                 │ PagePdfDocumentReader
                 ▼
┌─────────────────────────────────┐
│           9 Pages               │
└────────────────┬────────────────┘
                 │ TokenTextSplitter (chunkSize=600, overlap=100)
                 ▼
┌─────────────────────────────────┐
│        Semantic Chunks          │
└────────────────┬────────────────┘
                 │ EmbeddingModel (text-embedding-3-small)
                 ▼
┌─────────────────────────────────┐       similaritySearch(k=3)
│        SimpleVectorStore        │ ─────────────────────────────────┐
└────────────────▲────────────────┘                                  │
                 │                                                   ▼
┌────────────────┴────────────────┐                        ┌───────────────────┐
│          User Question          │                        │ Top-3 Context     │
│  "How does roaming work?"       │ ─────────────►         └─────────┬─────────┘
└─────────────────────────────────┘                                  │
                                                                     ▼
                                                           ┌───────────────────┐
                                                           │ ChatClient Prompt │
                                                           └─────────┬─────────┘
                                                                     │
                                                                     ▼
                                                           ┌───────────────────┐
                                                           │  Grounded Answer  │
                                                           └───────────────────┘
```

---

## 🏃 Running the Application

```powershell
cd 4_rag_basics/spring
mvn spring-boot:run
```

---

## 🛠️ REST API & cURL Commands (Port 8084)

### 1. Ask Grounded Question on International Roaming
```bash
curl -X GET "http://localhost:8084/api/rag/ask?question=How+does+international+roaming+work+and+what+charges+should+I+expect"
```

### 2. Ask Grounded Question on Network Technology
```bash
curl -X GET "http://localhost:8084/api/rag/ask?question=What+is+the+difference+between+VoLTE+and+legacy+circuit+switched+calling"
```
