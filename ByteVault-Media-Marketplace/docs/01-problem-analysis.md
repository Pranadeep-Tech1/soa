# 1. Problem Analysis and Requirement Specification

## 1.1 Problem statement
ByteVault Media needs an automated marketplace for digital software and e-books. Physical inventory is unnecessary; after a successful transaction the customer must receive immediate access to the purchased digital asset. The platform must protect digital intellectual property, route requests through a gateway, and discover services dynamically.

## 1.2 Actors
- Customer: registers, logs in, browses products, purchases, views order history and downloads assets.
- Administrator: manages the product catalog.
- Platform: authenticates requests, executes orders, creates signed download URLs and discovers services.

## 1.3 Functional requirements
FR-01 Register a customer.
FR-02 Authenticate with username/password and issue JWT.
FR-03 List active digital products.
FR-04 View product details.
FR-05 Allow ADMIN to create/update/disable products.
FR-06 Submit a purchase request.
FR-07 Validate product availability through Product Service.
FR-08 Create a PAID order for a successful simulated transaction.
FR-09 Generate an expiring signed download URL.
FR-10 Download the purchased digital asset immediately.
FR-11 Display customer purchase history.
FR-12 Route all client API traffic through the API Gateway.
FR-13 Register/discover services using Eureka.
FR-14 Perform service-to-service communication without hard-coded Product Service host/port.

## 1.4 Non-functional requirements
- Security: JWT, BCrypt, role-based access control, signed URLs.
- Availability: independently deployable services and Eureka discovery.
- Modularity: business responsibilities separated by bounded service responsibilities.
- Performance: gateway routing and local H2 persistence for academic demo.
- Maintainability: Maven modules, conventional Spring packages, configuration in YAML.
- Testability: service-level tests and API-flow test plan.

## 1.5 Acceptance criteria
A submission is considered complete when all five services start, all services appear in Eureka, login returns a JWT, catalog is visible, a logged-in user can purchase, Order Service can discover Product Service, a PAID order is saved, and a signed URL downloads the generated asset.

## 1.6 Constraints and assumptions
Payment is simulated as an educational transaction. Digital assets are represented by generated text content to avoid redistributing copyrighted files. H2 is used for local development. A shared secret is used only to keep the academic project compact.

## 1.7 DTI concepts
Digital transformation is represented through self-service digital commerce, zero physical inventory, instant fulfillment, API-first architecture, automation, security-by-design and independently deployable services.
