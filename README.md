# 💰 Bank Account Kata

This kata was developed as part of a recruitment process at Société Générale.

---

## 🎯 Functional Objectives

Allow a bank client to:
- make a **deposit** to their account
- make a **withdrawal**
- view the **transaction history** (statement: date, amount, current balance)

---

## ⚙️ Technical Objectives

- Demonstrate a **Craft / Clean Code / TDD** approach
- Apply **SOLID principles** and **lightweight DDD**
- Structure the project with a clear and scalable architecture
- Use **Conventional Commits**
- Minimal **CI/CD** via GitHub Actions
- Prepare for future integration with an **Angular front-end**

---

## 🧱 Tech Stack

### Back-end
- Java 21
- Spring Boot 3.x
- Maven
- JUnit 5, AssertJ, Mockito
- Git

### Front-end
- Angular 17
- Angular Material

---

## 📁 Project Structure 
---
```
bank-account-sg/
├── README.md
├── backend/
│   ├── pom.xml
│   ├── mvnw / mvnw.cmd
│   └── src/
│       ├── main/
│       │   ├── java/com/bankkata/bankaccount/
│       │   │   ├── BankAccountApplication.java
│       │   │   ├── application/
│       │   │   │   ├── config/AppConfig.java
│       │   │   │   └── service/
│       │   │   │       ├── AccountService.java
│       │   │   │       ├── ClockService.java
│       │   │   │       └── SystemClockService.java
│       │   │   ├── domain/model/
│       │   │   │   ├── Account.java
│       │   │   │   ├── Transaction.java
│       │   │   │   └── TransactionType.java
│       │   │   └── infrastructure/api/
│       │   │       ├── AccountController.java
│       │   │       └── dto/
│       │   │           ├── DepositRequest.java
│       │   │           ├── WithdrawalRequest.java
│       │   │           └── TransactionResponse.java
│       │   └── resources/application.properties
│       └── test/
│           └── java/com/bankkata/bankaccount/
│               ├── BankAccountApplicationTests.java
│               ├── application/service/AccountServiceTest.java
│               ├── domain/model/AccountTest.java
│               └── infrastructure/api/AccountControllerTest.java
├── frontend/
│   ├── angular.json
│   ├── package.json
│   └── src/
│       └── app/
│           ├── components/
│           ├── models/
│           ├── services/
│           └── app.module.ts
```


## 🛠️ setup

```bash
npm install -g @angular/cli
```

---

### 📦 backend setup

```bash
cd backend
./mvnw clean install
```

---

### 📦 frontend setup

```bash
cd ../frontend
npm install
```

---

## 🚀 start

### ▶️ start the backend

move to the folder `backend` :

```bash
./mvnw spring-boot:run
```

the rest API start in: [http://localhost:8080](http://localhost:8080)

---

### ▶️ start frontend

move to the folder `frontend` :

```bash
ng serve
```

the Angular application will be available in : [http://localhost:4200](http://localhost:4200)
