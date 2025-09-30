# 📝 Simple Blog App (Spring Boot + PostgreSQL + JWT)

A backend API built with **Spring Boot 3**, **Spring Security (JWT)**, and **PostgreSQL**, supporting users, roles, authentication, and pagination.

---

## 🚀 Features
- User authentication with **JWT access & refresh tokens**
- Roles: `USER`, `ADMIN`, `SUPERADMIN`
  - `ADMIN` + `SUPERADMIN` can create posts
  - Only `SUPERADMIN` can register new admins
- Global exception handling + security exception handling
- Passwords stored securely with **BCrypt**
- Pagination for post listing (`page` & `size`, default = 10)

---

## 🛠️ Tech Stack
- **Java 17+**
- **Spring Boot 3**
- **Spring Security** (JWT auth)
- **Spring Data JPA (Hibernate)**
- **PostgreSQL**
- **BCrypt** for password hashing

