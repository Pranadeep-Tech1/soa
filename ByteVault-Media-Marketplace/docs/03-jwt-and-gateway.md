# 3. JWT Authentication and API Gateway

## JWT lifecycle
1. User submits credentials to User Service.
2. User Service verifies the BCrypt hash.
3. User Service signs a JWT containing subject, userId, roles, issuer and expiry.
4. Browser stores the token for the academic demo.
5. Protected requests carry `Authorization: Bearer <token>`.
6. Gateway validates the token.
7. Downstream services validate the same token again, providing defense in depth.

## Authorization matrix
| Endpoint | Anonymous | USER | ADMIN |
|---|---:|---:|---:|
| Catalog GET | Yes | Yes | Yes |
| Product POST/PUT/DELETE | No | No | Yes |
| Purchase | No | Yes | Yes |
| My orders | No | Yes | Yes |
| Login/Register | Yes | Yes | Yes |

## Gateway routes
- `/api/auth/**` → USER-SERVICE
- `/api/users/**` → USER-SERVICE
- `/api/products/**` → PRODUCT-SERVICE
- `/api/orders/**` → ORDER-SERVICE

## Secure URL design
A purchase response contains a URL with a token signed by Order Service. The token encodes order ID, product ID and expiry. The server recomputes the HMAC and rejects modified or expired values.
