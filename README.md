# Order Management System

### **A comprehensive microservices-based order processing platform with real-time inventory management, payment processing and notification services using event sourcing patterns**

### 📋 Table of Contents

- [Overview](#overview)
- [Architecture](#architecture)
- [Features](#features)
- [Tech Stack](#tech-stack)
- [Microservices](#microservices)
- [Getting Started](#getting-started)
- [API Documentation](#api-documentation)
- [Monitoring](#monitoring)
- [Development](#development)
- [Deployment](#deployment)
- [Contributing](#contributing)
- [License](#license)


## 🎯 Overview

This enterprise-grade order management system addresses critical challenges faced by e-commerce companies:

- **❌ Problem**: Companies lose millions due to overselling and poor order coordination
- **✅ Solution**: Real-time inventory management with event sourcing prevents conflicts and ensures reliable order processing at scale


### Key Business Benefits

- **Zero Overselling**: Real-time inventory reservations prevent stock conflicts
- **High Availability**: Microservices architecture ensures system resilience
- **Audit Trail**: Complete event sourcing for compliance and debugging
- **Multi-Tenant**: Single platform serving multiple businesses
- **Scalability**: Handles thousands of concurrent orders


## 🏗️ Architecture

### System Overview
```
┌─────────────────┐    ┌─────────────────┐    ┌─────────────────┐
│   API Gateway   │    │ Service Discovery│    │  Config Server  │
│     (8080)      │    │     (8761)      │    │     (8888)      │
└─────────────────┘    └─────────────────┘    └─────────────────┘
         │                       │                       │
         └───────────────────────┼───────────────────────┘
                                 │
         ┌─────────────────────────────────────────────────┐
         │           Apache Kafka Event Bus                │
         └─────────────────────────────────────────────────┘
                                 │
    ┌────────────┬────────────┬───┴────────┬────────────┬────────────┐
    │            │            │           │            │            │
┌───▼───┐ ┌──────▼─┐ ┌────────▼┐ ┌────────▼┐ ┌────────▼─┐ ┌──────▼───┐
│ User  │ │Product │ │Inventory│ │ Order  │ │ Payment │ │Notification│
│Service│ │Service │ │Service  │ │Service │ │ Service │ │  Service   │
│(8081) │ │(8082)  │ │(8083)   │ │(8084)  │ │(8085)   │ │  (8086)    │
└───────┘ └────────┘ └─────────┘ └────────┘ └─────────┘ └────────────┘
```

### Multi-Tenant Architecture
```
🏪 Tenant A (Electronics) ─┐
🏪 Tenant B (Fashion)     ─┼─→ Single System Instance
🏪 Tenant C (Books)       ─┘   (Data Isolation per Tenant)
```

## ✨ Features

### Core Features
- **🛒 Order Management**: Complete order lifecycle from creation to delivery
- **📦 Inventory Control**: Real-time stock management across multiple warehouses
- **💳 Payment Processing**: Multi-gateway payment processing with retry logic
- **👥 User Management**: Authentication, authorization, and profile management
- **📧 Notifications**: Multi-channel notifications (Email, SMS, Push)

### Advanced Features
- **📊 Event Sourcing**: Complete audit trail of all system events
- **🏢 Multi-Tenancy**: Single platform serving multiple businesses
- **🔄 CQRS Pattern**: Optimized read/write operations
- **⚡ Circuit Breaker**: Fault tolerance and resilience
- **📈 Real-time Analytics**: Business metrics and monitoring
- **🔒 Security**: JWT authentication, role-based access control

### Enterprise Features
- **🐳 Containerization**: Docker and Kubernetes ready
- **📊 Monitoring**: Prometheus metrics and health checks
- **🔧 Configuration**: Centralized configuration management
- **🚀 Auto-scaling**: Kubernetes horizontal pod autoscaling
- **📝 Audit Logging**: Comprehensive audit trails for compliance

## 🛠️ Tech Stack

### Backend
- **Java 17**: Modern Java features and performance improvements
- **Spring Boot 3.x**: Enterprise application framework
- **Spring Cloud**: Microservices infrastructure
- **Spring Security**: Authentication and authorization
- **Spring Data JPA**: Database abstraction layer

### Microservices
- **Spring Cloud Gateway**: API Gateway and routing
- **Eureka**: Service discovery and registration
- **Spring Cloud Config**: Centralized configuration
- **Resilience4j**: Circuit breaker and fault tolerance

### Messaging & Events
- **Apache Kafka**: Event streaming platform
- **Spring Kafka**: Kafka integration
- **Event Sourcing**: Custom implementation with MongoDB

### Databases
- **PostgreSQL**: Primary transactional database
- **MongoDB**: Event store and document storage
- **Redis**: Caching and session management

### Infrastructure
- **Docker**: Containerization
- **Kubernetes**: Container orchestration
- **Helm**: Kubernetes package manager

### Monitoring & Observability
- **Spring Boot Actuator**: Application metrics
- **Micrometer**: Metrics collection
- **Prometheus**: Metrics storage and alerting
- **Grafana**: Metrics visualization (optional)

## 🏗️ Microservices

| Service | Port | Description | Database |
|---------|------|-------------|----------|
| **Infrastructure** |
| Config Server | 8888 | Centralized configuration | - |
| Eureka Server | 8761 | Service discovery | - |
| API Gateway | 8080 | Single entry point | Redis |
| **Business Services** |
| User Service | 8081 | User management & auth | PostgreSQL |
| Product Service | 8082 | Product catalog | PostgreSQL |
| Inventory Service | 8083 | Stock management | PostgreSQL |
| Order Service | 8084 | Order processing | PostgreSQL |
| Payment Service | 8085 | Payment processing | PostgreSQL |
| Notification Service | 8086 | Multi-channel notifications | PostgreSQL |
| **Platform Services** |
| Event Store Service | 8087 | Event sourcing | MongoDB |
| Tenant Service | 8088 | Multi-tenancy | PostgreSQL |

[//]: # (| Analytics Service | 8089 | Business intelligence | MongoDB |)

[//]: # (| File Service | 8090 | File & media management | PostgreSQL |)

[//]: # (| Audit Service | 8091 | Compliance & auditing | PostgreSQL |)


## 🚀 Getting Started

### Prerequisites

- **Java 21+** (OpenJDK or Oracle JDK)
- **Maven 3.8+** or **Gradle 7+**
- **Docker 20.10+** and **Docker Compose**
- **Git 2.30+**

### Quick Start

1. **Clone the repository**
   ```bash
   git clone https://github.com/eltonmessias/enterprise-order-management-system.git
   ```
2. **Start infrastructure services**
   ```bash
   cd docker
   docker-compose up -d
   ```

3. **Verify infrastructure**
   ```bash
   # Check all services are running
   docker-compose ps
   
   # Check service health
   curl http://localhost:8761  # Eureka Dashboard
   curl http://localhost:8080  # Kafka UI
   ```

4. **Build the project**
   ```bash
   mvn clean install
   ```

5. **Start microservices**
   ```bash
   # Start infrastructure services first
   java -jar config-server/target/config-server-1.0.0.jar
   java -jar eureka-server/target/eureka-server-1.0.0.jar
   java -jar api-gateway/target/api-gateway-1.0.0.jar
   
   # Start business services
   java -jar user-service/target/user-service-1.0.0.jar
   java -jar product-service/target/product-service-1.0.0.jar
   java -jar inventory-service/target/inventory-service-1.0.0.jar
   java -jar order-service/target/order-service-1.0.0.jar
   ```
