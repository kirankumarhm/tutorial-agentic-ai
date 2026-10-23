# 🚀 Tutorial: Agentic AI & Clinical Health Analysis

A hands-on, modular implementation and practical session based on the tutorial:
📺 **[Watch the Video Tutorial on YouTube](https://www.youtube.com/watch?v=D74el9mvNak&t=1442s)**

This repository covers hands-on implementations for building Agentic AI workflows and production-grade LLM applications using **LangChain** and **Streamlit**.

---

## 📁 Repository Structure

```text
tutorial-agentic-ai/
├── 1_simple_calling_llm/
│   └── call_llm.ipynb            # Foundational LLM calling patterns across providers
│
├── 2_health_analysis/
│   ├── blood_work_analysis.ipynb  # Interactive biomarker extraction & diet prompt engineering
│   ├── blood_work.txt             # Realistic sample lab report (CBC, Lipids, Metabolic Panel)
│   └── streamlit_app/
│       └── app.py                 # Full glassmorphic, model-agnostic Streamlit application
│
├── .gitignore                     # Security filter (strictly excludes .env and secret keys)
├── pyproject.toml                 # Project dependencies and configuration
└── README.md
```

---

## 🌟 Modules Overview

### 1️⃣ `1_simple_calling_llm/`
Demonstrates foundational patterns for initializing and calling Large Language Models through standard LangChain interfaces, parameter tuning (`temperature`, `streaming`), and multi-provider connectivity.

### 2️⃣ `2_health_analysis/`
An end-to-end clinical biomarker analysis and tailored functional nutrition engine.

#### **Key Features of the Streamlit App (`streamlit_app/app.py`)**:
* **⚡ Model & Provider-Agnostic**:
  - **Google Gemini**: `gemini-3.8-flash`, `gemini-3.8-pro`, `gemini-2.5-flash`, `gemma-4-31b-it`
  - **OpenAI**: `gpt-4o`, `gpt-4o-mini`, `o3-mini`, `o1-mini`
  - **Groq**: Ultra-fast LPU inference with `llama-3.3-70b-versatile`, `mixtral-8x7b-32768`
  - **OpenRouter**: Access to 200+ multi-provider models (Claude 3.5, DeepSeek, etc.)
  - **Ollama (Local)**: Private, offline open-source models (`llama3.2`, `deepseek-r1`, `mistral`)
  - **Custom OpenAI-Compatible**: Connect to vLLM, LM Studio, or Together AI
* **🔒 Privacy & In-Memory Key Security**:
  - Does **not** read or store keys from `.env`.
  - Keys are entered directly in the UI per session and kept strictly in memory.
  - Strict pre-execution validation prevents unauthenticated API calls.
* **🔬 Automated Biomarker Triage**:
  - Classifies test results as `HIGH`, `LOW`, or `NORMAL` against standard clinical reference ranges.
  - Formatted table with glowing neon status pills and metric summary cards.
* **🥗 Tailored Indian Dietary Strategies**:
  - Side-by-side comparison of **Foods to Prioritize** (e.g. methi seeds, whole millets, sprouted moong, amla) vs **Foods to Restrict** (vanaspati, refined flours, deep-fried farsan).
  - Customizable by dietary preference (*Vegetarian*, *Eggetarian*, *Non-Vegetarian*).
* **🩺 Physician Checklist**:
  - Generates targeted questions for patients to discuss with their primary care doctor.
* **🎨 Glassmorphic Interface**:
  - Deep obsidian dark canvas with ambient aurora glow, frosted translucent glass containers (`backdrop-filter: blur(20px)`), and responsive layout.

---

## 🛠️ Quickstart & Setup

### 1. Clone the Repository
```bash
git clone https://github.com/kirankumarhm/tutorial-agentic-ai.git
cd tutorial-agentic-ai
```

### 2. Create and Activate Virtual Environment
```bash
# Windows
python -m venv .venv
.\.venv\Scripts\activate

# macOS / Linux
python3 -m venv .venv
source .venv/bin/activate
```

### 3. Install Dependencies
```bash
pip install langchain langchain-core langchain-google-genai langchain-openai langchain-groq streamlit python-dotenv
```
*(Or install via `uv` or `pip install -e .` using the provided `pyproject.toml`)*

### 4. Launch the Streamlit Application
```bash
streamlit run 2_health_analysis/streamlit_app/app.py
```

---

## 🧪 Testing with Sample Blood Work
1. Launch the app in your browser (`http://localhost:8501`).
2. In the sidebar, select your preferred provider (e.g. **Google Gemini**, **OpenAI**, or **Groq**) and paste your API key.
3. Click **"📋 Load Sample Blood Report"** to load the provided clinical lab test panel.
4. Click **"🚀 Analyze Biomarkers"** to view the interactive triage and diet strategy.

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
