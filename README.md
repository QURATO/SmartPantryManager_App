# Smart Pantry Manager

A native Android application written in Java designed to reduce household food waste. The app tracks on-hand pantry inventory and suggests recipes based strictly on available ingredients—requiring zero shopping trips.

---

## 📱 Features

* **Pantry Management:** Full CRUD functionality (Create, Read, Update, Delete) to track ingredient names, quantities, units, and expiry dates.
* **Strict Recipe Matching:** Core business logic that suggests recipes only when 100% of required ingredients are present in the pantry in sufficient quantities.
* **User Feedback:** Clear empty-state messaging when no recipes match current inventory.
* **Preferences & Settings:** Toggle switch configuration for expiration date alerts.
* **Data Persistence:** Offline local data persistence using SQLite.

---

## 🛠️ Tech Stack & Database Choice

* **Language:** Java (Android SDK)
* **IDE:** Android Studio
* **Database:** SQLite (`SQLiteOpenHelper`)
* **UI Components:** `RecyclerView`, Custom Adapters, Explicit `Intents`, `SwitchCompat`

### Database Justification
SQLite was selected for local on-device persistence. For a household pantry manager, on-device storage operates completely offline without network latency, provides instant transaction times, protects user privacy, and incurs zero hosting overhead compared to cloud solutions like Firebase or PostgreSQL.

---

## 🚀 Setup & Installation Instructions

1. **Clone the Repository:**
   ```bash
   git clone [https://github.com/QURATO/SmartPantryManager_App.git](https://github.com/QURATO/SmartPantryManager_App.git)
