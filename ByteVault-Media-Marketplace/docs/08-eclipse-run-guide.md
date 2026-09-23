# Eclipse Run Guide

## Import
File → Import → Maven → Existing Maven Projects → choose `ByteVault-Media-Marketplace`.

## Run configurations
Create one Java Application/Spring Boot run configuration for each main class:
- `com.bytevault.discovery.DiscoveryServerApplication`
- `com.bytevault.user.UserServiceApplication`
- `com.bytevault.product.ProductServiceApplication`
- `com.bytevault.order.OrderServiceApplication`
- `com.bytevault.gateway.ApiGatewayApplication`

Run in that order. Wait for Eureka before starting the clients. Open `http://localhost:8761` to confirm registrations.

## Maven commands
From the root:

```bash
mvn clean test
mvn clean package -DskipTests
```

Then run each generated jar from its module `target` directory, or use Eclipse Run As → Spring Boot App.
