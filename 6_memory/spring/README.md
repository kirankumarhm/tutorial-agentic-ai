# 🧠 Module 6: Conversational Agent with Memory (Spring AI Edition)

Production-ready implementation of a **Stateful Conversational Agent** with conversational memory, session checkpointing, and coreference resolution built with **Spring Boot 3.4.1**, **Spring AI 1.0.0-M6**, and **Java 21 LTS**.

---

## 🏗️ Architecture

```
                                 ┌──────────────────────────────────────┐
                                 │      Spring AI ChatMemory Store      │
                                 │   (InMemoryChatMemory / Session ID)  │
                                 └───────────────▲──────────────────────┘
                                                 │ Loads & Stores State
                                                 ▼
┌──────────────────────────────┐          ┌──────────────┐          ┌───────────────────────────┐
│ Turn 1 User Prompt           │ ───────► │ Spring AI    │ ───────► │ Agent Response:           │
│ "What is the price of        │          │ ChatClient   │          │ "Wireless headphones      │
│  wireless headphones?"       │          │ + Advisor    │          │  cost $79.99"             │
└──────────────────────────────┘          └──────────────┘          └───────────────────────────┘
                                                 │
                               Memory persisted under conversationId
                                                 │
                                                 ▼
┌──────────────────────────────┐          ┌──────────────┐          ┌───────────────────────────┐
│ Turn 2 Follow-Up Prompt      │ ───────► │ Memory-Aware │ ───────► │ Agent Response:           │
│ "What are the reviews on     │          │ ChatClient   │          │ "The wireless headphones  │
│  this product?"              │          │              │          │  have a 4.6/5 rating from │
│  (Pronoun: "this product")   │          └──────┬───────┘          │  1,262 reviews."          │
└──────────────────────────────┘                 │                  └───────────────────────────┘
                                                 │
                                                 ▼ [Coreference Resolved: "this product" = headphones]
                                    Invokes getReview("wireless headphones")
```

---

## 🚀 Key Features

1. **`ChatMemory` & `MessageChatMemoryAdvisor`**:
   - Manages message history across multi-turn user dialogues.
   - Automatically injects conversation history before invoking the LLM.
2. **Session Partitioning via `conversationId`**:
   - Each conversation session maintains isolated state (e.g. `user-alice-session-1`).
   - Multiple users or tabs do not leak context into each other.
3. **Coreference Resolution**:
   - Resolves pronouns (*"it"*, *"this product"*, *"that one"*) based on preceding turns.
4. **Autonomous Tool Calling**:
   - Integrates with `ProductTools` (`getProduct`, `getReview`).

---

## 🛠️ REST API Endpoints & cURL Commands

### 1. Multi-Turn Chat (Turn 1: Inquire about product)
```bash
curl -X GET "http://localhost:8086/api/memory/chat?conversationId=user-alice-session-1&message=what+is+the+price+of+wireless+headphones"
```

### 2. Multi-Turn Chat (Turn 2: Follow-up using pronoun)
```bash
curl -X GET "http://localhost:8086/api/memory/chat?conversationId=user-alice-session-1&message=what+are+the+reviews+on+this+product"
```

### 3. Clear Session Memory
```bash
curl -X POST "http://localhost:8086/api/memory/clear?conversationId=user-alice-session-1"
```

### 4. View Available Product Catalog
```bash
curl -X GET "http://localhost:8086/api/memory/catalog"
```

---

## 🏃 Running the Application

```powershell
cd 6_memory/spring
mvn spring-boot:run
```
