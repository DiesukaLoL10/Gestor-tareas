const API_URL = 'http://localhost:8080/api/tareas';

export async function getAllTasks(estatus = null) {
    const url = estatus ? `${API_URL}?estatus=${estatus}` : API_URL;
    const response = await fetch(url);
    if (!response.ok) {
        throw new Error('Error al obtener las tareas');
    }
    return response.json();
}

export async function getConteoPorEstado() {
    const response = await fetch(`${API_URL}/conteo-por-estado`);
    if (!response.ok) {
        throw new Error('Error al obtener el conteo de tareas');
    }
    return response.json();
}

export async function createTask(titulo, descripcion, fechaInicio, prioridad) {
    const response = await fetch(`${API_URL}`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ titulo, descripcion, fechaInicio, prioridad }) 
    });
    
    if (!response.ok) {
        // Intentamos leer el JSON de error de tu ManejadorGlobal
        const errData = await response.json().catch(() => null);
        
        let errorMessage = 'Error al procesar la solicitud';
        if (errData) {
            // Si hay detalles de validación (ej. título vacío), los unimos
            if (errData.detalles && errData.detalles.length > 0) {
                errorMessage = errData.detalles.join(' | ');
            } else if (errData.mensaje) {
                // Si es un error de negocio (ej. Transición inválida)
                errorMessage = errData.mensaje;
            }
        }
        throw new Error(errorMessage);
    }
    return response.json();
}

export async function updateTaskStatus(id, estatus) {
    const response = await fetch(`${API_URL}/${id}/estatus`, {
        method: 'PATCH',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ estatus }),
    });
    if (!response.ok) {
        const errorData = await response.json();
        throw new Error(errorData.message || 'Error al actualizar el estado');
    }
    return response.json();
}

export async function updateTaskDetails(id, titulo, descripcion) {
    const response = await fetch(`http://localhost:8080/api/tareas/${id}`, {
        method: 'PUT',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ titulo, descripcion })
    });
    
    if (!response.ok) {
        const errData = await response.json().catch(() => null);
        let errorMessage = 'Error al actualizar la tarea';
        if (errData) {
            if (errData.detalles && errData.detalles.length > 0) errorMessage = errData.detalles.join(' | ');
            else if (errData.mensaje) errorMessage = errData.mensaje;
        }
        throw new Error(errorMessage);
    }
    return response.json();
}

export async function deleteTask(id) {
    const response = await fetch(`${API_URL}/${id}`, {
        method: 'DELETE',
    });
    if (!response.ok) {
        throw new Error('Error al eliminar la tarea');
    }
}