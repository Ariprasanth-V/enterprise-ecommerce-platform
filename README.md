# Enterprise E-Commerce Microservices Platform

A production-grade, distributed e-commerce backend and modern single-page frontend application designed from first principles. This platform implements an event-driven microservices architecture utilizing a comprehensive 15-service backend ecosystem orchestrated via Docker Compose, Spring Cloud, and API Gateway routing.

---

## 🏗️ Architecture & Tech Stack

### **Backend & Microservices (Spring Boot / Java / AI RAG)**
* **Language & Framework:** Java 21, Spring Boot 3.2.5, Spring Cloud
* **AI & Intelligence:** Google GenAI SDK (Gemini Integration) for intelligent features & RAG retrieval
* **Service Discovery & Config:** Netflix Eureka Discovery Server, Spring Cloud Config Server
* **Routing & Security:** Spring Cloud API Gateway, Spring Security with JWT Stateless Authentication
* **Resiliency & Observability:** Resilience4j Circuit Breakers, Distributed Tracing
* **Messaging & Event Streaming:** Apache Kafka (Asynchronous saga orchestration & event decoupling)
* **Data Persistence:** Spring Data JPA, Hibernate, MySQL, PostgreSQL, Redis (Distributed Caching)
* **Search Engine:** Elasticsearch clusters for ultra-fast catalog indexing

### **Frontend (React 19)**
* **Core:** React 19, Vite, JavaScript (JSX)
* **Styling & State:** Tailwind CSS, Zustand (reactive cart state & JWT session management)
* **Networking:** Axios with automated Bearer token interceptor routing

---

## 🏛️ The 15-Service Backend Ecosystem

1. **`config-server`**: Centralized externalized configuration management across all environment profiles.
2. **`eureka-server`**: Service registry handling dynamic microservice registration and health checks.
3. **`api-gateway`**: Unified entry point handling dynamic routing, load balancing, and security filter chains.
4. **`auth-service`**: Handles credential validation, user registration, and secure JWT token generation.
5. **`user-service`**: Manages detailed user profiles, shipping addresses, and account management.
6. **`product-service`**: Handles catalog items, inventory tracking, and admin product creation pipelines.
7. **`review-service`**: Manages product reviews, user ratings, and feedback aggregation.
8. **`search-service`**: High-performance catalog searching backed by Elasticsearch clusters.
9. **`ai-service`**: Powers generative AI features and RAG-driven contextual product assistance via the Gemini API.
10. **`cart-service`**: Manages shopping cart states and item quantities backed by Redis caching.
11. **`order-service`**: Orchestrates order lifecycles and emits order events via Kafka.
12. **`payment-service`**: Processes transactional payments and reconciles financial records.
13. **`inventory-service`**: Handles real-time stock levels, warehouse reservations, and stock deductions.
14. **`notification-service`**: Asynchronously processes Kafka events to dispatch user alerts and emails.
15. **`shipping-service`**: Manages logistics, fulfillment tracking, and delivery routing.

---

## 🚀 Getting Started & Local Deployment

### Prerequisites
* **Docker Desktop** installed and running.
* **Java 21 SDK** and **Maven** (for local development).
* **Node.js (v18+)** and npm.

### Running via Docker Compose
To spin up the full containerized architecture:

1. Clone the repository:
   ```bash
   git clone [https://github.com/Ariprasanth-V/enterprise-ecommerce-platform.git](https://github.com/Ariprasanth-V/enterprise-ecommerce-platform.git)
   cd enterprise-ecommerce-platform