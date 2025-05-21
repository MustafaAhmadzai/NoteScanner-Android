# 📷📝 NoteScanner Android App

An Android app that captures handwritten notes using the camera, sends them to a backend for OCR and summarization, and displays a downloadable PDF of the result — powered by Google Cloud Vision and OpenAI.

---

## Features

- **Camera Integration** — Capture notes directly from the device's camera
- **Retrofit + FastAPI** — Send image to a Python backend server
- **OCR + Summarization** — Extract text with Google Vision and summarize with GPT
- **PDF Generation** — Receive a generated PDF with summarized content
- **Secure File Access** — Download and open the PDF using FileProvider
- **Jetpack Compose UI** — Built with modern Android best practices

---

## Tech Stack

| Layer | Tech |
|-------|------|
| Frontend | Kotlin, Jetpack Compose, AndroidX |
| Backend API | [NoteScanner Backend (FastAPI + Python)] (https://github.com/MustafaAhmadzai/NoteScanner) |
| Networking | Retrofit, OkHttp |
| AI Services | Google Cloud Vision OCR, OpenAI API |
| File Handling | FileProvider, Internal Storage, PDF streaming |

---

## How It Works

1. User captures an image using the camera.
2. Image is uploaded to the backend using Retrofit.
3. The backend:
   - Extracts text with OCR
   - Summarizes using GPT
   - Generates a PDF
4. App downloads the PDF and opens it using a secure Intent.

---

## Getting Started

### Prerequisites

- Android Studio Hedgehog or later
- Android device or emulator with camera enabled
- Python backend running locally or hosted  
  → (https://github.com/MustafaAhmadzai/NoteScanner)
