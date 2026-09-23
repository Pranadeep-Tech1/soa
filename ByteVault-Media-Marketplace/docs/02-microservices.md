# 2. Microservice Identification and Service Discovery

| Service | Responsibility | Port | Data |
|---|---|---:|---|
| User Service | Registration, login, JWT issuing, user identity | 8081 | users |
| Product Service | Digital catalog and admin catalog management | 8082 | products |
| Order Service | Purchase transaction, order history, signed downloads | 8083 | purchase_orders |
| API Gateway | Single external entry point, JWT validation, routing | 8080 | none |
| Eureka Server | Service registry/discovery | 8761 | registry |

## Why these boundaries?
Product data changes independently from order data. Order execution needs product information but should not own the catalog. User identity is separated from commerce data. Gateway and discovery are infrastructure services rather than business domains.

## Service discovery
Order Service uses a load-balanced WebClient with `http://PRODUCT-SERVICE/...`. Eureka resolves the logical service name to a live instance. This avoids coupling Order Service to a fixed product host/port.

## API Gateway
The gateway is the only browser-facing backend endpoint. This supports a clean frontend API surface and gives the platform one place for inbound JWT validation and routing.
