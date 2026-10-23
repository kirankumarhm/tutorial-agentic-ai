import os
import re
import ast
from pathlib import Path
import streamlit as st

# --- Page Configuration (Must be first Streamlit command) ---
st.set_page_config(
    page_title="HemaLens AI - Glassmorphic Blood Work & Diet Analyzer",
    page_icon="🩸",
    layout="wide",
    initial_sidebar_state="expanded",
)

# --- Glassmorphism Aesthetic CSS ---
st.markdown("""
<style>
@import url('https://fonts.googleapis.com/css2?family=Plus+Jakarta+Sans:wght@300;400;500;600;700;800&display=swap');

html, body, [class*="css"] {
    font-family: 'Plus Jakarta Sans', sans-serif;
}

/* Atmospheric Ambient Glow Background */
.stApp {
    background: 
        radial-gradient(circle at 10% 12%, rgba(99, 102, 241, 0.14) 0%, transparent 35%),
        radial-gradient(circle at 88% 18%, rgba(236, 72, 153, 0.10) 0%, transparent 35%),
        radial-gradient(circle at 50% 88%, rgba(16, 185, 129, 0.10) 0%, transparent 45%),
        #090d16 !important;
    color: #f1f5f9;
}

/* Glass Sidebar */
[data-testid="stSidebar"] {
    background: rgba(13, 17, 28, 0.75) !important;
    backdrop-filter: blur(28px) saturate(180%) !important;
    -webkit-backdrop-filter: blur(28px) saturate(180%) !important;
    border-right: 1px solid rgba(255, 255, 255, 0.08) !important;
}

/* Frosted Glass Container / Border Wrapper */
div[data-testid="stVerticalBlock"] > div[data-testid="stVerticalBlockBorderWrapper"] {
    background: rgba(17, 23, 39, 0.55) !important;
    backdrop-filter: blur(20px) saturate(180%) !important;
    -webkit-backdrop-filter: blur(20px) saturate(180%) !important;
    border: 1px solid rgba(255, 255, 255, 0.09) !important;
    border-radius: 18px !important;
    box-shadow: 0 16px 40px 0 rgba(0, 0, 0, 0.45) !important;
    transition: all 0.3s ease;
}

div[data-testid="stVerticalBlock"] > div[data-testid="stVerticalBlockBorderWrapper"]:hover {
    border-color: rgba(129, 140, 248, 0.3) !important;
    box-shadow: 0 20px 50px 0 rgba(99, 102, 241, 0.16) !important;
}

/* Glass Hero Pill Badge */
.hero-pill {
    display: inline-flex;
    align-items: center;
    gap: 8px;
    background: rgba(99, 102, 241, 0.15);
    border: 1px solid rgba(129, 140, 248, 0.35);
    backdrop-filter: blur(12px);
    border-radius: 9999px;
    padding: 6px 14px;
    font-size: 0.78rem;
    font-weight: 700;
    letter-spacing: 0.06em;
    color: #c7d2fe;
    text-transform: uppercase;
    margin-bottom: 8px;
    box-shadow: 0 0 20px rgba(99, 102, 241, 0.22);
}

.pulse-dot {
    width: 8px;
    height: 8px;
    background-color: #10b981;
    border-radius: 50%;
    box-shadow: 0 0 10px #10b981;
    display: inline-block;
}

/* Glowing Neon Pills for Abnormal Test Flags */
.badge-high {
    background: rgba(239, 68, 68, 0.2);
    color: #fca5a5;
    border: 1px solid rgba(239, 68, 68, 0.5);
    box-shadow: 0 0 12px rgba(239, 68, 68, 0.25);
    padding: 3px 8px;
    border-radius: 6px;
    font-weight: 700;
    font-size: 0.78rem;
}

.badge-low {
    background: rgba(245, 158, 11, 0.2);
    color: #fcd34d;
    border: 1px solid rgba(245, 158, 11, 0.5);
    box-shadow: 0 0 12px rgba(245, 158, 11, 0.25);
    padding: 3px 8px;
    border-radius: 6px;
    font-weight: 700;
    font-size: 0.78rem;
}

.badge-normal {
    background: rgba(16, 185, 129, 0.2);
    color: #6ee7b7;
    border: 1px solid rgba(16, 185, 129, 0.5);
    box-shadow: 0 0 12px rgba(16, 185, 129, 0.25);
    padding: 3px 8px;
    border-radius: 6px;
    font-weight: 700;
    font-size: 0.78rem;
}

/* Diet Plan Side-by-Side Glass Cards */
.glass-diet-card-eat {
    background: rgba(16, 185, 129, 0.08);
    border: 1px solid rgba(16, 185, 129, 0.3);
    border-radius: 14px;
    padding: 18px 20px;
    margin-bottom: 12px;
    box-shadow: 0 8px 24px rgba(16, 185, 129, 0.1);
}

.glass-diet-card-avoid {
    background: rgba(239, 68, 68, 0.08);
    border: 1px solid rgba(239, 68, 68, 0.3);
    border-radius: 14px;
    padding: 18px 20px;
    margin-bottom: 12px;
    box-shadow: 0 8px 24px rgba(239, 68, 68, 0.1);
}

/* Glass Metric Summary Box */
.glass-metric {
    background: rgba(255, 255, 255, 0.03);
    border: 1px solid rgba(255, 255, 255, 0.09);
    border-radius: 12px;
    padding: 12px;
    text-align: center;
}
.glass-metric-num {
    font-size: 1.5rem;
    font-weight: 700;
    margin-bottom: 2px;
}
.glass-metric-label {
    font-size: 0.75rem;
    color: #94a3b8;
    text-transform: uppercase;
    letter-spacing: 0.05em;
}

/* Glass Inputs & Text Areas */
.stTextArea textarea {
    background: rgba(13, 18, 32, 0.65) !important;
    backdrop-filter: blur(14px) !important;
    border: 1px solid rgba(255, 255, 255, 0.11) !important;
    border-radius: 14px !important;
    color: #f8fafc !important;
    font-size: 0.92rem !important;
    line-height: 1.65 !important;
    transition: all 0.25s ease !important;
}

.stTextArea textarea:focus {
    border-color: rgba(99, 102, 241, 0.65) !important;
    box-shadow: 0 0 25px rgba(99, 102, 241, 0.25) !important;
}

/* Glass Buttons */
.stButton > button[kind="primary"] {
    background: linear-gradient(135deg, #6366f1 0%, #3b82f6 50%, #06b6d4 100%) !important;
    border: 1px solid rgba(255, 255, 255, 0.2) !important;
    border-radius: 12px !important;
    color: #ffffff !important;
    font-weight: 600 !important;
    letter-spacing: 0.02em !important;
    padding: 0.65rem 1.3rem !important;
    box-shadow: 0 10px 28px rgba(79, 70, 229, 0.35) !important;
    transition: all 0.3s cubic-bezier(0.4, 0, 0.2, 1) !important;
}

.stButton > button[kind="primary"]:hover {
    transform: translateY(-2px) !important;
    box-shadow: 0 14px 34px rgba(79, 70, 229, 0.55) !important;
}

.stButton > button[kind="secondary"] {
    background: rgba(255, 255, 255, 0.04) !important;
    border: 1px solid rgba(255, 255, 255, 0.12) !important;
    backdrop-filter: blur(12px) !important;
    border-radius: 12px !important;
    color: #cbd5e1 !important;
    transition: all 0.25s ease !important;
}

.stButton > button[kind="secondary"]:hover {
    background: rgba(255, 255, 255, 0.09) !important;
    border-color: rgba(255, 255, 255, 0.25) !important;
    color: #ffffff !important;
}

/* Glass Tabs */
.stTabs [data-baseweb="tab-list"] {
    gap: 8px;
    background: rgba(13, 18, 32, 0.55);
    padding: 6px;
    border-radius: 14px;
    border: 1px solid rgba(255, 255, 255, 0.08);
    backdrop-filter: blur(14px);
}

.stTabs [data-baseweb="tab"] {
    border-radius: 10px;
    padding: 8px 16px;
    color: #94a3b8;
    border: none !important;
    transition: all 0.2s ease;
}

.stTabs [aria-selected="true"] {
    background: rgba(99, 102, 241, 0.25) !important;
    color: #ffffff !important;
    font-weight: 600;
    box-shadow: 0 4px 20px rgba(99, 102, 241, 0.3);
    border: 1px solid rgba(129, 140, 248, 0.45) !important;
}

/* Disclaimer Glass Box */
.glass-disclaimer {
    background: rgba(245, 158, 11, 0.07);
    border-left: 3px solid #f59e0b;
    border-radius: 0 12px 12px 0;
    padding: 10px 14px;
    margin-top: 14px;
    font-size: 0.8rem;
    color: #cbd5e1;
    backdrop-filter: blur(10px);
}

/* Provider Tag */
.provider-badge {
    display: inline-block;
    padding: 3px 10px;
    border-radius: 6px;
    font-size: 0.75rem;
    font-weight: 600;
    background: rgba(255, 255, 255, 0.08);
    border: 1px solid rgba(255, 255, 255, 0.15);
    color: #93c5fd;
}
</style>
""", unsafe_allow_html=True)

# --- Pure Text Cleaning & De-Signaturing Engine ---
def clean_and_extract_text(raw_response) -> str:
    """
    Guarantees pure, clean text extraction from any LLM response,
    completely stripping all dictionary wrappers, digital signatures, and extras metadata.
    """
    if raw_response is None:
        return ""

    content = getattr(raw_response, "content", raw_response)

    # 1. Handle list format (e.g. Gemini 3.8/2.5 returning [{'type': 'text', 'text': '...', 'extras': {...}}])
    if isinstance(content, list):
        text_chunks = []
        for item in content:
            if isinstance(item, dict):
                if "text" in item and isinstance(item["text"], str):
                    text_chunks.append(item["text"])
                elif "content" in item and isinstance(item["content"], str):
                    text_chunks.append(item["content"])
            elif isinstance(item, str):
                text_chunks.append(item)
            elif hasattr(item, "text"):
                text_chunks.append(str(item.text))
        final_text = "\n".join(text_chunks).strip()
        if final_text:
            return _strip_signatures(final_text)

    # 2. Handle dict format
    if isinstance(content, dict):
        if "text" in content and isinstance(content["text"], str):
            return _strip_signatures(content["text"])
        if "content" in content and isinstance(content["content"], str):
            return _strip_signatures(content["content"])

    text_val = str(content).strip()

    # 3. Handle stringified list of dicts: "[{'type': 'text', ...}]"
    if text_val.startswith("[{'type'") or text_val.startswith("[{\"type\""):
        try:
            parsed = ast.literal_eval(text_val)
            if isinstance(parsed, list):
                chunks = [item.get("text", "") for item in parsed if isinstance(item, dict) and "text" in item]
                if chunks:
                    return _strip_signatures("\n".join(chunks).strip())
        except Exception:
            pass

    return _strip_signatures(text_val)

def _strip_signatures(text: str) -> str:
    """Removes leftover signatures or metadata blocks if present as raw strings."""
    cleaned = re.sub(r",?\s*['\"]extras['\"]\s*:\s*\{[^}]*\}", "", text)
    cleaned = re.sub(r",?\s*['\"]signature['\"]\s*:\s*['\"][^'\"]*['\"]", "", cleaned)
    if cleaned.startswith("[{'type': 'text', 'text': '") and cleaned.endswith("'}]"):
        cleaned = cleaned[len("[{'type': 'text', 'text': '"):-len("'}]")].replace("\\n", "\n")
    return cleaned.strip()

def parse_clinical_sections(full_text: str):
    """
    Robust regex splitting of Section 1 (Health Summary), Section 2 (Diet Plan),
    and Section 3 (Doctor Questions).
    """
    cleaned = clean_and_extract_text(full_text)
    
    pattern_sec2 = re.compile(r'(?:#{1,4}\s*|\*{0,2})SECTION\s*2\s*[:\-–]\s*(?:INDIAN\s*DIET[^\n]*)?', re.IGNORECASE)
    pattern_sec3 = re.compile(r'(?:#{1,4}\s*|\*{0,2})SECTION\s*3\s*[:\-–]\s*(?:QUESTIONS[^\n]*)?', re.IGNORECASE)
    
    sec1_match = re.split(pattern_sec2, cleaned, maxsplit=1)
    
    if len(sec1_match) == 2:
        sec1_raw = sec1_match[0]
        health_summary = re.sub(r'^(?:#{1,4}\s*|\*{0,2})SECTION\s*1\s*[:\-–]\s*(?:CLINICAL\s*HEALTH\s*SUMMARY[^\n]*)?\n*', '', sec1_raw, flags=re.IGNORECASE).strip(' -\n\r')
        
        rem = sec1_match[1]
        sec2_match = re.split(pattern_sec3, rem, maxsplit=1)
        if len(sec2_match) == 2:
            diet_plan = sec2_match[0].strip(' -\n\r')
            doctor_questions = sec2_match[1].strip(' -\n\r')
        else:
            diet_plan = rem.strip(' -\n\r')
            doctor_questions = ""
    else:
        health_summary = cleaned
        diet_plan = ""
        doctor_questions = ""
        
    return health_summary, diet_plan, doctor_questions

def parse_diet_subsections(diet_text: str):
    """Splits Section 2 into Foods to Prioritize vs Foods to Restrict."""
    eat_pattern = re.compile(r'(?:#{1,4}\s*|\*{0,2})(?:Foods\s*(?:&|and)?\s*Pantry\s*Staples\s*to\s*Prioritize|Foods\s*to\s*Prioritize|Foods\s*to\s*Eat[^\n]*)', re.IGNORECASE)
    avoid_pattern = re.compile(r'(?:#{1,4}\s*|\*{0,2})(?:Foods\s*(?:&|and)?\s*Habits\s*to\s*Restrict|Foods\s*to\s*Avoid|Foods\s*to\s*Restrict[^\n]*)', re.IGNORECASE)
    
    parts = re.split(avoid_pattern, diet_text, maxsplit=1)
    if len(parts) == 2:
        eat_part = re.sub(eat_pattern, '', parts[0]).strip(' -\n\r')
        avoid_part = parts[1].strip(' -\n\r')
        return eat_part, avoid_part
    return diet_text, ""

# --- Default Sample Blood Work (Reads from local file if available) ---
DEFAULT_SAMPLE = """Patient: Rajesh Sharma, Age 48, Male
Date: May 7, 2026

COMPLETE BLOOD COUNT (CBC)
--------------------------
Hemoglobin:        15.1 g/dL        (Normal: 13.5–17.5)
Hematocrit:        44%              (Normal: 41–53%)
WBC:               6.8 x10^3/uL     (Normal: 4.5–11.0)
Platelets:         220 x10^3/uL     (Normal: 150–400)

LIPID PANEL
-----------
Total Cholesterol: 238 mg/dL        (Normal: <200)   [HIGH]
LDL Cholesterol:   162 mg/dL        (Normal: <100)   [HIGH]
HDL Cholesterol:   36 mg/dL         (Normal: >40)    [LOW]
Triglycerides:     188 mg/dL        (Normal: <150)   [HIGH]

METABOLIC PANEL
---------------
Glucose (Fasting): 92 mg/dL         (Normal: 70–99)
HbA1c:             5.3%             (Normal: <5.7%)
Creatinine:        1.0 mg/dL        (Normal: 0.7–1.3)
eGFR:              82 mL/min        (Normal: >60)

LIVER FUNCTION
--------------
ALT:               28 U/L           (Normal: 7–40)
AST:               25 U/L           (Normal: 10–40)
Bilirubin Total:   0.8 mg/dL        (Normal: 0.2–1.2)
"""

local_txt_path = Path(__file__).resolve().parent.parent / "blood_work.txt"
if local_txt_path.exists():
    try:
        DEFAULT_SAMPLE = local_txt_path.read_text(encoding="utf-8")
    except Exception:
        pass

# Initialize Session State
if "extracted_values" not in st.session_state:
    st.session_state.extracted_values = None
if "health_summary" not in st.session_state:
    st.session_state.health_summary = None
if "diet_plan" not in st.session_state:
    st.session_state.diet_plan = None
if "doctor_questions" not in st.session_state:
    st.session_state.doctor_questions = None

# Sanitize legacy session state strings
for k in ["extracted_values", "health_summary", "diet_plan", "doctor_questions"]:
    if st.session_state.get(k):
        st.session_state[k] = clean_and_extract_text(st.session_state[k])

# --- Model Agnostic Factory (Strictly uses user-supplied key) ---
def create_model_agnostic_llm(provider: str, model_name: str, api_key: str, base_url: str = None, temperature: float = 0.2):
    """
    Factory function instantiating standard LangChain Chat Models.
    Strictly accepts runtime user API key with no fallback to .env.
    """
    clean_model = model_name.strip()
    clean_key = api_key.strip() if api_key else ""

    if provider == "Google Gemini":
        from langchain_google_genai import ChatGoogleGenerativeAI
        if not clean_key:
            raise ValueError("Google Gemini API Key is required. Please paste your key in the sidebar.")
        return ChatGoogleGenerativeAI(
            model=clean_model,
            google_api_key=clean_key,
            temperature=temperature
        )

    elif provider == "OpenAI":
        from langchain_openai import ChatOpenAI
        if not clean_key:
            raise ValueError("OpenAI API Key is required. Please paste your key in the sidebar.")
        return ChatOpenAI(
            model=clean_model,
            api_key=clean_key,
            temperature=temperature
        )

    elif provider == "Groq":
        from langchain_groq import ChatGroq
        if not clean_key:
            raise ValueError("Groq API Key is required. Please paste your key in the sidebar.")
        return ChatGroq(
            model=clean_model,
            api_key=clean_key,
            temperature=temperature
        )

    elif provider == "OpenRouter":
        from langchain_openai import ChatOpenAI
        if not clean_key:
            raise ValueError("OpenRouter API Key is required. Please paste your key in the sidebar.")
        return ChatOpenAI(
            model=clean_model,
            api_key=clean_key,
            base_url="https://openrouter.ai/api/v1",
            temperature=temperature
        )

    elif provider == "Ollama (Local)":
        from langchain_openai import ChatOpenAI
        endpoint = base_url.strip() if (base_url and base_url.strip()) else "http://localhost:11434/v1"
        return ChatOpenAI(
            model=clean_model,
            api_key="ollama",
            base_url=endpoint,
            temperature=temperature
        )

    elif provider == "Custom OpenAI-Compatible":
        from langchain_openai import ChatOpenAI
        if not base_url or not base_url.strip():
            raise ValueError("Base URL is required for Custom OpenAI-Compatible endpoint.")
        return ChatOpenAI(
            model=clean_model,
            api_key=clean_key or "custom",
            base_url=base_url.strip(),
            temperature=temperature
        )

    else:
        raise ValueError(f"Unknown provider: {provider}")

# --- Provider Presets Dictionary ---
PROVIDER_PRESETS = {
    "Google Gemini": {
        "models": ["gemini-3.8-flash", "gemini-3.8-pro", "gemini-2.5-flash", "gemma-4-31b-it"],
        "placeholder": "AIzaSy...",
        "doc": "Google AI Studio API Key"
    },
    "OpenAI": {
        "models": ["gpt-4o", "gpt-4o-mini", "o3-mini", "o1-mini", "gpt-4.5-preview"],
        "placeholder": "sk-proj-...",
        "doc": "OpenAI Developer API Key"
    },
    "Groq": {
        "models": ["llama-3.3-70b-versatile", "llama-3.1-8b-instant", "mixtral-8x7b-32768", "gemma2-9b-it"],
        "placeholder": "gsk_...",
        "doc": "Groq Console API Key (Fast LPU inference)"
    },
    "OpenRouter": {
        "models": [
            "google/gemini-2.5-flash",
            "anthropic/claude-3.5-sonnet",
            "meta-llama/llama-3.3-70b-instruct",
            "deepseek/deepseek-chat"
        ],
        "placeholder": "sk-or-v1-...",
        "doc": "OpenRouter API Key (200+ models)"
    },
    "Ollama (Local)": {
        "models": ["llama3.2", "mistral", "deepseek-r1", "qwen2.5:7b"],
        "placeholder": "Local (No Key Required)",
        "doc": "Local offline models via Ollama"
    },
    "Custom OpenAI-Compatible": {
        "models": ["deepseek-chat", "mistral-large", "custom-model"],
        "placeholder": "Enter custom API key...",
        "doc": "vLLM, LM Studio, DeepSeek, or Together AI"
    }
}

# --- Sidebar Configuration ---
with st.sidebar:
    st.markdown('<div class="hero-pill"><span class="pulse-dot"></span> Model Agnostic Engine</div>', unsafe_allow_html=True)
    st.title("⚙️ AI Provider Setup")

    # 1. Select Provider
    selected_provider = st.selectbox(
        "AI Provider",
        options=list(PROVIDER_PRESETS.keys()),
        index=0,
        help="Select any AI provider or local inference engine."
    )
    provider_info = PROVIDER_PRESETS[selected_provider]

    # 2. API Key handling: Always starts BLANK (no .env loading)
    if selected_provider == "Ollama (Local)":
        user_api_key = "ollama"
        custom_base_url = st.text_input("Ollama Base URL", value="http://localhost:11434/v1")
        st.info("ℹ️ Ollama runs locally on your machine. No API key required.")
    elif selected_provider == "Custom OpenAI-Compatible":
        custom_base_url = st.text_input("API Base URL", placeholder="e.g. https://api.deepseek.com/v1")
        user_api_key = st.text_input(
            "API Key (Optional)",
            value="",
            type="password",
            placeholder=provider_info["placeholder"]
        )
    else:
        custom_base_url = None
        user_api_key = st.text_input(
            f"{selected_provider} API Key",
            value="",
            type="password",
            placeholder=provider_info["placeholder"],
            help=f"Paste your {selected_provider} API key here."
        )
        if not user_api_key.strip():
            st.caption("🔒 Key is currently empty. Paste your key above to proceed.")
        else:
            st.caption("✅ Key entered.")

    # 3. Model Selection
    preset_models = provider_info["models"]
    model_choice = st.selectbox(
        "Choose Preset Model",
        options=preset_models,
        index=0
    )

    custom_model_input = st.text_input(
        "Or type custom model name (optional):",
        placeholder=f"e.g. {preset_models[0]}"
    )
    active_model = custom_model_input.strip() if custom_model_input.strip() else model_choice

    st.markdown(f'<span class="provider-badge">⚡ {selected_provider} &bull; {active_model}</span>', unsafe_allow_html=True)

    st.divider()

    # 4. Nutrition / Dietary Preferences
    st.subheader("🥗 Dietary Profile")
    dietary_pref = st.selectbox(
        "Indian Diet Preference",
        options=["Vegetarian (Pure)", "Eggetarian", "Non-Vegetarian"],
        index=0,
        help="Tailors grain, dal, spice, and protein interventions to dietary rules."
    )

    st.divider()

    # 5. Quick Demo Sample
    st.subheader("🧪 Quick Demo")
    if st.button("📋 Load Sample Blood Report", use_container_width=True):
        st.session_state["report_input"] = DEFAULT_SAMPLE

    st.markdown("""
    <div class="glass-disclaimer">
        <b>Clinical Notice:</b> This tool uses LLMs for exploratory biomarker triage and Indian functional nutrition insights. Always consult your doctor for formal diagnosis or therapy.
    </div>
    """, unsafe_allow_html=True)

# --- Header Section ---
col_head1, col_head2 = st.columns([3, 1])
with col_head1:
    st.markdown('<div class="hero-pill"><span class="pulse-dot"></span> Clinical Intelligence &bull; Glassmorphic UI</div>', unsafe_allow_html=True)
    st.title("🩸 Blood Work & Functional Nutrition AI")
    st.caption("Universal biomarker classification, reference range triage, and personalized Indian dietary strategies.")

st.divider()

# --- Main Layout ---
left_col, right_col = st.columns([1, 1], gap="large")

with left_col:
    st.subheader("📄 Blood Work Input")
    
    default_text = st.session_state.get("report_input", "")
    blood_report = st.text_area(
        label="Paste Lab Report",
        value=default_text,
        height=470,
        placeholder="Paste your lab results here (CBC, Lipid, Metabolic panel, Liver enzymes, etc.)...",
        label_visibility="collapsed"
    )
    
    c1, c2 = st.columns([2, 1])
    with c1:
        analyze_clicked = st.button("🚀 Analyze Biomarkers", type="primary", use_container_width=True)
    with c2:
        if st.button("Clear Input", use_container_width=True):
            st.session_state["report_input"] = ""
            st.session_state.extracted_values = None
            st.session_state.health_summary = None
            st.session_state.diet_plan = None
            st.session_state.doctor_questions = None
            st.rerun()

# --- Execution Logic & Strict Validation ---
if analyze_clicked:
    # Validation 1: Blood report input
    if not blood_report.strip():
        st.warning("⚠️ **Report Missing**: Please paste or load a blood work report on the left first.")
    
    # Validation 2: API key validation (No key -> No operation -> Notify user)
    elif selected_provider != "Ollama (Local)" and not user_api_key.strip():
        st.error(
            f"🔑 **API Key Required**: Please enter your **{selected_provider} API Key** in the left sidebar to proceed with analysis.\n\n"
            f"The key is kept in memory during this session and is not stored."
        )

    # Validation 3: Custom endpoint validation
    elif selected_provider == "Custom OpenAI-Compatible" and not (custom_base_url and custom_base_url.strip()):
        st.error("🌐 **Base URL Required**: Please provide a valid Base URL for your custom OpenAI-compatible endpoint.")

    else:
        try:
            # Build provider-agnostic model instance with explicit user key
            llm = create_model_agnostic_llm(
                provider=selected_provider,
                model_name=active_model,
                api_key=user_api_key.strip(),
                base_url=custom_base_url,
                temperature=0.2
            )

            with st.status(f"🔬 Analyzing with {selected_provider} ({active_model})...", expanded=True) as status:
                st.write("Step 1: Extracting biomarkers and evaluating reference ranges...")
                extraction_prompt = f"""
You are an expert clinical laboratory data assistant.
From the blood report below:
1. Extract ALL test biomarkers, their numeric values, units, and reference ranges.
2. Classify each as HIGH, LOW, or NORMAL based on standard reference ranges.
3. Return STRICTLY a clean Markdown table with these exact headers:
| Biomarker | Result & Units | Reference Range | Status | Clinical Notes |

In the Status column, write ONLY one of: HIGH, LOW, or NORMAL.

Blood Report:
{blood_report}
"""
                extraction_response = llm.invoke(extraction_prompt)
                st.session_state.extracted_values = clean_and_extract_text(extraction_response)

                st.write("Step 2: Synthesizing metabolic health condition and tailored Indian nutrition plan...")
                diet_prompt = f"""
You are an expert clinical nutritionist and functional medicine consultant specializing in Indian dietary patterns.
The patient adheres to a {dietary_pref} diet.

Review this blood work analysis:
{st.session_state.extracted_values}

Provide your analysis structured strictly with the three following markdown headers:

### SECTION 1: CLINICAL HEALTH SUMMARY
Write 4-5 concise, empathetic bullet points explaining the patient's metabolic and health state in plain, jargon-free language. Mention the primary areas of concern (e.g. lipid profile, pre-diabetes, anemia, or vitamin deficiencies).

### SECTION 2: INDIAN DIET & NUTRITIONAL PLAN
Format this into two distinct subsections:
#### Foods & Pantry Staples to Prioritize (Eat More)
Name specific Indian ingredients (e.g., methi seeds, ragi/nachni, chana dal, sprouted moong, turmeric, amla, curry leaves). Explain why it helps their specific abnormal biomarkers.

#### Foods & Habits to Restrict/Avoid (Cut Down)
Specify common Indian preparations to avoid (e.g., vanaspati/refined oils, fried farsan, maida, white rice overload, sugary chai/sweets).

### SECTION 3: QUESTIONS FOR YOUR DOCTOR
Provide 3 targeted, high-value questions the patient should ask their primary care physician during their next visit.
"""
                diet_response = llm.invoke(diet_prompt)
                raw_diet_text = clean_and_extract_text(diet_response)

                # Parse the response cleanly into 3 distinct sections
                h_sum, d_plan, d_ques = parse_clinical_sections(raw_diet_text)
                st.session_state.health_summary = h_sum
                st.session_state.diet_plan = d_plan
                st.session_state.doctor_questions = d_ques

                status.update(label=f"✅ Analysis complete with {active_model}!", state="complete", expanded=False)

        except Exception as e:
            err_msg = str(e)
            
            # Friendly Error Handling for 429 Quota Exhaustion
            if "credit_balance_exhausted" in err_msg or "insufficient_quota" in err_msg or "429" in err_msg:
                st.error(
                    f"❌ **API Quota Exhausted (429 - Insufficient Balance)**\n\n"
                    f"The **{selected_provider}** API key you entered has no credits remaining on its billing account.\n\n"
                    f"**Suggested Options:**\n"
                    f"- Check your billing account at your provider's console.\n"
                    f"- Or switch providers in the sidebar: **Google Gemini**, **Groq** (free tier with fast inference), or **Ollama (Local)**."
                )
            elif "401" in err_msg or "invalid_api_key" in err_msg or "API_KEY_INVALID" in err_msg:
                st.error(
                    f"❌ **Invalid API Key (401)**\n\n"
                    f"The API key you entered for **{selected_provider}** was rejected as invalid. Please check and re-paste your key."
                )
            elif "404" in err_msg or "NOT_FOUND" in err_msg:
                st.error(
                    f"❌ **Model Not Found (404)**\n\n"
                    f"Model `{active_model}` is not accessible with this API key. Please select a different preset model from the dropdown."
                )
            else:
                st.error(f"❌ **Analysis Error**: {err_msg}")

# --- Results Presentation ---
with right_col:
    st.subheader("📊 Clinical Insights")

    if st.session_state.health_summary:
        tab_summary, tab_biomarkers, tab_diet, tab_questions = st.tabs([
            "🩺 Health Overview",
            "🔬 Biomarkers Table",
            "🥗 Indian Diet Plan",
            "💬 Doctor Discussion"
        ])

        # TAB 1: Health Overview
        with tab_summary:
            with st.container(border=True):
                st.markdown("### 💡 Key Metabolic Findings")
                clean_overview = re.sub(r'^(?:#{1,4}\s*|\*{0,2})SECTION\s*1[^\n]*\n*', '', st.session_state.health_summary, flags=re.IGNORECASE).strip()
                st.markdown(clean_overview)

        # TAB 2: Biomarkers Table & Metric Tally
        with tab_biomarkers:
            with st.container(border=True):
                st.markdown("### 📋 Biomarker Reference Triage")
                
                table_content = st.session_state.extracted_values or ""
                high_count = len(re.findall(r'\|\s*HIGH\s*\|', table_content, re.IGNORECASE))
                low_count = len(re.findall(r'\|\s*LOW\s*\|', table_content, re.IGNORECASE))
                normal_count = len(re.findall(r'\|\s*NORMAL\s*\|', table_content, re.IGNORECASE))
                total_count = high_count + low_count + normal_count

                if total_count > 0:
                    m1, m2, m3, m4 = st.columns(4)
                    with m1:
                        st.markdown(f'<div class="glass-metric"><div class="glass-metric-num" style="color: #93c5fd;">{total_count}</div><div class="glass-metric-label">Analyzed</div></div>', unsafe_allow_html=True)
                    with m2:
                        st.markdown(f'<div class="glass-metric"><div class="glass-metric-num" style="color: #fca5a5;">{high_count}</div><div class="glass-metric-label">Elevated (High)</div></div>', unsafe_allow_html=True)
                    with m3:
                        st.markdown(f'<div class="glass-metric"><div class="glass-metric-num" style="color: #fcd34d;">{low_count}</div><div class="glass-metric-label">Low / Deficit</div></div>', unsafe_allow_html=True)
                    with m4:
                        st.markdown(f'<div class="glass-metric"><div class="glass-metric-num" style="color: #6ee7b7;">{normal_count}</div><div class="glass-metric-label">Optimal</div></div>', unsafe_allow_html=True)
                    st.write("")

                # Format table with neon badges
                formatted_table = re.sub(r'\|\s*HIGH\s*\|', r'| <span class="badge-high">HIGH</span> |', table_content, flags=re.IGNORECASE)
                formatted_table = re.sub(r'\|\s*LOW\s*\|', r'| <span class="badge-low">LOW</span> |', formatted_table, flags=re.IGNORECASE)
                formatted_table = re.sub(r'\|\s*NORMAL\s*\|', r'| <span class="badge-normal">NORMAL</span> |', formatted_table, flags=re.IGNORECASE)
                st.markdown(formatted_table, unsafe_allow_html=True)

        # TAB 3: Indian Diet Plan (Side-by-Side Eat vs Avoid Comparison)
        with tab_diet:
            with st.container(border=True):
                st.markdown(f"### 🍛 Tailored Indian Nutrition ({dietary_pref})")
                
                eat_text, avoid_text = parse_diet_subsections(st.session_state.diet_plan or "")
                
                if eat_text and avoid_text:
                    col_eat, col_avoid = st.columns(2, gap="medium")
                    with col_eat:
                        st.markdown("""
                        <div class="glass-diet-card-eat">
                            <h4 style="color: #6ee7b7; margin-top: 0;">🥗 Foods & Pantry Staples to Prioritize</h4>
                        </div>
                        """, unsafe_allow_html=True)
                        st.markdown(eat_text)

                    with col_avoid:
                        st.markdown("""
                        <div class="glass-diet-card-avoid">
                            <h4 style="color: #fca5a5; margin-top: 0;">🚫 Foods & Habits to Restrict</h4>
                        </div>
                        """, unsafe_allow_html=True)
                        st.markdown(avoid_text)
                else:
                    st.markdown(st.session_state.diet_plan or "No diet plan generated.")

        # TAB 4: Doctor Questions
        with tab_questions:
            with st.container(border=True):
                st.markdown("### 🩺 High-Value Questions for Your Physician")
                if st.session_state.doctor_questions:
                    clean_questions = re.sub(r'^(?:#{1,4}\s*|\*{0,2})SECTION\s*3[^\n]*\n*', '', st.session_state.doctor_questions, flags=re.IGNORECASE).strip()
                    st.markdown(clean_questions)
                else:
                    st.info("No specific questions generated.")

        # Download Report Option
        full_export = f"""# BLOOD WORK & CLINICAL REPORT SUMMARY
Provider: {selected_provider} ({active_model})
Dietary Preference: {dietary_pref}

## 1. HEALTH SUMMARY
{st.session_state.health_summary}

## 2. LAB BIOMARKERS
{st.session_state.extracted_values}

## 3. INDIAN DIET PLAN
{st.session_state.diet_plan}

## 4. QUESTIONS FOR YOUR PHYSICIAN
{st.session_state.doctor_questions}

---
*Disclaimer: Generated for educational purposes only. Always consult a physician.*
"""
        st.download_button(
            label="📥 Download Full Report (Markdown)",
            data=full_export,
            file_name="blood_work_analysis.md",
            mime="text/markdown",
            use_container_width=True
        )
    else:
        with st.container(border=True):
            st.info("👈 Paste your report on the left or click **'Load Sample Blood Report'** in the sidebar to run analysis.")
            st.markdown("""
            **What this AI provides:**
            - **Multi-Provider Flexibility**: Switch between Google Gemini, OpenAI, Groq, OpenRouter, or Local Ollama.
            - **Automated Biomarker Triage**: Fast classification of High, Low, and Normal ranges with neon badges.
            - **Side-by-Side Desi Nutritional Strategy**: Comparison of what to eat more of vs what to restrict.
            - **Physician Ready**: Prepared questions to optimize your clinic appointment.
            """)
