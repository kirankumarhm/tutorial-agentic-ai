# 🩺 Module 2 — Clinical Health Analysis (Spring Boot 3.4 & Spring AI)

Enterprise Java implementation of the **Two-Stage Clinical Biomarker Triage & Indian Diet Planning Engine**.

---

## 🏗️ Architecture

```
┌─────────────────────────────────────────────────────────────────┐
│                      Input Blood Report                         │
│                     (blood_work.txt)                            │
└────────────────────────────────┬────────────────────────────────┘
                                 │
                                 ▼
┌─────────────────────────────────────────────────────────────────┐
│         STAGE 1 — Biomarker Triage (HealthAnalysisService)      │
│  Classifies test values as NORMAL, HIGH, or LOW vs references   │
└────────────────────────────────┬────────────────────────────────┘
                                 │
                                 ▼
┌─────────────────────────────────────────────────────────────────┐
│        STAGE 2 — Functional Nutrition (Clinical Nutritionist)   │
│  Generates 4-5 line summary + Indian Foods to Avoid/Prioritize  │
└────────────────────────────────┬────────────────────────────────┘
                                 │
                                 ▼
┌─────────────────────────────────────────────────────────────────┐
│      REST Controller Endpoint / CLI Console Runner              │
│       GET /api/health/analyze-sample | POST /api/health/analyze │
└─────────────────────────────────────────────────────────────────┘
```

---

## 🏃 Running the Application

```powershell
cd 2_health_analysis/spring
mvn spring-boot:run
```

---

## 🛠️ REST API & cURL Commands (Port 8082)

### 1. Analyze Bundled Sample Lab Report (`blood_work.txt`)
```bash
curl -X GET "http://localhost:8082/api/health/analyze-sample"
```

### 2. Analyze Custom Clinical Lab Report
```bash
curl -X POST "http://localhost:8082/api/health/analyze" \
  -H "Content-Type: text/plain" \
  -d "Patient: John Doe, 45, Male. Fasting Blood Glucose: 165 mg/dL (Normal: 70-99). HbA1c: 7.8% (Normal: <5.7%). Total Cholesterol: 245 mg/dL."
```
