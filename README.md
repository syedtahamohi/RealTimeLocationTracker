# RealTimeLocationTracker

A real-time location tracking system built with **Java Spring Boot** and **Apache Kafka**. Location data is published by a sender service and consumed in real time by a receiver service, with Kafka acting as the message broker in between.

---

## Architecture

```
LocationSender  ──publish──►  Kafka (KRaft)  ──consume──►  LocationReceiver
 (Producer)                   Topic: Location               (Consumer)
 Spring Boot                  Port: 9092                    Spring Boot
```

- **LocationSender** — Spring Boot app that acts as a Kafka producer. Publishes GPS location data to the `Location` topic.
- **LocationReceiver** — Spring Boot app that acts as a Kafka consumer. Listens on the `Location` topic (consumer group: `user-group`) and processes incoming location updates.
- **Apache Kafka (KRaft mode)** — Message broker. No Zookeeper required — uses the modern KRaft consensus protocol for self-managed metadata.

---

## Tech Stack

| Technology | Version | Purpose |
|---|---|---|
| Java | 17+ | Application language |
| Spring Boot | Latest | Application framework |
| Spring Kafka | Latest | Kafka producer/consumer integration |
| Apache Kafka | 3.9.0 | Message broker |
| Docker | Latest | Container runtime |
| Docker Compose | Latest | Multi-container orchestration |

---

## Prerequisites

- [Docker Desktop](https://www.docker.com/products/docker-desktop/) installed and running
- Java 17 or higher
- Maven

---

## Getting Started

### 1. Start Kafka

From the project root, start Kafka using Docker Compose:

```bash
docker compose up -d
```

This starts a single Kafka node in KRaft mode on port `9092`. No Zookeeper needed.

Verify Kafka is running in Docker Desktop → Containers → `kafka` → Logs. You should see:

```
Running in KRaft mode...
[KafkaRaftServer nodeId=1] Kafka Server started
```

### 2. Start LocationReceiver (Consumer)

Start the receiver first so it's ready to consume messages:

```bash
cd LocationReceiver
./mvnw spring-boot:run
```

### 3. Start LocationSender (Producer)

Then start the sender:

```bash
cd LocationSender
./mvnw spring-boot:run

```
The sender will begin publishing location data to the `Location` Kafka topic, and the receiver will print the incoming updates.

---
IDE can be used to run the Spring Boot applications instead of the command line if preferred.

---

## Project Structure

```
RealTimeLocationTracker/
├── LocationSender/          # Kafka producer (Spring Boot)
│   └── src/
│       └── main/
│           ├── java/        # Producer logic
│           └── resources/   # application.properties
├── LocationReceiver/        # Kafka consumer (Spring Boot)
│   └── src/
│       └── main/
│           ├── java/        # Consumer logic
│           └── resources/   # application.properties
└── docker-compose.yml       # Kafka infrastructure (KRaft mode)
```

---

## Kafka Configuration

| Property | Value |
|---|---|
| Bootstrap server | `localhost:9092` |
| Topic | `Location` |
| Consumer group | `user-group` |
| Kafka mode | KRaft (no Zookeeper) |
| Cluster ID | Auto-generated |

---

## Why KRaft over Zookeeper?

This project uses **KRaft mode** (Kafka Raft), the modern way to run Kafka without an external Zookeeper dependency.

| Feature | Zookeeper (old) | KRaft (new) |
|---|---|---|
| External dependency | Yes (Zookeeper process) | No |
| Startup time | Slower | Faster |
| Metadata management | Separate system | Built into Kafka |
| Scalability | Limited (~200K partitions) | Millions of partitions |
| Future support | Deprecated in Kafka 3.x | Default from Kafka 4.0+ |

---