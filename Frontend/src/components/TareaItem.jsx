import { useState } from 'react';
import { updateTaskStatus, deleteTask } from '../services/TareaServicio.js';

const TRANSICIONES_VALIDAS = {
    PENDING: ['IN_PROGRESS', 'CANCELLED'],
    IN_PROGRESS: ['COMPLETED', 'CANCELLED'],
    COMPLETED: [],
    CANCELLED: [],
};

const COLOR_ESTADO = {
    PENDING: 'bg-yellow-100 text-yellow-800',
    IN_PROGRESS: 'bg-blue-100 text-blue-800',
    COMPLETED: 'bg-green-100 text-green-800',
    CANCELLED: 'bg-gray-100 text-gray-600',
};

function TareaItem({ task, onTaskUpdated, onTaskDeleted }) {
    const [error, setError] = useState(null);

    async function handleStatusChange(newStatus) {
        setError(null);
        try {
            const updated = await updateTaskStatus(task.id, newStatus);
            onTaskUpdated(updated);
        } catch (err) {
            setError(err.message);
        }
    }

    async function handleDelete() {
        try {
            await deleteTask(task.id);
            onTaskDeleted(task.id);
        } catch (err) {
            setError(err.message);
        }
    }

    const opcionesDisponibles = TRANSICIONES_VALIDAS[task.estatus] ?? [];

    return (
        <div className="bg-white p-4 rounded-lg shadow mb-3">
            <div className="flex justify-between items-start">
                <div>
                    <h3 className="font-semibold">{task.titulo}</h3>
                    {task.descripcion && (
                        <p className="text-gray-600 text-sm mt-1">{task.descripcion}</p>
                    )}
                </div>
                <span className={`text-xs px-2 py-1 rounded-full ${COLOR_ESTADO[task.estatus] ?? 'bg-gray-100'}`}>
                    {task.estatus}
                </span>
            </div>

            {error && <p className="text-red-600 text-sm mt-2">{error}</p>}

            <div className="flex gap-2 mt-3">
                {opcionesDisponibles.map((status) => (
                    <button
                        key={status}
                        onClick={() => handleStatusChange(status)}
                        className="text-xs bg-gray-200 hover:bg-gray-300 px-2 py-1 rounded"
                    >
                        → {status}
                    </button>
                ))}
                <button
                    onClick={handleDelete}
                    className="text-xs bg-red-100 text-red-700 hover:bg-red-200 px-2 py-1 rounded ml-auto"
                >
                    Eliminar
                </button>
            </div>
        </div>
    );
}

export default TareaItem;