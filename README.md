# Gestor de Tareas

Aplicación full-stack para gestionar el ciclo de vida de tareas (To-Do), desarrollada como parte de una prueba técnica.

## Stack tecnológico

**Backend:** Java 25, Spring Boot, Spring Data JPA, PostgreSQL, Flyway
**Frontend:** React (Vite), Tailwind CSS
**Infraestructura local:** Docker (PostgreSQL)

## Ciclo de vida de una tarea

\`\`\`
[ PENDING ] ---> [ IN_PROGRESS ] ---> [ COMPLETED ]
     |
     └---> [ CANCELLED ]
\`\`\`

## Arquitectura

El backend sigue una separación de capas:

\`\`\`
Controller  →  recibe peticiones HTTP, valida entrada
Service     →  lógica de negocio (transiciones de estado válidas)
Repository  →  acceso a datos (Spring Data JPA)
Model       →  entidades JPA
DTO         →  objetos de entrada/salida (no se expone la entidad directamente)
Exception   →  manejo centralizado de errores (@RestControllerAdvice)
\`\`\`

## Requisitos previos

- Java 21+ (JDK)
- Node.js 18+ y pnpm
- Docker Desktop

## Cómo levantar el proyecto

### 1. Base de datos (PostgreSQL con Docker)

\`\`\`bash
cd backend
docker compose up -d
\`\`\`

### 2. Backend

Configura las variables de entorno (ver \`backend/src/main/resources/application.properties\`):
- \`DB_USERNAME\`
- \`DB_PASSWORD\`

\`\`\`bash
cd backend
./mvnw spring-boot:run
\`\`\`
El backend queda disponible en \`http://localhost:8080\`.

### 3. Frontend

\`\`\`bash
cd frontend
pnpm install
pnpm dev
\`\`\`
El frontend queda disponible en \`http://localhost:5173\`.

## Endpoints principales

| Método | Ruta                        | Descripción                          |
|--------|-----------------------------|---------------------------------------|
| POST   | /api/tareas                 | Crear una tarea                       |
| GET    | /api/tareas                 | Listar todas las tareas                |
| GET    | /api/tareas?estatus=X        | Filtrar tareas por estado              |
| GET    | /api/tareas/{id}             | Obtener una tarea por id                |
| PATCH  | /api/tareas/{id}/status      | Cambiar el estado de una tarea          |
| DELETE | /api/tareas/{id}             | Eliminar una tarea                      |

## Decisiones de diseño destacadas

- **Migraciones con Flyway + `ddl-auto=validate`**: el esquema de base de datos se controla explícitamente mediante scripts SQL versionados, no mediante generación automática de Hibernate.
- **DTOs separados de las entidades**: evita acoplar la API pública a la estructura interna de persistencia.
- **Manejo centralizado de errores**: un único `@RestControllerAdvice` traduce las excepciones de negocio a códigos HTTP correctos (400, 404, 422).
- **Configuración externalizada**: las credenciales de base de datos se inyectan por variables de entorno, no se hardcodean en el código.
- **Índice en la columna `status`**: para consultas eficientes al filtrar tareas por estado.