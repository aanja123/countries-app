# Countries App

A simple web application for browsing country information, built as a Spring Boot backend and an Angular frontend. Data comes from the [REST Countries API](https://restcountries.com/).

## Features

- List of all countries with name, capital, population, region, and flag
- Search countries by name
- Filter countries by region
- Sort countries by population (ascending/descending)
- Detailed view per country: official name, subregion, area, languages, currencies, timezones, and bordering countries (clickable)
- Centralized error handling on the backend (404 for missing countries, 502/503 for external API issues) with clear error messages shown on the frontend

## Tech Stack

- **Backend:** Java 17, Spring Boot 4.1.1, Maven
- **Frontend:** Angular 22, standalone components
- **External API:** REST Countries API v5
<!--
## Project Structure
countries-app/
├── countries-backend/ Spring Boot REST API
├── countries-frontend/ Angular application
└── README.md
-->
## Prerequisites

- JDK 17+
- Node.js (LTS) and npm
- A free API key from [restcountries.com](https://restcountries.com/)

## Running the app

There are two ways to run this project: manually (each app in its own terminal), or with Docker Compose (one command, both apps).

### Option 1: Manual

**Prerequisites:** JDK 17+, Node.js 22+ and npm, a free API key from [restcountries.com](https://restcountries.com/).

**Backend** — the REST Countries API key must be provided as an environment variable:
```powershell
cd countries-backend
$env:REST_COUNTRIES_API_KEY="your-api-key-here"
.\mvnw.cmd spring-boot:run
```
Runs on **http://localhost:8080**.

**Frontend** — in a separate terminal:
```powershell
cd countries-frontend
npm install
ng serve
```
Runs on **http://localhost:4200**.

### Option 2: Docker Compose

**Prerequisites:** [Docker Desktop](https://www.docker.com/products/docker-desktop/) installed and running.

Set your API key as an environment variable in the terminal you'll run Docker Compose from:
```powershell
$env:REST_COUNTRIES_API_KEY="your-api-key-here"
```

Then, from the project root:
```powershell
docker compose up --build
```

This builds and starts both the backend and frontend in containers:
- Backend: **http://localhost:8080**
- Frontend: **http://localhost:4200**

## Bonus Features

- **Spring Cache (Caffeine):** Country data is cached server-side for 1 hour using Spring's `@Cacheable` annotation, backed by Caffeine. Caching also makes repeated requests significantly faster, since cached responses are served from memory instead of over the network.
- **Docker Compose:** One `docker compose up --build` command builds and runs both apps together — backend via a multi-stage JDK/JRE build, frontend built and served via nginx. Runs on any machine with Docker, no local Java/Node setup needed.

<!--
## API Endpoints (backend)

| Method | Endpoint | Description |
|---|---|---|
| GET | `/api/countries` | List countries. Supports `?name=`, `?region=`, `?sort=population,asc\|desc` |
| GET | `/api/countries/{code}` | Get details for one country by its 3-letter code (e.g. `SVN`) |

## Notes

- The backend caches the full country list in memory for 1 hour to reduce calls to the external API (the free plan has a monthly request limit).
- CORS is configured on the backend to allow requests from `http://localhost:4200`.
-->