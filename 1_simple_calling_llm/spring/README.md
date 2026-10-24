# 🚀 Module 1 — Simple LLM Calling (Spring Boot 3.4 & Spring AI)

Enterprise Java implementation of basic Large Language Model invocation, temperature tuning, and role-based prompting using **Spring AI**.

---

## 🏗️ Architecture

```
┌─────────────────────────────────┐
│     application.yml / ENV       │  OPENAI_API_KEY & OPENAI_MODEL
└────────────────┬────────────────┘
                 │
                 ▼
┌─────────────────────────────────┐     chatClient.prompt()     ┌────────────────────────┐
│     Spring AI ChatClient        │ ──────────────────────────► │     OpenAI / Proxy     │
│ (Fluent Builder API for Prompts)│                             │  (gpt-4o-mini / local) │
└────────────────┬────────────────┘                             └───────────┬────────────┘
                 │                                                          │
                 ▼                                                          ▼
┌─────────────────────────────────┐                               ┌──────────────────────┐
│  CLI Runner / REST Controller   │ ◄──────────────────────────── │   Formatted String   │
│   (http://localhost:8081/api)   │                               │  (.call().content()) │
└─────────────────────────────────┘                               └──────────────────────┘
```

---

## 🏃 Running the Application

```powershell
cd 1_simple_calling_llm/spring
mvn spring-boot:run
```

---

## 🛠️ REST API & cURL Commands (Port 8081)

### 1. Basic Invocations
```bash
curl -X GET "http://localhost:8081/api/llm/ask?query=Why+do+parrots+talk"
```

### 2. Temperature Tuning (Deterministic vs Creative)
```bash
# Deterministic (temperature = 0.0)
curl -X GET "http://localhost:8081/api/llm/tagline?temperature=0.0"

# Creative (temperature = 1.0)
curl -X GET "http://localhost:8081/api/llm/tagline?temperature=1.0"
```

### 3. Role-Based Prompting (One short line answer)
```bash
curl -X GET "http://localhost:8081/api/llm/short-answer?question=How+many+moons+does+Jupiter+have"
```
