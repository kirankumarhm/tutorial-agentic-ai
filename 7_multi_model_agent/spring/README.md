# 🔬 Module 7: Multimodal Agent (Spring AI Edition)

Production-ready implementation of a **Multimodal Vision Agent** that ingests medical lab report images (`blood_work.png`), extracts clinical biomarkers via vision models, classifies health conditions, and calls specialized diet planning tools built with **Spring Boot 3.4.1**, **Spring AI 1.0.0-M6**, and **Java 21 LTS**.

---

## 🏗️ Architecture

```
┌─────────────────────────────────┐
│        blood_work.png           │  Raw Patient Lab Report (Image)
└────────────────┬────────────────┘
                 │ Multimodal Resource Ingestion
                 ▼
┌────────────────────────────────────────────────────────┐
│     Spring AI Multimodal ChatClient (Vision LLM)       │
│                                                        │
│  1. Visual OCR & Numerical Extraction                  │
│     - Total Cholesterol: 238 mg/dL [HIGH]              │
│     - LDL Cholesterol:   162 mg/dL [HIGH]              │
│     - HDL Cholesterol:   36 mg/dL  [LOW]               │
│     - Triglycerides:     188 mg/dL [HIGH]              │
│  2. Clinical Triage Reasoner                           │
│     - Diagnosis: Category "high_cholesterol"           │
│  3. Decision: Call getDietRecommendation()             │
└────────────────────────┬───────────────────────────────┘
                         │ Tool Call: getDietRecommendation("high_cholesterol")
                         ▼
┌────────────────────────────────────────────────────────┐
│             DietTools Component (@Tool)                │
│  Eat: fruits, vegetables, whole grains, lean protein   │
│  Avoid: red meat, fried food, full-fat dairy           │
└────────────────────────┬───────────────────────────────┘
                         │ Tool Observation
                         ▼
┌────────────────────────────────────────────────────────┐
│                 Synthesized Final Report               │
│  - Biomarker Summary Table with High/Low flags         │
│  - Categorization: high_cholesterol                    │
│  - Targeted Dietary Recommendations & Doctor Advice    │
└────────────────────────────────────────────────────────┘
```

---

## 🚀 Key Features

1. **Multimodal Media Processing**:
   - Accepts image resources directly via `ChatClient`'s `.media(MimeTypeUtils.IMAGE_PNG, resource)`.
2. **Clinical Vision Tool Calling**:
   - Integrates `DietTools` `@Tool` annotation for dynamic clinical lookup.
3. **Multipart File Upload REST Endpoint**:
   - Supports uploading custom lab report images (`PNG`, `JPEG`, `WEBP`) via `multipart/form-data`.
4. **Dual Endpoint Support**:
   - Google Gemini (`gemini-3.8-flash`) / OpenAI (`gpt-4o-mini`).

---

## 🛠️ REST API Endpoints & cURL Commands

### 1. Analyze Sample Blood Work Image (`blood_work.png`)
```bash
curl -X GET "http://localhost:8087/api/multimodal/analyze-sample"
```

### 2. Analyze Uploaded Medical Image (Multipart Form Upload)
```bash
curl -X POST "http://localhost:8087/api/multimodal/analyze" \
  -F "file=@D:/Workspaces/tutorial-agentic-ai/7_multi_model_agent/blood_work.png" \
  -F "prompt=Analyse this blood work report and suggest a diet plan."
```

### 3. Retrieve Diet Plans Catalog
```bash
curl -X GET "http://localhost:8087/api/multimodal/diets"
```

---

## 🏃 Running the Application

```powershell
cd 7_multi_model_agent/spring
mvn spring-boot:run
```
