# Gestor de Tareas

Aplicación full-stack para gestionar el ciclo de vida de tareas (To-Do), desarrollada con enfoque empresarial como parte de una prueba técnica.

## Características Destacadas
- **Reglas de Negocio Automáticas:** Asignación y revocación automática de la fecha de terminación según los cambios de estado.
- **Borrado Lógico (Soft Delete):** Las tareas eliminadas se ocultan mediante una bandera `activo`, manteniendo la integridad histórica en la base de datos sin perder información.
- **Niveles de Prioridad:** Clasificación de tareas por prioridad (Alta, Media, Baja) con indicadores visuales en la interfaz.
- **Edición "Inline":** Modificación de título y descripción directamente sobre la tarjeta de la tarea sin redirecciones.
- **Búsqueda en Tiempo Real:** Filtrado instantáneo de tareas por título en el frontend, optimizado para no sobrecargar el servidor.

## Stack tecnológico

**Backend:** Java 25, Spring Boot, Spring Data JPA, PostgreSQL, Flyway
**Frontend:** React (Vite), Tailwind CSS
**Infraestructura local:** Docker (PostgreSQL)

## Ciclo de vida de una tarea

```text
[ PENDING ] ---> [ IN_PROGRESS ] ---> [ COMPLETED ]
     |
     └---> [ CANCELLED ]
