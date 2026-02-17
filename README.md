## IT342_G1_Dabon_Lab1

Full-stack sample with a Spring Boot backend, a React web client, and an Android (Kotlin) mobile app.

## Tech Stack

**Backend**
- Java 21, Spring Boot 4 (WebMVC, Data JPA, Validation)
- MySQL

**Web**
- React, TypeScript, Vite
- Tailwind CSS

**Mobile**
- Android (Kotlin)

## Features
- Register and login
- JWT-based session handling
- View profile details
- Web and mobile dashboards

## API Endpoints
Base URL: `http://localhost:8080/api`

- `POST /auth/register`
- `POST /auth/login`
- `POST /auth/logout` (planned, not implemented yet)
- `GET /user/me` (requires `Authorization: Bearer <token>`)

## Run Instructions

### Backend (Spring Boot)
1. Start MySQL and update credentials in [backend/src/main/resources/application.properties](backend/src/main/resources/application.properties).
2. From [backend](backend) folder:
	```bash
	./mvnw spring-boot:run
	```
3. Backend runs on `http://localhost:8080`.

### Frontend (Web)
1. From [web](web) folder:
	```bash
	npm install
	npm run dev
	```
2. Web app runs on `http://localhost:5173`.

### Mobile (Android Studio)
1. Open the [mobile](mobile) folder in Android Studio.
2. Sync Gradle.
3. Run the app on an emulator or device.
4. API base URL is set to `http://10.0.2.2:8080/api/` for the Android emulator.
	- If you use a physical device, update the base URL in
	  [mobile/app/src/main/java/com/example/mobile/core/network/ApiClient.kt](mobile/app/src/main/java/com/example/mobile/core/network/ApiClient.kt).
