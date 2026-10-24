# 🤖 Module 5: Single Agent & Tool Calling (Spring AI Edition)

Production-ready implementation of an **Autonomous ReAct Agent** with tool calling (function calling) built with **Spring Boot 3.4.1**, **Spring AI 1.0.0-M6**, and **Java 21 LTS**.

---

## 🏗️ Architecture

```
┌────────────────────────────────────────────────────────┐
│                      User Request                      │
│      "What is the price and reviews of smart watch?"   │
└───────────────────────────┬────────────────────────────┘
                            │
                            ▼
┌────────────────────────────────────────────────────────┐
│               Spring AI ChatClient (ReAct)             │
│                                                        │
│  1. THOUGHT: "I need product pricing and specs."       │
│  2. ACTION:  getProduct("smart watch")                 │
└─────────────┬────────────────────────────▲─────────────┘
              │ Tool Call                  │ Tool Output
              ▼                            │
┌───────────────────────────┐              │
│    ProductTools Component │ ─────────────┘
│    (@Tool getProduct)     │
└───────────────────────────┘
              │
              ▼
┌────────────────────────────────────────────────────────┐
│               Spring AI ChatClient (ReAct)             │
│                                                        │
│  3. THOUGHT: "Now I need customer reviews."            │
│  4. ACTION:  getReview("smart watch")                  │
└─────────────┬────────────────────────────▲─────────────┘
              │ Tool Call                  │ Tool Output
              ▼                            │
┌───────────────────────────┐              │
│    ProductTools Component │ ─────────────┘
│    (@Tool getReview)      │
└───────────────────────────┘
              │
              ▼
┌────────────────────────────────────────────────────────┐
│                 Synthesized Final Answer               │
│ "The smartwatch costs $199.99 with 340 reviews (3.9★)" │
└────────────────────────────────────────────────────────┘
```

---

## 🚀 Key Features

1. **Spring AI `@Tool` Support**:
   - `ProductTools` exposes `@Tool` methods (`getProduct`, `getReview`) with rich semantic descriptions.
   - Spring AI automatically converts these into OpenAI-compatible tool specifications and dispatches function invocations dynamically.
2. **Autonomous Tool Routing**:
   - Single tool dispatch (`getProduct` for pricing, `getReview` for sentiment).
   - Multi-hop dispatch (both tools invoked in a single conversational turn for composite inquiries).
3. **Dual Endpoint Support**:
   - Works out of the box with **Google Gemini** (`https://generativelanguage.googleapis.com/v1beta/openai` + `gemini-3.8-flash`) via `GOOGLE_API_KEY`.
   - Seamlessly switches to official **OpenAI** (`gpt-4o-mini`) by updating `application.yml`.

---

## 🏃 Running the Application

```powershell
cd 5_single_agent/spring
mvn spring-boot:run
```

---

## 🛠️ REST API Endpoints & cURL Commands (Port 8085)

### 1. Single Tool Call (Price & Specs Query)
```bash
curl -X GET "http://localhost:8085/api/agent/ask?question=what+is+the+price+of+wireless+headphones"
```

### 2. Single Tool Call (Customer Reviews Query)
```bash
curl -X GET "http://localhost:8085/api/agent/ask?question=how+do+people+like+smart+watch"
```

### 3. Multi-Hop Tool Calling (Both Product Specs + Reviews)
```bash
curl -X GET "http://localhost:8085/api/agent/ask?question=what+is+the+price+and+reviews+of+smart+watch"
```

### 4. View In-Memory Product & Review Catalog
```bash
curl -X GET "http://localhost:8085/api/agent/catalog"
```
