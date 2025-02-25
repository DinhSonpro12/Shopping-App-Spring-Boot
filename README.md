# **ShopApp**  
**ShopApp** is a RESTful e-commerce application built using ***Spring Boot*** and ***MySQL***. It provides essential shopping functionalities such as category management, product listing, order processing, and user authentication with ***Spring Security***.  

---



# **Table of Contents**  
- [**Introductions**](#introductions)  
- [**Project Structure**](#project-structure)  
- [**Technologies**](#technologies)  
- [**Install & Config**](#install--config)  
  - [***Install***](#install)  
  - [***Config***](#config)  
- [**Features**](#features)  
  - [***Main Features:***](#main-features)  
  - [***Security & Error Handling:***](#security--error-handling)  
- [**Illustrations**](#illustrations)  
- [**Deployment**](#deployment)  
- [**Author**](#author)  

---

# **Introductions**  
The **ShopApp** project is divided into **two** main parts:  

- **Backend (Spring Boot)**:  
  - Provides RESTful APIs for product management, order processing, and user authentication.  
  - Implements ***Spring Security*** for secure login and role-based access control.  
  - Uses **MySQL** as the database.  

- **Frontend (Future Development)**:  
  - Can be built using **ReactJS, Angular, or VueJS** to interact with the backend APIs.  

---

# **Project Structure**  

```
ShopApp/                <Project>
├── src/                : Source code directory
│   ├── main/           
│   │   ├── java/com/shopapp/
│   │   │   ├── config/       : Security & application configurations
│   │   │   ├── controllers/  : Handle API requests
│   │   │   ├── dtos/          : Data transfer objects
│   │   │   ├── models/     : Database entity models
│   │   │   ├── repositories/ : Database access layer (JPA)
│   │   │   ├── services/     : Business logic & service layer
│   │   │   └── ShopApp.java  : Main Spring Boot application
│   │   ├── resources/
│   │   │   ├── application.yml  : Application configuration
│   │   │   ├── data.sql                 : Sample data for testing
│   │   │   └── schema.sql               : Database schema definition
│   ├── test/          : Unit tests and integration tests
│
├── pom.xml            : Maven dependencies
├── .gitignore         : Ignored files for version control
└── README.md          : Project documentation
```

---

# **Technologies**  
- **Backend**: `Spring Boot, Spring Security, Spring Data JPA, MySQL, RESTful API.`  
- **Authentication**: `Spring Security with JWT.`  
- **Database**: `MySQL with Hibernate (JPA).`  
- **Build Tool**: `Maven.`  

---

# **Install & Config**  
## ***Install***  
1. **Clone the project**  
   ```sh
   git clone https://github.com/your-username/ShopApp.git
   cd ShopApp
   ```

2. **Setup database** (MySQL)  
   - Create a **new database** in MySQL:  
     ```sql
     CREATE DATABASE shopapp;
     ```

3. **Update `application.properties`**  
   - Open `src/main/resources/application.properties` and configure:  
     ```properties
     spring.datasource.url=jdbc:mysql://localhost:3306/shopapp
     spring.datasource.username=root
     spring.datasource.password=yourpassword
     ```

4. **Run the application**  
   ```sh
   mvn spring-boot:run
   ```

---

## ***Config***  
### **Environment Variables (`.env` file)**  
Create a `.env` file in the root directory and add:  
```env
DB_URL=jdbc:mysql://localhost:3306/shopapp
DB_USERNAME=root
DB_PASSWORD=yourpassword
JWT_SECRET=my_secret_key
```
Then, update `application.properties`:  
```properties
spring.datasource.url=${DB_URL}
spring.datasource.username=${DB_USERNAME}
spring.datasource.password=${DB_PASSWORD}
jwt.secret=${JWT_SECRET}
```

---

# **Features**  
## ***Main Features:***  
✅ **Category Management**:  
   - Create, update, delete categories.  
   - Retrieve category list.  

✅ **Product Management**:  
   - Add, update, remove products.  
   - List products by category.  

✅ **Order & Order Details**:  
   - Place an order.  
   - View order details.  
   - Manage order status (Pending, Shipped, Delivered).  

✅ **User Authentication & Authorization**:  
   - Register/Login users.  
   - Use **JWT (JSON Web Token)** for authentication.  
   - Role-based access control (Admin/User).  

✅ **RESTful API**:  
   - Well-structured API endpoints using Spring Boot controllers.  

---

## ***Security & Error Handling:***  
✅ **Spring Security for Authentication**  
✅ **JWT Token-Based Authorization**  
✅ **Global Exception Handling** using `@ControllerAdvice`  
✅ **Validation for User Input** (`@Valid`, `@NotNull`, etc.)  
✅ **Cross-Origin Resource Sharing (CORS) Configuration**  

---

# **Illustrations**  
📌 **Database** 
## ***List of tables***
| **Table Name**   | **Description** |
|-----------------|--------------------------------|
| `users`        | Stores user account information |
| `products`     | List of available products |
| `categories`   | Product categories |
| `orders`       | Order details and status |
| `order_details` | Products included in an order |
| `roles`        | Stores user roles and permissions |
| `tokens`       | Stores authentication tokens |  

## ***ERD Diagram***

<p align="center">
  <img src="https://github.com/user-attachments/assets/2ba6ddf3-16c4-450e-82bc-4e0899ee6d96" 
       alt="ERD Diagram" title="ERD Diagram"/>
</p>
<p align="center"><u>Image: ERD Diagram </u></p>  



<!-- ## ***SQL Schema*** -->
<!-- -->



📌 **API Workflow**  
<p align="center">
  <img src="https://github.com/user-attachments/assets/2ba6ddf3-16c4-450e-82bc-4e0899ee6d96" 
       alt="ERD Diagram" title="ERD Diagram"/>
</p>
<p align="center"><u>Image: API Request Flow</u></p>  

---

# **Deployment**  
### **Deploy Backend (Spring Boot) using [Render](https://render.com/):**  
- To deploy the backend API, use **Render**, a cloud hosting platform.  

### **Deploy Database (MySQL) using [PlanetScale](https://planetscale.com/):**  
- You can use **PlanetScale** to host a cloud MySQL database.  

> 🚀 ***Live API Endpoint:*** [**ShopApp API**](https://your-api-url.com)  
> `Note: The API server may take a few seconds to start if in idle state.`  

---

# **Author**  
📌 ***Name***: [**Dinh Trong Tung Son**](https://github.com/DinhSonpro12)
📌 ***Email***: dinhtrongtungson2626@gmail.com  

💬 ***If you have any questions or suggestions, feel free to contact me via email.*** 🚀
