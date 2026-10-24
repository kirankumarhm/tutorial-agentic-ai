# 🚀 Tutorial: Agentic AI & Clinical Health Analysis

A hands-on, modular implementation and practical session based on the tutorial:
📺 **[Watch the Video Tutorial on YouTube](https://www.youtube.com/watch?v=D74el9mvNak&t=1442s)**

This repository covers hands-on implementations for building Agentic AI workflows, multimodal vision triage, conversational memory, and production-grade LLM applications using **Python (LangChain / LangGraph / Streamlit)** and **Enterprise Java (Spring Boot 3.4.1 & Spring AI 1.0.0-M6 on Java 21 LTS)**.

---

## 📁 Repository Structure (Polyglot: Python & Spring Edition)

```text
tutorial-agentic-ai/
├── 1_simple_calling_llm/
│   ├── call_llm.ipynb                 # [Python] Foundational LLM calling patterns across providers
│   └── spring/                        # [Spring] Spring Boot 3.4 & Spring AI (Port 8081)
│       ├── pom.xml
│       └── src/main/java/com/tutorial/llm/
│
├── 2_health_analysis/
│   ├── blood_work_analysis.ipynb       # [Python] Interactive biomarker extraction & diet prompt engineering
│   ├── blood_work.txt                  # Realistic sample lab report (CBC, Lipids, Metabolic Panel)
│   ├── streamlit_app/                  # [Python] Glassmorphic Streamlit application
│   └── spring/                        # [Spring] Two-Stage Clinical Pipeline & REST API (Port 8082)
│       ├── pom.xml
│       └── src/main/java/com/tutorial/health/
│
├── 3_vector_db/
│   ├── chroma_vector_db_basics.ipynb  # [Python] Vector embeddings, indexing, and Chroma similarity search
│   └── spring/                        # [Spring] VectorStore & Semantic Search API (Port 8083)
│       ├── pom.xml
│       └── src/main/java/com/tutorial/vectordb/
│
├── 4_rag_basics/
│   ├── basic_rag.ipynb                # [Python] End-to-end Retrieval-Augmented Generation (RAG) pipeline
│   ├── telecom_guide.pdf              # Document for chunking, embedding & vector retrieval
│   └── spring/                        # [Spring] PDF Reader, TokenSplitter & RAG REST API (Port 8084)
│       ├── pom.xml
│       └── src/main/java/com/tutorial/rag/
│
├── 5_single_agent/
│   ├── single_agent.ipynb             # [Python] ReAct agent loop, tool definition (@tool), dynamic routing
│   └── spring/                        # [Spring] Spring AI Tool Calling & REST API (Port 8085)
│       ├── pom.xml
│       └── src/main/java/com/tutorial/agent/
│
├── 6_memory/
│   ├── single_agent_with_memory.ipynb # [Python] Conversational agent with InMemorySaver & thread_id state
│   └── spring/                        # [Spring] ChatMemory & MessageChatMemoryAdvisor (Port 8086)
│       ├── pom.xml
│       └── src/main/java/com/tutorial/memory/
│
├── 7_multi_model_agent/
│   ├── multi_model_agent.ipynb        # [Python] Multimodal vision agent analyzing blood_work.png + tool calling
│   ├── blood_work.png                 # Clinical lab report image (biomarker table)
│   └── spring/                        # [Spring] Multimodal ChatClient + DietTools & Upload API (Port 8087)
│       ├── pom.xml
│       └── src/main/java/com/tutorial/multimodal/
│
├── pom.xml                            # Root Maven Parent & Multi-Module Aggregator (Modules 1-7)
├── pyproject.toml                      # Python dependencies managed via uv
├── requirements.txt                    # Pinned pip requirements
└── README.md
```

---

## 💻 Prerequisites & Required Software

Before getting started, make sure you have the required software installed on your machine:

| Software | Required Version | Purpose | Link |
| :--- | :--- | :--- | :--- |
| **Python** | `3.10+` (tested on `3.12` & `3.13`) | Core programming language runtime | [python.org](https://www.python.org/downloads/) |
| **Java JDK** | `21 LTS` | Runtime for Spring Boot 3.4 & Spring AI | [adoptium.net](https://adoptium.net/) |
| **Apache Maven** | `3.9+` | Build & dependency manager for Spring | [maven.apache.org](https://maven.apache.org/) |
| **Git** | Any modern version | Source code and version control | [git-scm.com](https://git-scm.com/) |
| **uv** *(Recommended)* | `0.10+` | Ultra-fast Python package and project manager | [astral.sh/uv](https://github.com/astral-sh/uv) |
| **Web Browser** | Latest Chrome, Edge, or Firefox | To interact with Streamlit apps and Jupyter | [google.com/chrome](https://www.google.com/chrome/) |
| **VS Code / Cursor** | Latest | Recommended IDE with Python & Java extensions | [code.visualstudio.com](https://code.visualstudio.com/) |

---

## 🔑 Environment Configuration

Create a `.env` file in the repository root or set system environment variables:

```ini
# Google Gemini API Key (Active by default across Python & Spring)
GOOGLE_API_KEY=your_google_gemini_api_key

# OpenAI API Key (Optional alternative)
OPENAI_API_KEY=your_openai_api_key
```

> [!TIP]
> All Spring Boot `application.yml` files are pre-configured with a **Dual-Endpoint Architecture**:
> - **Option A (Default)**: Routes through Google Gemini's OpenAI-compatible endpoint (`https://generativelanguage.googleapis.com/v1beta/openai` using model `gemini-3.8-flash`) via `GOOGLE_API_KEY`.
> - **Option B**: Easily switch to official OpenAI (`gpt-4o-mini`) simply by toggling comments in `application.yml`.

---

## 🚀 Running the Applications

### 1. Launch the Streamlit Health Analyzer App (Python)
```bash
uv sync   # or pip install -r requirements.txt
streamlit run 2_health_analysis/streamlit_app/app.py
```
Your browser will open automatically to `http://localhost:8501`.

### 2. Launch the Jupyter Notebooks (Python)
```bash
python -m notebook
```
- Open `1_simple_calling_llm/call_llm.ipynb` — Foundational LLM calling patterns.
- Open `2_health_analysis/blood_work_analysis.ipynb` — Clinical biomarker extraction & triage.
- Open `3_vector_db/chroma_vector_db_basics.ipynb` — Vector embeddings & Chroma similarity search.
- Open `4_rag_basics/basic_rag.ipynb` — End-to-end RAG pipeline over `telecom_guide.pdf`.
- Open `5_single_agent/single_agent.ipynb` — Autonomous ReAct agent with dynamic tool routing.
- Open `6_memory/single_agent_with_memory.ipynb` — Conversational memory with `InMemorySaver` & session threads.
- Open `7_multi_model_agent/multi_model_agent.ipynb` — Multimodal vision agent analyzing `blood_work.png`.

---

### 3. Launch the Spring Edition (Spring Boot 3.4 & Spring AI on Java 21)

Every module contains an enterprise Spring Boot 3.4 application equipped with both a **CLI Console Runner** and an **interactive REST Controller**:

#### Build All 7 Spring Modules from Root:
```powershell
mvn clean compile
```

#### Run Individual Spring Modules:

* **Module 1: Simple LLM Calling** (Port `8081`)
  ```powershell
  cd 1_simple_calling_llm/spring
  mvn spring-boot:run
  ```

* **Module 2: Clinical Health Analysis** (Port `8082`)
  ```powershell
  cd 2_health_analysis/spring
  mvn spring-boot:run
  ```

* **Module 3: Vector DB & Semantic Search** (Port `8083`)
  ```powershell
  cd 3_vector_db/spring
  mvn spring-boot:run
  ```

* **Module 4: RAG Pipeline with PDF** (Port `8084`)
  ```powershell
  cd 4_rag_basics/spring
  mvn spring-boot:run
  ```

* **Module 5: Single Agent & Tool Calling** (Port `8085`)
  ```powershell
  cd 5_single_agent/spring
  mvn spring-boot:run
  ```

* **Module 6: Conversational Memory Agent** (Port `8086`)
  ```powershell
  cd 6_memory/spring
  mvn spring-boot:run
  ```

* **Module 7: Multimodal Vision Agent** (Port `8087`)
  ```powershell
  cd 7_multi_model_agent/spring
  mvn spring-boot:run
  ```

---

## 🧪 Complete REST API Testing with cURL (Modules 1 to 7)

Use the following copy-pasteable `curl` commands to test each Spring Boot service:

### 🔹 Module 1: Simple LLM Calling (Port 8081)
```bash
# 1. Basic Prompt Invocation
curl -X GET "http://localhost:8081/api/llm/ask?query=Why+do+parrots+talk"

# 2. Temperature Tuning (Deterministic 0.0 vs Creative 1.0)
curl -X GET "http://localhost:8081/api/llm/tagline?temperature=0.0"
curl -X GET "http://localhost:8081/api/llm/tagline?temperature=1.0"

# 3. Role-Based Prompting (Concise Answer)
curl -X GET "http://localhost:8081/api/llm/short-answer?question=How+many+moons+does+Jupiter+have"
```

---

### 🔹 Module 2: Clinical Health Analysis (Port 8082)
```bash
# 1. Analyze Bundled Sample Lab Report (blood_work.txt)
curl -X GET "http://localhost:8082/api/health/analyze-sample"

# 2. Analyze Custom Clinical Lab Report
curl -X POST "http://localhost:8082/api/health/analyze" \
  -H "Content-Type: text/plain" \
  -d "Patient: John Doe, 45, Male. Fasting Blood Glucose: 165 mg/dL (Normal: 70-99). HbA1c: 7.8% (Normal: <5.7%). Total Cholesterol: 245 mg/dL."
```

---

### 🔹 Module 3: Vector DB & Semantic Search (Port 8083)
```bash
# 1. Initialize In-Memory Vector Store with Sample Articles
curl -X POST "http://localhost:8083/api/vector/init"

# 2. Semantic Search for Executive & Business Queries (Top-2 Nearest Neighbors)
curl -X GET "http://localhost:8083/api/vector/search?query=Elon+Musk&topK=2"

# 3. Semantic Search on Financial Data
curl -X GET "http://localhost:8083/api/vector/search?query=record+quarterly+earnings+and+profits&topK=1"
```

---

### 🔹 Module 4: RAG Pipeline with PDF (Port 8084)
```bash
# 1. Inquire about International Roaming Policies from telecom_guide.pdf
curl -X GET "http://localhost:8084/api/rag/ask?question=How+does+international+roaming+work+and+what+charges+should+I+expect"

# 2. Inquire about Technical Architecture (VoLTE vs Circuit Switched)
curl -X GET "http://localhost:8084/api/rag/ask?question=What+is+the+difference+between+VoLTE+and+legacy+circuit+switched+calling"
```

---

### 🔹 Module 5: Single Agent & Tool Calling (Port 8085)
```bash
# 1. Single Tool Dispatch (Product Catalog Lookup)
curl -X GET "http://localhost:8085/api/agent/ask?question=what+is+the+price+of+wireless+headphones"

# 2. Single Tool Dispatch (Customer Sentiment & Reviews Lookup)
curl -X GET "http://localhost:8085/api/agent/ask?question=how+do+people+like+smart+watch"

# 3. Multi-Hop Tool Dispatch (Executes both getProduct and getReview in one turn)
curl -X GET "http://localhost:8085/api/agent/ask?question=what+is+the+price+and+reviews+of+smart+watch"

# 4. Inspect Available Product Catalog & Review Data
curl -X GET "http://localhost:8085/api/agent/catalog"
```

---

### 🔹 Module 6: Conversational Agent with Memory (Port 8086)
```bash
# 1. Turn 1: Inquire about a specific product in a conversation session
curl -X GET "http://localhost:8086/api/memory/chat?conversationId=user-alice-session-1&message=what+is+the+price+of+wireless+headphones"

# 2. Turn 2: Follow-up using pronoun ("this product") — resolved via ChatMemory!
curl -X GET "http://localhost:8086/api/memory/chat?conversationId=user-alice-session-1&message=what+are+the+reviews+on+this+product"

# 3. View In-Memory Product Catalog
curl -X GET "http://localhost:8086/api/memory/catalog"

# 4. Clear Session Memory for a given conversation
curl -X POST "http://localhost:8086/api/memory/clear?conversationId=user-alice-session-1"
```

---

### 🔹 Module 7: Multimodal Vision Agent (Port 8087)
```bash
# 1. Analyze Bundled Medical Report Image (blood_work.png) + Automatic Tool Calling
curl -X GET "http://localhost:8087/api/multimodal/analyze-sample"

# 2. Analyze Custom Uploaded Image via Multipart Form Upload
curl -X POST "http://localhost:8087/api/multimodal/analyze" \
  -F "file=@D:/Workspaces/tutorial-agentic-ai/7_multi_model_agent/blood_work.png" \
  -F "prompt=Analyse this blood work report and suggest a diet plan."

# 3. Retrieve All Available Clinical Diet Plans
curl -X GET "http://localhost:8087/api/multimodal/diets"
```

---

## 🌟 Modules Overview

### 1️⃣ `1_simple_calling_llm/`
Demonstrates foundational patterns for initializing and calling Large Language Models through standard LangChain and Spring AI interfaces, parameter tuning (`temperature`), and multi-provider connectivity.

### 2️⃣ `2_health_analysis/`
An end-to-end clinical biomarker analysis and tailored functional nutrition engine.
- **Biomarker Triage**: Classifies test results as `HIGH`, `LOW`, or `NORMAL`.
- **Targeted Nutrition**: Tailored Indian dietary recommendations (Foods to Prioritize vs Restrict).
- **Streamlit Web Application**: Modern obsidian glassmorphic interface on port 8501.

### 3️⃣ `3_vector_db/`
Explores vector database indexing and similarity search using **ChromaDB** (Python) and Spring AI's `VectorStore`:
- Dense semantic vector embeddings.
- In-memory collection indexing.
- K-nearest neighbors similarity retrieval.

### 4️⃣ `4_rag_basics/`
Hands-on implementation of **Retrieval-Augmented Generation (RAG)**:
- PDF document ingestion with `PyPDFLoader` / `PagePdfDocumentReader`.
- Granular text chunking (`RecursiveCharacterTextSplitter` / `TokenTextSplitter`).
- Grounded contextual prompt synthesis.

### 5️⃣ `5_single_agent/`
Autonomous **ReAct (Reasoning + Action) Agent Pattern**:
- Converts business functions into schema-backed tools using `@tool` (Python) and `@Tool` (Spring AI).
- Autonomous tool selection: decides whether to invoke `getProduct`, `getReview`, or both.
- Multi-hop reasoning across heterogeneous data sources.

### 6️⃣ `6_memory/`
**Stateful Conversational Agents with Memory Checkpointing**:
- Maintains persistent context across user turns using `InMemorySaver` (LangGraph) and `MessageChatMemoryAdvisor` (Spring AI).
- Session partitioning via `thread_id` / `conversationId`.
- Seamless coreference resolution (understanding pronouns like *"this product"* or *"it"* without restating facts).

### 7️⃣ `7_multi_model_agent/`
**Multimodal Vision Agent with Dynamic Tool Execution**:
- Direct visual ingestion of medical report images (`blood_work.png`).
- Automated visual OCR and abnormal biomarker identification (Lipid Panel: Total Cholesterol 238, LDL 162).
- Autonomous condition triage (`high_cholesterol`).
- Dynamic tool dispatch to clinical nutrition engines (`getDietRecommendation`).

---

## 📺 Video Reference
This repository contains the practical session code from the tutorial video:
- **YouTube Link**: [Agentic AI Practical Tutorial](https://www.youtube.com/watch?v=D74el9mvNak&t=1442s)

---

## ⚠️ Clinical Disclaimer
This application is designed solely for educational, exploratory, and tutorial purposes. It does not provide medical diagnoses, treatment plans, or clinical prescriptions. Always consult a licensed medical professional before making healthcare or dietary changes.

---

## 📄 License
MIT License. Created by [Kiran Kumar H M](https://github.com/kirankumarhm).
