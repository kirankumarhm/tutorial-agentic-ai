# 🚀 Tutorial: Agentic AI & Clinical Health Analysis

A hands-on, modular implementation and practical session based on the tutorial:
📺 **[Watch the Video Tutorial on YouTube](https://www.youtube.com/watch?v=D74el9mvNak&t=1442s)**

This repository covers hands-on implementations for building Agentic AI workflows and production-grade LLM applications using **LangChain** and **Streamlit**.

---

## 📁 Repository Structure

```text
tutorial-agentic-ai/
├── 1_simple_calling_llm/
│   └── call_llm.ipynb                 # Foundational LLM calling patterns across providers
│
├── 2_health_analysis/
│   ├── blood_work_analysis.ipynb       # Interactive biomarker extraction & diet prompt engineering
│   ├── blood_work.txt                  # Realistic sample lab report (CBC, Lipids, Metabolic Panel)
│   └── streamlit_app/
│       ├── app.py                      # Full glassmorphic, model-agnostic Streamlit application
│       └── AI Blood Work & Diet Analyzer.png # Application UI Screenshot
│
├── 3_vector_db/
│   └── chroma_vector_db_basics.ipynb  # Vector embeddings, indexing, and Chroma similarity search
│
├── 4_rag_basics/
│   ├── basic_rag.ipynb                # End-to-end Retrieval-Augmented Generation (RAG) pipeline
│   └── telecom_guide.pdf              # Document for chunking, embedding & vector retrieval
│
├── .gitignore                          # Security filter (strictly excludes .env and secret keys)
├── pyproject.toml                      # Project dependencies managed via uv
├── requirements.txt                    # Pinned pip requirements
└── README.md
```

---

## 💻 Prerequisites & Required Software

Before getting started, make sure you have the required software installed on your machine:

| Software | Required Version | Purpose | Link |
| :--- | :--- | :--- | :--- |
| **Python** | `3.10+` (tested on `3.12` & `3.13`) | Core programming language runtime | [python.org](https://www.python.org/downloads/) |
| **Git** | Any modern version | Source code and version control | [git-scm.com](https://git-scm.com/) |
| **uv** *(Recommended)* | `0.10+` | Ultra-fast Python package and project manager | [astral.sh/uv](https://github.com/astral-sh/uv) |
| **Web Browser** | Latest Chrome, Edge, or Firefox | To interact with Streamlit apps and Jupyter Notebook | [google.com/chrome](https://www.google.com/chrome/) |
| **VS Code / Cursor** *(Optional)* | Latest | Recommended IDE with Python & Jupyter extensions | [code.visualstudio.com](https://code.visualstudio.com/) |
| **C++ Build Tools** *(Windows)* | Visual Studio 2022 Build Tools | Required for compiling native extensions (ChromaDB, hnswlib) | [visualstudio.microsoft.com](https://visualstudio.microsoft.com/visual-cpp-build-tools/) |
| **Ollama** *(Optional)* | Latest | Running local offline LLMs (`llama3.2`, `deepseek-r1`) | [ollama.com](https://ollama.com/) |

---

### 1. Installing Required Software by Operating System

#### 🪟 Windows

1. **Using Winget (Fastest & Recommended)**:
   Open **PowerShell** as Administrator and run:
   ```powershell
   # Install Git
   winget install --id Git.Git -e --source winget

   # Install Python (3.12 or 3.13)
   winget install --id Python.Python.3.12 -e --source winget

   # Install uv (Fast package manager)
   powershell -ExecutionPolicy ByPass -c "irm https://astral.sh/uv/install.ps1 | iex"
   ```
2. **Using Direct Installers**:
   - Download Git from [git-scm.com/download/win](https://git-scm.com/download/win).
   - Download Python from [python.org/downloads](https://www.python.org/downloads/).
   - ⚠️ **Important**: During Python installation, make sure to check the box: **"Add python.exe to PATH"**.
   - If compiling vector libraries from source, install the [C++ Build Tools](https://aka.ms/vs/17/release/vs_BuildTools.exe) with "Desktop development with C++" workload.

---

#### 🍎 macOS

1. **Install Homebrew** (if not already installed):
   ```bash
   /bin/bash -c "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/HEAD/install.sh)"
   ```
2. **Install Git, Python & uv via Homebrew**:
   ```bash
   brew update
   brew install git python@3.12 uv
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
curl -LsSf https://astral.sh/uv/install.sh | sh
```

##### **Fedora / RHEL / CentOS**:
```bash
sudo dnf check-update
sudo dnf install -y git python3 python3-pip python3-devel gcc gcc-c++
curl -LsSf https://astral.sh/uv/install.sh | sh
```

##### **Arch Linux / Manjaro**:
```bash
sudo pacman -Syu --noconfirm git python python-pip base-devel
curl -LsSf https://astral.sh/uv/install.sh | sh
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

All dependencies are defined in [`pyproject.toml`](./pyproject.toml) and [`requirements.txt`](./requirements.txt):

| Category | Package | Version | Purpose |
| :--- | :--- | :--- | :--- |
| **Core Framework** | **`langchain`** | `^1.4.7` | High-level orchestration for LLM chains and prompts |
| | **`langchain-core`** | `^1.0.0` | Fundamental abstractions, message schemas, and runnables |
| | **`langchain-community`** | `^0.4.1` | Third-party integrations, document loaders, and tools |
| **Model Drivers** | **`langchain-google-genai`**| `^4.2.2` | Google Gemini models (`gemini-2.5`, `gemini-3.8-flash`) |
| | **`langchain-openai`** | `^1.4.7` | OpenAI (`gpt-4o`, `o3-mini`), OpenRouter, and Ollama |
| | **`langchain-groq`** | `^1.1.3` | Ultra-low latency LPU inference with Groq |
| **Vector DB & Embeddings** | **`chromadb`** | `^1.5.9` | High-performance embedded local vector database |
| | **`langchain-chroma`** | `^1.1.0` | LangChain vector store integration for Chroma |
| | **`sentence-transformers`** | `^6.2.0` | Fast open-source embeddings (e.g., `all-MiniLM-L6-v2`) |
| | **`langchain-huggingface`** | `^1.2.2` | LangChain wrapper for Hugging Face embeddings |
| | **`torchvision`** | `^0.27.0` | PyTorch backend for tensor computation |
| **RAG & Document Tools** | **`langchain-text-splitters`**| `^1.1.2` | Text chunking (`RecursiveCharacterTextSplitter`) |
| | **`pypdf`** | `^6.11.0` | PDF document extraction and page reading |
| | **`fpdf2`** | `^2.8.7` | Programmatic PDF report generation |
| | **`pandas`** | `^3.0.2` | Tabular data analysis and biomarker dataframes |
| | **`pillow`** | `^12.2.0` | Image processing and visual asset handling |
| **Interactive UI & Web** | **`streamlit`** | `^1.57.0` | Powers the reactive, glassmorphic web dashboard |
| **Notebooks & Widgets** | **`notebook`** | `^7.5.6` | Modern web-based interactive computational notebooks |
| | **`ipykernel`** | `^7.2.0` | Jupyter Python execution kernel |
| | **`ipywidgets`** | `^8.1.9` | Rich notebook interactive widgets & download progress bars |
| **Utilities** | **`python-dotenv`** | `^1.2.2` | Securely loads configuration and environment variables |
| | **`requests`** | `^2.31.0` | HTTP requests and API model auto-detection |

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

#### Method A: Ultra-Fast Installation using `uv` (Recommended)
If you have [uv](https://github.com/astral-sh/uv) installed:
```bash
# Sync all project dependencies directly from pyproject.toml:
uv sync
```

#### Method B: Standard Installation via `pip`
```bash
pip install --upgrade pip
pip install -r requirements.txt
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
python -m notebook
```
- Open `1_simple_calling_llm/call_llm.ipynb` to explore foundational LLM calling patterns.
- Open `2_health_analysis/blood_work_analysis.ipynb` to explore clinical biomarker extraction.
- Open `3_vector_db/chroma_vector_db_basics.ipynb` to explore vector database indexing and semantic search.
- Open `4_rag_basics/basic_rag.ipynb` to explore document chunking, retrieval, and RAG pipelines.

---

## 🌟 Modules Overview

### 1️⃣ `1_simple_calling_llm/`
Demonstrates foundational patterns for initializing and calling Large Language Models through standard LangChain interfaces, parameter tuning (`temperature`, `streaming`), and multi-provider connectivity.

### 2️⃣ `2_health_analysis/`
An end-to-end clinical biomarker analysis and tailored functional nutrition engine.

<p align="center">
  <img src="2_health_analysis/streamlit_app/AI%20Blood%20Work%20%26%20Diet%20Analyzer.png" alt="AI Blood Work & Diet Analyzer" width="900" style="border-radius: 12px; box-shadow: 0 8px 30px rgba(0,0,0,0.3);" />
</p>

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

### 3️⃣ `3_vector_db/`
Explores semantic search, embeddings, and vector store operations using **ChromaDB**:
- Creating dense vector representations with `sentence-transformers` (`all-MiniLM-L6-v2`) and HuggingFace.
- Indexing document embeddings into in-memory and persistent Chroma vector collections.
- Executing similarity search queries (`k-nearest neighbors`) with distance metrics.

### 4️⃣ `4_rag_basics/`
Hands-on implementation of **Retrieval-Augmented Generation (RAG)**:
- Loading unstructured documents (PDFs using `pypdf`).
- Chunking strategies using `RecursiveCharacterTextSplitter` with customizable `chunk_size` and `chunk_overlap`.
- Integrating document retrievers with LangChain prompt templates and LLMs to answer domain-specific questions grounded in source data.

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
