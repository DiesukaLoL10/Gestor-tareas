export const ESTATUS_FILTRO = [null, 'PENDING', 'IN_PROGRESS', 'COMPLETED', 'CANCELLED'];

export const ESTATUS_LABEL = {
    PENDING: 'Pendiente',
    IN_PROGRESS: 'En progreso',
    COMPLETED: 'Completada',
    CANCELLED: 'Cancelada',
};

export function etiquetaEstatus(estatus) {
    return ESTATUS_LABEL[estatus] ?? estatus ?? 'Todas';
}

export function mensajeListaVacia(estatus) {
    if (!estatus) return 'No hay tareas todavía.';
    const mensajes = {
        PENDING: 'No hay tareas pendientes.',
        IN_PROGRESS: 'No hay tareas en progreso.',
        COMPLETED: 'No hay tareas completadas.',
        CANCELLED: 'No hay tareas canceladas.',
    };
    return mensajes[estatus] ?? 'No hay tareas en este estado.';
}
