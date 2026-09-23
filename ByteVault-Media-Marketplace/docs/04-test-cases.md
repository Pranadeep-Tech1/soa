# 4. Test Cases

| ID | Test | Expected |
|---|---|---|
| TC01 | Login with student/student123 | 200 + JWT |
| TC02 | Login with wrong password | 4xx |
| TC03 | GET catalog without JWT | 200 |
| TC04 | POST product as USER | 403 |
| TC05 | POST product as ADMIN | 200/201 |
| TC06 | Purchase valid product with JWT | 200 + PAID order + download URL |
| TC07 | Purchase unknown product | error response |
| TC08 | GET my orders with JWT | 200 + current user's orders |
| TC09 | Download with valid signed URL | 200 + attachment |
| TC10 | Tamper token signature | 403 |
| TC11 | Expire download token | 403 |
| TC12 | Remove Product Service instance | request cannot be fulfilled until service is available; demonstrates discovery dependency |
| TC13 | Verify Eureka dashboard | all service instances registered |
| TC14 | Call Order Service through gateway | gateway routes using Eureka |

## Manual Postman sequence
1. `POST http://localhost:8080/api/auth/login` body `{ "username":"student", "password":"student123" }`.
2. Copy `token`.
3. `GET http://localhost:8080/api/products`.
4. `POST http://localhost:8080/api/orders/purchase` with Bearer token and `{ "productId":1 }`.
5. Open returned `downloadUrl`.
6. Repeat with a modified token and capture the 403 result.
