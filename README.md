# 📚 LibraryTracker

LibraryTracker is a **Spring Boot + PostgreSQL** based **library management system** secured with JWT.  
It manages authors, books, users, and loan transactions; provides API documentation via Swagger, and handles database migrations with Flyway.

---

## 🚀 Features

- **Author Management**: Add, update, delete authors; view authors with their books
- **Book Management**: Add, update, delete books; search and filter (category, title, author name, availability)
- **User Management**: Roles (`ADMIN`, `USER`), lock/unlock accounts, enable/disable users
- **Loan Transactions**: Borrow books, return books, list active/overdue loans, enforce max active-loan limit
- **Authentication**: JWT-based login, secure endpoint access
- **API Documentation**: Swagger UI integration
- **Database Management**: Flyway migrations

---

## 🛠 Tech Stack

- **Backend**: Java 17, Spring Boot
- **Database**: PostgreSQL
- **ORM**: Hibernate / JPA
- **Migrations**: Flyway
- **Security**: Spring Security, JWT
- **Docs**: springdoc-openapi (Swagger UI)
- **Build**: Maven
- **Containerization**: Docker, Docker Compose

---

## 📂 Project Structure

```
src
├── main
│   ├── java/com/example/LibraryTracker
│   │   ├── config/          # Security, Swagger, JWT configs
│   │   ├── controller/      # REST Controllers
│   │   ├── dto/             # Data Transfer Objects
│   │   ├── entity/          # JPA entities
│   │   ├── exception/       # Custom exceptions & global handler
│   │   ├── mapper/          # MapStruct DTO<->Entity mappers
│   │   ├── repository/      # Spring Data JPA repositories
│   │   ├── service/         # Service layer
│   │   └── LibraryTrackerApplication.java
│   └── resources
│       ├── application.yml  # Configuration
│       └── db/migration/    # Flyway SQL migrations
└── test                     # Tests
```

---

## ⚙️ Setup & Run

### 1) Build
```bash
mvn clean install
```

### 2) Run with Docker
**Dockerfile** and **docker-compose.yml** are included. PostgreSQL and the app start with one command:

```bash
docker compose up --build
```

Default environment variables via `.env`:
```env
POSTGRES_DB=library
POSTGRES_USER=library_user
POSTGRES_PASSWORD=library_pass
SPRING_PROFILES_ACTIVE=prod
JWT_SECRET_KEY=library-secret
```

App URL:
```
http://localhost:8080/app
```

Swagger UI:
```
http://localhost:8080/app/swagger-ui.html
```
(Also try `http://localhost:8080/app/swagger-ui/index.html` depending on springdoc version.)

---

## 🔑 Authentication

- **Login**: `POST /api/auth/login`
- **Request**:
```json
{ "username": "username", "password": "password" }
```
- **Response**:
```json
{
  "token": "jwt_token",
  "userId": 1,
  "roles": ["USER"],
  "enabled": true,
  "accountNonLocked": true
}
```
Use the token with:
```
Authorization: Bearer <jwt_token>
```

---

## 📚 API Endpoints & Sample Requests

> All protected endpoints require `Authorization: Bearer <token>` header.  
> Example cURL header:
> ```bash
> -H "Authorization: Bearer $TOKEN" -H "Content-Type: application/json"
> ```

### 1) Auth

- **POST** `/api/auth/login` → login and get JWT

```bash
curl -X POST http://localhost:8080/app/api/auth/login   -H "Content-Type: application/json"   -d '{"username":"admin","password":"admin"}'
```

---

### 2) Authors

- **POST** `/api/authors` → create author (body: `CreateAuthorDto`)
- **GET** `/api/authors/{id}` → get author by id
- **GET** `/api/authors` → list all authors
- **GET** `/api/authors/{id}/with-books` → author with books
- **PUT** `/api/authors/{id}` → update author (body: `CreateAuthorDto`)
- **DELETE** `/api/authors/{id}` → delete author
- **GET** `/api/authors/by-name?name=...` → get by name

**Sample**:
```bash
curl -X POST http://localhost:8080/app/api/authors   -H "Authorization: Bearer $TOKEN" -H "Content-Type: application/json"   -d '{ "name":"Sabahattin Ali", "birthYear":1907 }'
```

---

### 3) Books

- **POST** `/api/books` → create (body: `CreateBookDto`)
- **GET** `/api/books/{id}` → get by id
- **GET** `/api/books` → list all
- **GET** `/api/books/search/category?category=Novel`
- **GET** `/api/books/search/title?keyword=madonna`
- **GET** `/api/books/search/author?authorName=Sabahattin%20Ali`
- **PUT** `/api/books/{id}` → update
- **DELETE** `/api/books/{id}` → delete
- **GET** `/api/books/available` → available books
- **GET** `/api/books/available?category=Novel` → available by category

**Sample**:
```bash
curl -X POST http://localhost:8080/app/api/books   -H "Authorization: Bearer $TOKEN" -H "Content-Type: application/json"   -d '{ "title":"Kürk Mantolu Madonna", "publishYear":1943, "category":"Novel", "authorId":1 }'
```

---

### 4) Users

- **POST** `/api/users` → create user (body: `CreateUserDto`)
- **GET** `/api/users/by-username/{username}`
- **GET** `/api/users/by-email/{email}`
- **GET** `/api/users` → list all
- **PUT** `/api/users/{id}` → update
- **DELETE** `/api/users/{id}` → delete
- **PUT** `/api/users/{userId}/roles/add?role=ADMIN`
- **PUT** `/api/users/{userId}/roles/remove?role=USER`
- **PUT** `/api/users/{userId}/enable`
- **PUT** `/api/users/{userId}/disable`
- **PUT** `/api/users/{userId}/lock`
- **PUT** `/api/users/{userId}/unlock`

**Sample**:
```bash
curl -X PUT "http://localhost:8080/app/api/users/5/roles/add?role=ADMIN"   -H "Authorization: Bearer $TOKEN"
```

---

### 5) Loans

- **POST** `/api/loans/borrow` → borrow (body: `CreateLoanDto`)
- **PUT** `/api/loans/{loanId}/return` → return
- **GET** `/api/loans/user/{userId}` → loans by user
- **GET** `/api/loans/user/{userId}/active` → active loans by user
- **GET** `/api/loans/book/{bookId}` → loans by book
- **GET** `/api/loans/active` → all active (not returned)
- **GET** `/api/loans/overdue` → overdue loans

**Sample**:
```bash
curl -X POST http://localhost:8080/app/api/loans/borrow   -H "Authorization: Bearer $TOKEN" -H "Content-Type: application/json"   -d '{ "userId": 5, "bookId": 10 }'
```

> Business rule example: a user can borrow at most **3** active books (configurable in service).

---

## 🔍 If Swagger UI Does Not Work

If you cannot access Swagger UI, check the following:

1. **Endpoints**  
   Try both paths (depending on springdoc version):  
   - `/swagger-ui/index.html`
   - `/swagger-ui.html`

2. **Dependencies (pom.xml)**  
   Ensure springdoc dependency exists:
   ```xml
   <dependency>
     <groupId>org.springdoc</groupId>
     <artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>
     <version>2.5.0</version>
   </dependency>
   ```

3. **SecurityConfig**  
   Permit swagger endpoints:
   ```java
   .requestMatchers("/swagger-ui.html", "/swagger-ui/**", "/v3/api-docs/**", "/api-docs/**").permitAll()
   ```

4. **Profiles/Config**  
   Swagger disabled in prod? Check:
   ```yaml
   springdoc:
     api-docs:
       enabled: true
     swagger-ui:
       enabled: true
   ```

5. **Context Path**  
   If you use `server.servlet.context-path=/app`, Swagger is under `/app/swagger-ui/index.html`.

6. **CORS/Reverse Proxy**  
   If behind a proxy (Nginx/Traefik), ensure paths are forwarded and not cached incorrectly.

7. **Logs**  
   Check app logs for 404/401/403 around swagger resources.

---

## 📌 Notes

- With **Flyway**, set `spring.jpa.hibernate.ddl-auto=none` and maintain schema via migrations.
- Keep `JWT_SECRET_KEY` in environment variables or `.env`.
- For Docker, use env-based configuration (`SPRING_DATASOURCE_*`, etc.).

---

## 📜 License

This project is for personal learning and development purposes. It can be used as a reference by anyone.
