# Viva / Demonstration Questions and Answers

**Q1. Why microservices?** Separate product, identity and order responsibilities so they can be developed/deployed independently.

**Q2. Why Eureka?** The order service can discover Product Service by logical name rather than a fixed address.

**Q3. Why an API Gateway?** It gives clients one endpoint and centralizes inbound routing and security concerns.

**Q4. Why JWT?** It provides stateless bearer authentication suitable for API clients; services can validate the token without a server-side session.

**Q5. How is the download secured?** A purchase creates an HMAC-signed token containing order/product identifiers and an expiry. Tampering or expiry causes rejection.

**Q6. Why no inventory count?** The products are digital assets, so physical stock is not decremented.

**Q7. What is the inter-service call?** Order Service uses a load-balanced WebClient to call Product Service through Eureka.

**Q8. What is the DTI idea?** Automated digital self-service, instant fulfillment and elimination of physical inventory handling.

**Q9. How would you make it production-ready?** External identity provider, asymmetric JWT keys, object storage signed URLs, payment gateway, Kafka/outbox for reliable events, observability, secrets manager, HTTPS and rate limiting.
