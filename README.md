# QuadShop API

A RESTful backend service built with **Java Spring Boot**, powering the QuadShop merchandise platform. Provides endpoints for managing products, images, and related data. Developed in IntelliJ IDEA with MySQL as the primary database.

---

## 🚀 Features

### 🧩 Product Management
- Retrieve all products
- Retrieve a single product
- Create new products
- Update existing products
- Delete products
- Manage product sizes and specifications

### 🖼️ Image Management
- Upload product images
- Delete images
- Integration with **Cloudinary** for external image hosting
- Image metadata stored in MySQL

---

## 🧰 Tech Stack

| Layer | Technology |
|---|---|
| Language | Java |
| Framework | Spring Boot |
| Database | MySQL |
| ORM | Spring Data JPA |
| Image Hosting | Cloudinary |
| Build Tool | Gradle |
| IDE | IntelliJ IDEA |

---

## 🗄️ Database Structure

The API uses a MySQL database with the following tables:

- `products`
- `images`
- `sizes`
- `specifications`
- `orders`
- `order_items`

Each table is mapped to a JPA entity in the `domain` package.

---

## 🗂️ Project Structure

```
src/
└── main/
    ├── java/com/example/QuadShop
    │   ├── business/         # Service layer
    │   ├── controller/       # REST controllers
    │   ├── domain/           # Entities / Models
    │   ├── persistence/      # Repositories (Spring Data JPA)
    │   ├── Configuration/    # Cloudinary, CORS, etc.
    │   └── QuadShopApplication.java
    └── resources/
        ├── application.properties
        └── static / templates (if used)
```

---

## ⚙️ Setup & Installation

### 1. Clone the repository

```bash
git clone https://github.com/your-repo/QuadShop_API.git
cd QuadShop_API
```

### 2. Configure environment variables

Edit `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/quadshop
spring.datasource.username=root
spring.datasource.password=yourpassword

cloudinary.cloud_name=xxxx
cloudinary.api_key=xxxx
cloudinary.api_secret=xxxx
```

### 3. Run the application

```bash
./gradlew bootRun
```

Or run directly from IntelliJ IDEA.

---

## 📡 API Endpoints

### Products

| Method | Endpoint | Description |
|---|---|---|
| `GET` | `/products` | Retrieve all products |
| `GET` | `/products/{id}` | Retrieve a single product |
| `POST` | `/products` | Create a new product |
| `PUT` | `/products/{id}` | Update an existing product |
| `DELETE` | `/products/{id}` | Delete a product |

### Images

| Method | Endpoint | Description |
|---|---|---|
| `POST` | `/images/upload` | Upload a product image |
| `DELETE` | `/images/{id}` | Delete an image |

> More endpoints will be added as the API grows.

---

## 📄 License

This project is owned by **Quad Solutions**. Unauthorized distribution or reproduction is prohibited.
