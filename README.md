# 📚 Spring Boot REST API - Books Management

🔗 Repository: https://github.com/pbhukya197/SpringBoot4-REST-APIs

---

## 📖 Description

This project is a RESTful API built using Spring Boot for managing books.
It demonstrates core backend concepts like CRUD operations, validation, exception handling, and API documentation using Swagger.

The project is designed to help understand how real-world REST APIs are built using Java and Spring Boot.

---

## 🚀 Features

* CRUD operations (Create, Read, Update, Delete)
* RESTful API design
* Input validation using Jakarta Validation
* Custom exception handling
* Swagger/OpenAPI integration for API documentation
* Clean project structure
* In-memory data handling (no database)

---

## 🛠️ Tech Stack

* Java
* Spring Boot
* Spring Web
* Maven
* Swagger / OpenAPI
* Jakarta Validation

---

## 📁 Project Structure

```
restapi.booking.books
│
├── controller        # REST Controllers
├── entity            # Entity classes (Book)
├── request           # DTOs (BookRequest)
├── exception         # Custom exceptions & handlers
└── resources         # application.properties
```

---

## 📌 API Endpoints

| Method | Endpoint               | Description               |
| ------ | ---------------------- | ------------------------- |
| GET    | /api/books             | Get all books             |
| GET    | /api/books/{id}        | Get book by ID            |
| GET    | /api/books/{id}/filter | Get book by ID & category |
| POST   | /api/books             | Create a new book         |
| PUT    | /api/books/{id}        | Update an existing book   |
| DELETE | /api/books/{id}        | Delete a book             |

---

## ✅ Validation

* Uses `@Valid` for request validation
* Uses annotations like `@Min`, `@NotBlank`
* Ensures only valid data is processed

---

## ⚠️ Exception Handling

* Custom exception: `BookNotFoundException`
* Global exception handling using `@RestControllerAdvice`
* Returns structured error response:

```json
{
  "status": 404,
  "message": "Book with id not found",
  "timestamp": 1712060000000
}
```

---

## 📖 Swagger API Documentation

Swagger UI is integrated for easy API testing and exploration.

👉 Access here:

```
http://localhost:8080/swagger-ui.html
```

---

## ▶️ How to Run

### 1. Clone the Repository

```
git clone https://github.com/pbhukya197/SpringBoot4-REST-APIs.git
```

### 2. Navigate to Project

```
cd SpringBoot4-REST-APIs
```

### 3. Run the Application

```
mvn spring-boot:run
```

### 4. Open in Browser

```
http://localhost:8080
```

---

## 🔮 Future Enhancements

* Database integration (MySQL / PostgreSQL)
* Spring Security (Authentication & Authorization)
* File/Image upload support
* Pagination & Sorting
* Deployment using Docker

---

## 🎯 Learning Outcomes

This project helps in understanding:

* REST API development using Spring Boot
* Request handling and validation
* Exception handling and error responses
* API documentation using Swagger
* Clean code structure and best practices

---

## 👨‍💻 Author

**Bhukya Praveen**

---

## ⭐ Support

If you like this project, give it a ⭐ on GitHub!

---
