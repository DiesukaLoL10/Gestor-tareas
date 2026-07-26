const API_URL = 'http://localhost:8080/api/tareas';

export async function getAllTasks(estatus = null) {
    const url = estatus ? `${API_URL}?estatus=${estatus}` : API_URL;
    const response = await fetch(url);
    if (!response.ok) {
        throw new Error('Error al obtener las tareas');
    }
    return response.json();
}

export async function createTask(titulo, descripcion) {
    const response = await fetch(API_URL, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ titulo, descripcion }),
    });
    if (!response.ok) {
        const errorData = await response.json();
        throw new Error(errorData.message || 'Error al crear la tarea');
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

export async function deleteTask(id) {
    const response = await fetch(`${API_URL}/${id}`, {
        method: 'DELETE',
    });
    if (!response.ok) {
        throw new Error('Error al eliminar la tarea');
    }
}