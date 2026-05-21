# Sehat Sathi — AI-Powered Healthcare Assistant

A comprehensive Android healthcare app delivering **AI medical chat**, **live video consultations with doctors**, **emergency assistance**, **digital health records**, and **medicine inventory tracking** — all in your native language.

> _"Sehat Sathi" (सेहत साथी) — Hindi for "Health Companion"_

> **Status:** Active development — all core flows implemented (auth, chatbot, video calls, appointments, health records, medicine stock).

---

## ✨ Features

### 🤖 AI-Powered Healthcare Chatbot
- Real-time medical Q&A powered by **Firebase AI / Gemini**
- Symptom triage and basic medical guidance
- Conversational UI that's friendly to non-technical users

### 📹 Telemedicine — Live Video Consultations
- One-tap **video calls with doctors** via **ZegoCloud SDK**
- "Join Meet" flow for patient-side connection
- Real-time audio/video with low-latency streaming

### 👨‍⚕️ Doctor Appointment System
- Browse available doctors and their schedules
- View doctor availability slots
- Book consultations directly from the app

### 🚨 Emergency Screen
- Quick-access emergency contact / SOS UI
- Designed for high-stress, low-time scenarios

### 📋 Digital Health Records
- Store and view medical reports as PDFs (in-app PDF viewer)
- Persisted via Firebase Realtime Database
- Categorised, searchable history

### 💊 Medicine Inventory Tracking
- Add, view, and update medicine stock
- Useful for clinics, pharmacies, or personal medication tracking
- Real-time sync via Firebase

### 🌐 Multi-Language Support
- Dedicated `LanguageScreen` for selecting preferred language
- Designed for Bharat-first audiences with regional language readiness

### 👤 Role-Based Authentication
- Two distinct logins: **User** (`Login.kt`) and **Admin** (`LoginAdmin.kt`)
- Tab-based sign-up flow
- Persistent user profiles via Firebase Auth + Database

---

## 🛠 Tech Stack

| Layer | Technology |
|------|------------|
| Language | **Kotlin** (JVM 11) |
| UI | **Jetpack Compose**, Material 3, Material Icons Extended |
| Architecture | MVVM, single-Activity Compose, role-based navigation |
| DI | **Hilt** (Dagger) + KSP code generation |
| Backend | **Firebase** — Auth, Realtime Database, **Firebase AI (Gemini)** |
| Video Calls | **ZegoCloud** UIKit Prebuilt Call SDK |
| Network | Retrofit + OkHttp + Gson |
| Serialization | Kotlinx Serialization (plugin) |
| Images | Coil |
| PDF Rendering | Bouquet |
| Async | Kotlin Coroutines + Flow + LiveData (Compose runtime) |
| Splash | AndroidX Core Splash Screen API |

---

## 📁 Project Structure

```
app/src/main/java/com/example/sehattsathi/
├── MainActivity.kt                 ← Single Activity, hosts Compose UI
├── ConferenceActivity.kt           ← ZegoCloud video-call host activity
│
├── common/
│   ├── Const.kt                    ← App-wide constants
│   └── ResultState.kt              ← Sealed class for Loading/Success/Error
│
├── models/
│   └── MedicineModel.kt            ← Data class for medicine inventory
│
├── navigation/
│   ├── AppNavigation.kt            ← NavHost + role-based routing
│   └── Routes.kt                   ← Route constants
│
├── repo/
│   ├── Repo.kt                     ← Generic repository interface
│   ├── firebaseRepo.kt             ← Firebase implementation
│   └── MedicineStock.kt            ← Medicine inventory repository
│
├── screens/
│   ├── Login.kt / LoginAdmin.kt    ← Role-split authentication
│   ├── Signup.kt + Tab UI          ← New user registration
│   ├── HomeScreen + BottomNavbar   ← Main hub
│   ├── ProfileScreen               ← User profile
│   ├── LanguageScreen              ← Multi-language picker
│   ├── Chatbot + SehatSathiChatBot ← AI medical chat (Firebase AI)
│   ├── DoctorAppointment           ← Booking flow
│   ├── DoctorAvailability          ← Doctor schedule
│   ├── JoinMeet                    ← Telemedicine entry
│   ├── HealthRecords               ← PDF medical record viewer
│   └── EmergencyScreen             ← SOS / emergency
│
├── viewmodel/
│   └── MyViewModel.kt              ← Application state & business logic
│
└── ui/theme/                       ← Material 3 theme + typography
```

---

## 🚀 Getting Started

### Prerequisites

- **Android Studio** Hedgehog (2023.1.1) or newer
- **JDK 11**
- **Min SDK 26** (Android 8.0), **Compile SDK 36**

### Setup

1. **Clone the repo**
   ```bash
   git clone https://github.com/gurpreet-rrr/sehattsathi.git
   cd sehattsathi
   ```

2. **Configure Firebase** (required)
   - Create a project at [console.firebase.google.com](https://console.firebase.google.com)
   - Enable **Authentication** (Email/Password), **Realtime Database**, and **Firebase AI / Gemini**
   - Download `google-services.json` and place it in `app/`
   - The bundled `google-services.json` is for the developer's demo project — replace with your own before building.

3. **Configure ZegoCloud** (for video calls)
   - Sign up at [zegocloud.com](https://www.zegocloud.com/)
   - Create a project and get `AppID` + `AppSign`
   - Wire them into the `ConferenceActivity` initialisation

4. **Open in Android Studio** → Sync Gradle → Run ▶️

---

## 📸 Screenshots

> _Add screenshots once you take them — replace these placeholders with images in a `screenshots/` folder._

| Login | Home | Chatbot |
|---|---|---|
| _coming soon_ | _coming soon_ | _coming soon_ |

| Doctor Appointment | Video Call | Health Records |
|---|---|---|
| _coming soon_ | _coming soon_ | _coming soon_ |

| Emergency | Medicine Stock | Profile |
|---|---|---|
| _coming soon_ | _coming soon_ | _coming soon_ |

---

## 🗺 Roadmap

### Implemented ✅
- Email/password auth with separate User and Admin flows
- Tab-based login + signup UI
- AI medical chatbot powered by Firebase AI (Gemini)
- Doctor appointment booking + availability view
- Live video consultations with doctors (ZegoCloud)
- Emergency screen
- Health records with in-app PDF viewer
- Medicine inventory CRUD
- Multi-language support (UI scaffold)
- Bottom navigation across main screens

### Planned 🔜
- Doctor-side dashboard (admin can review and accept appointments)
- Push notifications for appointment reminders (FCM)
- Translation backend wiring for full multi-language
- Health-records cloud storage with categorisation tags
- Pharmacy stock alerts on low-quantity thresholds
- Reviews and ratings for doctors

---

## 🔒 Security Notes

- `google-services.json` is committed for ease of setup — replace before production. Firebase Security Rules protect data, not the config file.
- ZegoCloud credentials should NEVER be committed — use BuildConfig or `local.properties` to inject them at build time.
- Signing keys (`*.jks`, `*.keystore`) and `keystore.properties` are explicitly excluded from version control via `.gitignore`.

---

## 👤 Author

**Gurpreet Chugh**
B.Tech CSE — IK Gujral Punjab Technical University, Kapurthala

- GitHub: [@gurpreet-rrr](https://github.com/gurpreet-rrr)
- Email: gurpjhg@gmail.com

---

## 📄 License

This project is for educational and portfolio purposes. Feel free to study the code, but please don't republish as-is.
