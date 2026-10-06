# 🔒 VaultNote — Personal Workspace & Encrypted Vault

VaultNote is a production-grade personal productivity workspace featuring nested hierarchical documentation, a zero-knowledge encrypted private diary vault, and a precise financial expense planner.

I am building this project as an experiment just purely out of curiosity, will include more as I progress.

## 🛠️ Tech Stack
- **Backend:** Java 21, Spring Boot 3.x, Spring Security, Spring Data JPA
- **Database & Cache:** PostgreSQL 16, Redis
- **Frontend:** JavaScript (React), Tailwind CSS, Tiptap Editor

## 📐 System Architecture
```mermaid
graph TD
    Client[React Frontend] -->|REST API + JWT| Gateway[Spring Boot Backend]
    Gateway -->|Hierarchical Queries| DB[(PostgreSQL)]
    Gateway -->|Session Keys| Cache[(Redis)]
