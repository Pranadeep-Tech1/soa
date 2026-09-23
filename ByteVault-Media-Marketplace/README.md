# ByteVault Media — SOA Programming & Microservices Project

A complete educational digital marketplace for downloadable software and e-books. The system demonstrates independent microservices, Eureka service discovery, API Gateway routing, JWT authentication, inter-service communication, digital order processing, signed/expiring download URLs, a responsive frontend, H2 persistence, and automated tests.

## Technology stack
- Java 21
- Spring Boot 4.1.1
- Spring Cloud 2025.1.3 (Oakwood)
- Spring Cloud Gateway WebFlux
- Spring Cloud Netflix Eureka
- Spring Security + JWT resource-server validation
- Spring Data JPA + H2
- Maven
- HTML/CSS/JavaScript frontend

Spring's compatibility table maps Spring Cloud 2025.1.x to Spring Boot 4.0.x/4.1.x; the project uses the stable Spring Cloud 2025.1.3 release. See the official Spring Cloud project page for the current compatibility table. The Spring Boot documentation currently lists 4.1.1 as a stable release.

## Architecture

```text
Browser / Frontend
       |
       v
+-------------------+
| API Gateway :8080  |  JWT validation + routing
+---------+---------+
          |
    Eureka discovery
          |
 +--------+---------+------------------+
 |                  |                  |
v                  v                  v
User :8081      Product :8082      Order :8083
 JWT issuer      Catalog            Purchase workflow
 H2 users        H2 products        H2 orders
                                      |
                                      | WebClient + Eureka
                                      v
                                Product Service
```

### Main flow
1. Browser opens `http://localhost:8080`.
2. User logs in through `/api/auth/login`.
3. User Service validates the BCrypt password and issues a signed JWT.
4. Gateway validates the JWT for protected operations.
5. Product Service supplies catalog data.
6. Purchase goes to Order Service.
7. Order Service discovers Product Service through Eureka and retrieves product details using WebClient.
8. A PAID order is persisted and a 15-minute HMAC-signed download URL is generated.
9. The browser receives the secure URL and immediately downloads the digital demo asset.

## Eclipse setup
1. Install JDK 21 and Eclipse IDE for Enterprise Java and Web Developers.
2. Install Maven support if your Eclipse package does not include it.
3. File → Import → Maven → Existing Maven Projects.
4. Select the root `ByteVault-Media-Marketplace` directory.
5. Wait for Maven dependency resolution.
6. Run these Spring Boot applications in order:
   - `DiscoveryServerApplication` — 8761
   - `UserServiceApplication` — 8081
   - `ProductServiceApplication` — 8082
   - `OrderServiceApplication` — 8083
   - `ApiGatewayApplication` — 8080
7. Open `http://localhost:8080`.

### Demo credentials
- Student: `student` / `student123`
- Admin: `admin` / `admin123`

Change these credentials and secrets before any real deployment.

## API summary

| Method | Gateway endpoint | Purpose | Auth |
|---|---|---|---|
| POST | `/api/auth/register` | Register | Public |
| POST | `/api/auth/login` | Get JWT | Public |
| GET | `/api/products` | Browse catalog | Public |
| GET | `/api/products/{id}` | Product details | Public |
| POST | `/api/products` | Add product | ADMIN JWT |
| PUT | `/api/products/{id}` | Update product | ADMIN JWT |
| DELETE | `/api/products/{id}` | Soft-delete product | ADMIN JWT |
| POST | `/api/orders/purchase` | Purchase product | USER/ADMIN JWT |
| GET | `/api/orders/my` | Purchase history | USER/ADMIN JWT |
| GET | `/api/orders/download/{id}?token=...` | Secure download | Signed expiring URL |

## Security design
- Passwords are BCrypt hashed.
- JWTs contain subject, userId, roles, issuer, issued-at and expiry claims.
- Gateway and backend services validate the same HMAC-SHA256 JWT.
- Admin product mutation endpoints use method security and `ROLE_ADMIN`.
- Download links use an independent HMAC signature and a 15-minute expiry.
- The demo download endpoint returns generated content rather than storing copyrighted material.

## Test plan
The project includes focused tests for authentication, product catalog, and the purchase flow. For the assessment, also demonstrate:
- invalid JWT rejected with 401
- non-admin rejected from product mutation with 403
- unknown product returns an error
- successful purchase creates `PAID` order
- download token works before expiry
- tampered download token returns 403
- expired download token returns 403
- Eureka shows USER-SERVICE, PRODUCT-SERVICE, ORDER-SERVICE and API-GATEWAY
- gateway routes requests without exposing service ports to the frontend

## Rubric mapping
### 1. Problem Analysis and Requirement Specification — Weight 10
See `docs/01-problem-analysis.md` for actors, functional requirements, non-functional requirements, use cases, constraints, risks and acceptance criteria.

### 2. Microservice Identification and Service Discovery — Weight 10
Five independently runnable services are included: User, Product, Order, API Gateway and Eureka Discovery. The core business services are deliberately separated and Order→Product communication uses service discovery.

### 3. JWT Authentication — Weight 10
JWT login, expiry, roles, BCrypt passwords, gateway validation and backend resource-server validation are implemented.

### 4. API Gateway Configuration — Weight 10
Gateway routes `/api/auth/**`, `/api/users/**`, `/api/products/**` and `/api/orders/**` to Eureka-discovered services.

### 5. LinkedIn Article with DTI Concepts and Review — Weight 10
See `docs/05-linkedin-article.md`. It is a publication-ready article draft covering DTI/digital transformation concepts, architecture, innovation, results, review and screenshots to capture.

### 6. MOOCs — Weight 10
See `docs/06-mooc-completion.md`. It contains the two specified courses and a completion evidence checklist. Enter your actual completion percentage; do not fabricate certificates.

## Recommended screenshots for the submission
1. Eclipse Project Explorer showing all modules.
2. Eureka dashboard showing all registered services.
3. Login page.
4. Product catalog page.
5. Successful purchase and browser download.
6. H2 order database / order response.
7. Postman request with Bearer JWT.
8. 401 response for invalid JWT.
9. 403 response for USER attempting admin product mutation.
10. Eclipse console showing each service started.

## Important note
This is an academic/demo implementation. For production, replace the shared HMAC JWT setup with a dedicated identity provider/authorization server, use asymmetric signing keys, external object storage with signed URLs, a real payment provider, centralized secrets, HTTPS, observability, rate limiting and transactional/event-driven order processing.
