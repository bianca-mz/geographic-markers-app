# 📍 Geographic Markers — Android App

A native Android application built with **Kotlin** and **Jetpack Compose** for managing geographic markers on an interactive map. Users can create, view, edit, and delete custom markers with images, all stored in the cloud via Supabase.

> Built as a personal project to integrate Google Maps, cloud persistence, and image storage with modern Android architecture.

---

## 📸 Screenshots

*Coming soon*

---

## ✨ Features

- 🗺️ **Interactive map** — Google Maps with dynamically loaded markers from the database
- ➕ **Create markers** — Long press any point on the map to create a marker with title, description, and optional image
- ✏️ **Edit markers** — Update title, description, and image; changes sync to Supabase in real time
- 🗑️ **Delete markers** — Swipe-to-delete gesture from the list view; removes from remote database
- 🖼️ **Image system** — Pick from gallery → convert to ByteArray → upload to Supabase Storage → store public URL
- 📌 **Marker preview** — Tap any marker on the map to see an info card with image, title, description, and coordinates

---

## 🛠 Tech Stack

| Layer | Technology |
|-------|-----------|
| Language | Kotlin |
| UI | Jetpack Compose · Material Design 3 |
| Navigation | Navigation Compose |
| Architecture | MVVM · ViewModel · State Management |
| Maps | Google Maps SDK · Maps Compose |
| Backend | Supabase (PostgreSQL + Storage) |
| Networking | Supabase Kotlin SDK · Ktor Client |
| Image loading | Coil |

---

## 🏗 Architecture

The project follows **MVVM** architecture with a clear separation of concerns:

```
app/
├── MyApp.kt                  # Application class — initializes Supabase
├── data/
│   ├── Marker.kt             # Data class — mirrors Supabase table schema
│   └── MySupabaseClient.kt   # Data layer — all DB and Storage operations
├── ui/
│   ├── map/                  # Map screen + ViewModel
│   ├── list/                 # Markers list screen + ViewModel
│   └── detail/               # Create/Edit screen + ViewModel
```

**Database schema (Supabase/PostgreSQL):**

| Column | Type |
|--------|------|
| id | UUID (PK) |
| title | text |
| description | text |
| latitude | float8 |
| longitude | float8 |
| image_url | text |

---

## 🚀 Getting Started

### Prerequisites
- Android Studio Hedgehog or later
- Google Maps API Key
- Supabase project (URL + anon key)

### Setup

1. **Clone the repository**
   ```bash
   git clone https://github.com/bianca-mz/geographic-markers-app.git
   cd geographic-markers-app
   ```

2. **Add your API keys**

   Create a `local.properties` file in the root (already in `.gitignore`):
   ```properties
   MAPS_API_KEY=your_google_maps_api_key
   SUPABASE_URL=your_supabase_project_url
   SUPABASE_ANON_KEY=your_supabase_anon_key
   ```

3. **Set up Supabase**
   - Create a `markers` table with the schema above
   - Create a storage bucket named `marker-images` with public read policy

4. **Run the app**

   Open in Android Studio and click **Run ▶**

---

## 📋 Key Technical Highlights

- ✅ Full **Google Maps + Jetpack Compose** integration
- ✅ Complete **CRUD** connected to a remote database
- ✅ Cloud **image management** with Supabase Storage
- ✅ **Navigation** between screens with Navigation Compose
- ✅ Android **permissions** handling (location, gallery)
- ✅ **Reactive state** with Compose
- ✅ Real-time data persistence

---

## 👩‍💻 Author

**Bianca Sánchez**
Junior Software Developer · Android & Spring Boot

[![LinkedIn](https://img.shields.io/badge/LinkedIn-bianca--sánchez--zurita-0077B5?logo=linkedin&logoColor=white)](https://www.linkedin.com/in/bianca-sánchez-zurita)
[![GitHub](https://img.shields.io/badge/GitHub-bianca--mz-181717?logo=github&logoColor=white)](https://github.com/bianca-mz)
