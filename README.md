# Real-Time Attendance & Presence Service

A low-latency classroom presence API built with Spring Boot, utilizing WebSockets for real-time event tracking, Caffeine for in-memory caching, and PostgreSQL for durable session logs. It features an AI-integrated hook to generate concise lesson notes from session events.

## 🔗 Project Links
* **Live API Base URL (Hosted on Render/Railway):** `[INSERT_YOUR_HOSTED_LINK_HERE]`
* **Presentation Slides (PDF):** `[INSERT_LINK_TO_PDF_HOSTED_EXTERNALLY_NOT_GDRIVE]`

## 🗂️ Submission Assets
All required visual assets are located in the `/Screenshots` folder in the root directory:
* `api_responses.png` - Postman tests of the REST endpoints.
* `database_ui.png` - View of the populated PostgreSQL tables.
* `live_state.png` - Visual proof of the application running.
* `er_diagram.png` - The relational database design.

## 🗃️ Entity Relationships
*Refer to `/Screenshots/er_diagram.png` for the visual layout.*

* **ClassSession (1) to (N) SessionEvent:** A single class session contains multiple real-time events (joins, leaves, hand raises). The `session_id` acts as a foreign key in the `session_events` table.
* **User (1) to (N) SessionEvent:** A single user can trigger multiple events across different sessions. The `user_id` acts as a foreign key in the `session_events` table.

## 🛠️ Technology Stack
* **Core:** Java 17+, Spring Boot 3
* **Real-Time:** Spring WebSocket (STOMP over SockJS)
* **Database & Migrations:** PostgreSQL, Spring Data JPA, Flyway
* **Caching:** Spring Cache with Caffeine
* **AI Integration:** Spring RestTemplate integrating with [Insert AI API used, e.g., Google Gemini API]

## 🚀 How to Run Locally

1. Clone the repository.
2. Ensure PostgreSQL is running locally on port `5432` with a database named `attendance_db`.
3. Update `src/main/resources/application.yml` with your local database credentials.
4. Run `mvn spring-boot:run`. Flyway will automatically create the database tables.