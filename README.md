🏦 POS Transaction Simulator (End-to-End)
This repository contains four main microservices that simulate the POS transaction lifecycle, starting from the POS device to the PSP (Payment Service Provider), then to Shaparak, and finally to the banking system.

This system is designed for development and testing of financial transaction systems, specifically the simulation of POS transaction flows and balance inquiries.

📦 Project Structure
.
├── pos               # Simulated POS device (request generator)
├── psp              # Payment Service Provider layer (validates & logs requests)
├── shaparak         # National switching system (validates & routes to bank)
├── bank             # Simulated banking systems (e.g., Mellat, Karafarin)

🧩 Use Cases
1. POS Transaction (Purchase)
Request: POS → PSP → Shaparak → Bank
Response: Bank → Shaparak → PSP → POS

Each service logs the request and updates the transaction status to MongoDB and ElasticSearch.

Transaction Payload:
{
  "posId": "UUID",
  "sourceCardNumber": "String",
  "destinationCardNumber": "String",
  "password": "Encrypted",
  "price": "Decimal"
}

2. POS Balance Inquiry
Request: POS → PSP → Shaparak → Bank

Response: Bank → Shaparak → PSP → POS

Balance Response:
{
  "transactionId": "UUID",
  "balance": "Decimal",
  "fee": "Decimal",
  "status": "SUCCESSFUL | FAILED"
}

🧾 Logs and Data Flow
✅ MongoDB: Stores transaction status, error messages, and timestamps.

📊 ElasticSearch: Stores event logs for monitoring.

📁 Log Files: Each microservice appends request/response events.

🧪 Technologies Used
Java / Spring Boot

MongoDB

ElasticSearch

RESTful APIs

MySQL (for bank services)

Docker (optional deployment)

🛠️ Setup Instructions
Each service can be run independently using Maven or Docker. For simulation, POST requests can be sent using Postman or curl to the POS service.

Sample Endpoints:
POST /transaction → Start purchase transaction

POST /balance → Get current card balance

🧙 Inspiration
Inspired by real-world Iranian payment network systems (like Shaparak), this simulator aims to provide a sandbox environment for developers to test POS scenarios.

