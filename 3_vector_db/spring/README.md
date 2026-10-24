# 📦 Module 3 — Vector Database & Semantic Search (Spring Boot 3.4 & Spring AI)

Enterprise Java implementation of **Vector Indexing & Semantic Search** using Spring AI's `VectorStore` and `EmbeddingModel`.

---

## 🏗️ Architecture

```
┌─────────────────────────────────────────────────────────────┐
│                       INDEXING PHASE                        │
│                                                             │
│   News Documents            EmbeddingModel     VectorStore  │
│   - Tesla 1B profit   ───►  (Dense Vector) ──► (In-Memory / │
│   - Apple 35% sales                               Chroma)   │
│   - SpaceX contract                                         │
└─────────────────────────────────────────────────────────────┘
                               │
                               ▼
┌─────────────────────────────────────────────────────────────┐
│                      RETRIEVAL PHASE                        │
│                                                             │
│   Query: "Elon Musk" ───► Query Embedding ──► Top Matches:  │
│                                               1. Tesla      │
│                                               2. SpaceX     │
└─────────────────────────────────────────────────────────────┘
```

---

## 🏃 Running the Application

```powershell
cd 3_vector_db/spring
mvn spring-boot:run
```

---

## 🛠️ REST API & cURL Commands (Port 8083)

### 1. Initialize & Index Sample Articles
```bash
curl -X POST "http://localhost:8083/api/vector/init"
```

### 2. Semantic Search (Top-2 Nearest Neighbors)
```bash
curl -X GET "http://localhost:8083/api/vector/search?query=Elon+Musk&topK=2"
```

### 3. Semantic Search on Financial Data
```bash
curl -X GET "http://localhost:8083/api/vector/search?query=record+quarterly+earnings+and+profits&topK=1"
```
