# Hotel Booking Demo

LuxeStay is a full-stack hotel booking demo built with:

- `frontend/`: React + Vite
- `backend/`: Spring Boot + Spring Security + JPA
- `database`: seeded H2 in-memory database by default

The project now starts with mock hotel inventory, sample rooms, amenities, bookings, customer users, and a seeded admin account so the app is ready for demos immediately.

## Features

- Browse a polished hotel catalog
- Open hotel detail pages and inspect room inventory
- Book rooms through a live backend flow
- View and cancel bookings from the profile page
- Sign in as admin and review seeded inventory, users, revenue, and bookings
- Access H2 console for inspection during development



## Project Structure

```text
New folder/
|- backend/
|  |- src/main/java/com/example/backend/
|  |  |- config/
|  |  |- controller/
|  |  |- dto/
|  |  |- model/
|  |  |- repository/
|  |  |- service/
|  |- src/main/resources/application.properties
|  |- pom.xml
|- frontend/
|  |- src/
|  |  |- context/
|  |  |- pages/
|  |  |- services/
|  |  |- App.jsx
|  |  |- index.css
|  |- package.json
|- README.md
```

## Backend Setup

### Requirements

- Java 17+
- Maven 3.9+

### Run the backend

```bash
cd backend
mvn spring-boot:run
```

By default the backend starts on:

- `http://localhost:8080`

### Default database configuration

The backend uses H2 in-memory mode by default:

```properties
spring.datasource.url=jdbc:h2:mem:hotel_booking;MODE=MySQL;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE
spring.datasource.username=sa
spring.datasource.password=
spring.datasource.driver-class-name=org.h2.Driver
```

You can override this with environment variables:

- `SPRING_DATASOURCE_URL`
- `SPRING_DATASOURCE_USERNAME`
- `SPRING_DATASOURCE_PASSWORD`
- `SPRING_DATASOURCE_DRIVER_CLASS_NAME`

### H2 Console

Open:

- `http://localhost:8080/h2-console`

Use:

- JDBC URL: `jdbc:h2:mem:hotel_booking`
- Username: `sa`
- Password: leave blank

## Frontend Setup

### Requirements

- Node.js 18+
- npm

### Install dependencies

```bash
cd frontend
npm install
```

### Run the frontend

```bash
npm run dev
```

The frontend runs by default on:

- `http://localhost:5173`

### API base URL

The frontend uses:

- `VITE_API_URL`

If not provided, it falls back to:

- `http://localhost:8080/api`

## Build and Test

### Frontend production build

```bash
cd frontend
npm run build
```

### Backend tests

```bash
cd backend
mvn test
```

## Main Routes

### Frontend

- `/` home
- `/hotels` hotel catalog
- `/hotels/:id` hotel detail
- `/checkout?roomId=...` booking flow
- `/profile` customer booking history
- `/login` sign in, register, password reset
- `/admin` admin dashboard

### Backend API

- `/api/auth/login`
- `/api/auth/register`
- `/api/auth/forgot-password`
- `/api/hotels`
- `/api/rooms`
- `/api/bookings`
- `/api/admin/dashboard`
- `/api/admin/bookings`
- `/api/admin/users`

## Notes

- Hotel and room browsing is public.
- Booking and profile actions require authentication.
- The admin dashboard expects the seeded admin account.
- The backend is currently optimized for local development and demo use.


## Current Development Defaults

- Backend port: `8080`
- Frontend port: `5173`
- H2 console enabled: `true`
- JWT auth enabled
- Seed data enabled automatically when the database is empty
<img width="1600" height="900" alt="1000095987" src="https://github.com/user-attachments/assets/86e9a055-d360-4d86-bbaa-d74bd2c5d75b" />
<img width="1600" height="900" alt="1000095982" src="https://github.com/user-attachments/assets/d3f9f4ef-fc4b-491b-be4f-7acb11c7c2fb" />
<img width="1600" height="900" alt="1000095983" src="https://github.com/user-attachments/assets/04600dd8-4be9-404c-b54d-f31a3d14b5da" />
<img width="1600" height="900" alt="1000095984" src="https://github.com/user-attachments/assets/d38d95b3-00ff-48ad-a341-2e922f38b11d" />
<img width="1600" height="900" alt="1000095985" src="https://github.com/user-attachments/assets/702783c2-6e13-4b52-ba51-8f803f01e1fd" />
<img width="1600" height="900" alt="1000095986" src="https://github.com/user-attachments/assets/a5b86229-ae93-477c-81ae-17ded4a4b6ef" />
<img width="1600" height="900" alt="1000095988" src="https://github.com/user-attachments/assets/a7ad0cfc-4e7e-4d5d-afea-62a426e47a7d" />
<img width="1600" height="900" alt="1000095989" src="https://github.com/user-attachments/assets/92a47ee2-0085-4a88-b089-a04e640f2d4b" />
<img width="1600" height="900" alt="1000095990" src="https://github.com/user-attachments/assets/85ba0262-aeaa-441d-ac73-4b3c46f1d6c6" />
