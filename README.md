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

## 💻 Prerequisites & OS Installation Guide

Before getting started, make sure you have **Git** and **Python 3.10+** (recommended: Python 3.11, 3.12, or 3.13) installed on your system.

### 1. Installing Prerequisites by Operating System

#### 🪟 Windows

1. **Using Winget (Fastest & Recommended)**:
   Open **PowerShell** as Administrator and run:
   ```powershell
   # Install Git
   winget install --id Git.Git -e --source winget

   # Install Python (3.12 or 3.13)
   winget install --id Python.Python.3.12 -e --source winget
   ```
2. **Using Direct Installers**:
   - Download Git from [git-scm.com/download/win](https://git-scm.com/download/win).
   - Download Python from [python.org/downloads](https://www.python.org/downloads/).
   - ⚠️ **Important**: During Python installation, make sure to check the box: **"Add python.exe to PATH"**.

---

#### 🍎 macOS

1. **Install Homebrew** (if not already installed):
   ```bash
   /bin/bash -c "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/HEAD/install.sh)"
   ```
2. **Install Git & Python via Homebrew**:
   ```bash
   brew update
   brew install git python@3.12
   ```
3. *(Alternative)*: Install Apple's Command Line Developer Tools:
   ```bash
   xcode-select --install
   ```

---

#### 🐧 Linux

##### **Ubuntu / Debian / Linux Mint**:
```bash
sudo apt update && sudo apt upgrade -y
sudo apt install -y git python3 python3-pip python3-venv python3-dev build-essential
```

##### **Fedora / RHEL / CentOS**:
```bash
sudo dnf check-update
sudo dnf install -y git python3 python3-pip python3-devel
```

##### **Arch Linux / Manjaro**:
```bash
sudo pacman -Syu --noconfirm git python python-pip
```

---

### 2. Optional: Local Inference with Ollama (For Offline LLMs)
If you wish to run open-weights models (such as `llama3.2`, `mistral`, `deepseek-r1`) offline and locally without paying for API keys:
- **Windows**: Download from [ollama.com/download/windows](https://ollama.com/download/windows) or run `winget install Ollama.Ollama`.
- **macOS**: `brew install ollama` or download from [ollama.com/download/mac](https://ollama.com/download/mac).
- **Linux**: Run `curl -fsSL https://ollama.com/install.sh | sh`.

After installing, pull a model:
```bash
ollama run llama3.2
```

---

## 📦 Required Python Packages

The core dependencies powering this project:

| Package | Version | Purpose |
| :--- | :--- | :--- |
| **`streamlit`** | `^1.40.0` | Powers the reactive, glassmorphic interactive web dashboard |
| **`langchain`** | `^1.4.0` | Orchestrates LLM chains, prompts, and output pipelines |
| **`langchain-core`** | `^1.0.0` | Core abstractions, messages, and interfaces |
| **`langchain-google-genai`** | `^2.0.0` | Driver for Google Gemini (`gemini-3.8-flash`, etc.) |
| **`langchain-openai`** | `^0.3.0` | Driver for OpenAI, OpenRouter, and local Ollama endpoints |
| **`langchain-groq`** | `^0.2.0` | Ultra-low latency LPU inference with Groq |
| **`python-dotenv`** | `^1.0.0` | Environment variable utilities |
| **`requests`** | `^2.31.0` | HTTP requests and API model auto-detection |
| **`notebook` / `ipykernel`** | `^7.0.0` | Interactive Jupyter notebook execution for experiments |

---

## 🛠️ Step-by-Step Setup & Virtual Environment

### Step 1: Clone the Repository
```bash
git clone https://github.com/kirankumarhm/tutorial-agentic-ai.git
cd tutorial-agentic-ai
```

### Step 2: Create and Activate a Virtual Environment

#### On Windows (PowerShell):
```powershell
python -m venv .venv
.\.venv\Scripts\Activate.ps1
```
*(If you encounter an execution policy restriction in PowerShell, run `Set-ExecutionPolicy -ExecutionPolicy RemoteSigned -Scope CurrentUser` once).*

#### On Windows (Command Prompt `cmd`):
```cmd
python -m venv .venv
.\.venv\Scripts\activate.bat
```

#### On macOS / Linux (Terminal):
```bash
python3 -m venv .venv
source .venv/bin/activate
```

---

### Step 3: Install Packages

#### Method A: Standard Installation via `pip`
```bash
pip install --upgrade pip
pip install -r 2_health_analysis/requirements.txt  # or install directly:
pip install streamlit langchain langchain-core langchain-google-genai langchain-openai langchain-groq python-dotenv requests ipykernel notebook
```

#### Method B: Ultra-Fast Installation using `uv` (Recommended)
If you have [uv](https://github.com/astral-sh/uv) installed:
```bash
# Windows
powershell -ExecutionPolicy ByPass -c "irm https://astral.sh/uv/install.ps1 | iex"

# macOS/Linux
curl -LsSf https://astral.sh/uv/install.sh | sh

# Sync dependencies instantly:
uv venv
uv pip install -e .
```

---

## 🚀 Running the Applications

### 1. Launch the Streamlit Health Analyzer App
Make sure your virtual environment is active:
```bash
streamlit run 2_health_analysis/streamlit_app/app.py
```
Your browser will open automatically to `http://localhost:8501`.

### 2. Launch the Jupyter Notebooks
To explore the step-by-step tutorial notebooks:
```bash
jupyter notebook
```
- Open `1_simple_calling_llm/call_llm.ipynb` to explore foundational LLM calling.
- Open `2_health_analysis/blood_work_analysis.ipynb` to explore clinical extraction logic.

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
  - Does **not** read or expose keys from `.env`.
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
