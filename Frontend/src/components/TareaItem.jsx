import { useState } from 'react';
import { updateTaskStatus, deleteTask } from '../services/TareaServicio.js';
import { updateTaskDetails } from '../services/TareaServicio.js'; // <-- Importamos la nueva función
import { etiquetaEstatus } from '../constants/estatus.js';

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

const ESTILO_PRIORIDAD = {
    ALTA: { badge: 'bg-red-100 text-red-800', border: 'border-red-500' },
    MEDIA: { badge: 'bg-orange-100 text-orange-800', border: 'border-orange-400' },
    BAJA: { badge: 'bg-blue-100 text-blue-700', border: 'border-blue-300' },
};

function TareaItem({ task, onTaskUpdated, onTaskDeleted }) {
    const [error, setError] = useState(null);
    const [isEditing, setIsEditing] = useState(false);
    
    // Estados temporales para los inputs
    const [editTitulo, setEditTitulo] = useState(task.titulo);
    const [editDescripcion, setEditDescripcion] = useState(task.descripcion || '');

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

    async function handleSaveEdits() {
        setError(null);
        try {
            const updated = await updateTaskDetails(task.id, editTitulo, editDescripcion);
            onTaskUpdated(updated);
            setIsEditing(false); // Salir del modo edición
        } catch (err) {
            setError(err.message);
        }
    }

    const opcionesDisponibles = TRANSICIONES_VALIDAS[task.estatus] ?? [];
    const estiloPrio = ESTILO_PRIORIDAD[task.prioridad] || ESTILO_PRIORIDAD.MEDIA;

    return (
        <div className={`bg-white p-4 rounded-lg shadow mb-3 border-l-4 ${estiloPrio.border}`}>
            <div className="flex flex-col sm:flex-row justify-between items-start gap-2">
                <div className="w-full">
                    {/* --- MODO EDICIÓN --- */}
                    {isEditing ? (
                        <div className="flex flex-col gap-2 mb-2 w-full pr-4">
                            <input 
                                type="text" 
                                value={editTitulo} 
                                onChange={(e) => setEditTitulo(e.target.value)}
                                className="border rounded p-1 w-full font-semibold"
                            />
                            <textarea 
                                value={editDescripcion} 
                                onChange={(e) => setEditDescripcion(e.target.value)}
                                className="border rounded p-1 w-full text-sm text-gray-600"
                                rows="2"
                            />
                            <div className="flex gap-2">
                                <button 
                                    onClick={handleSaveEdits}
                                    className="text-xs bg-green-600 text-white px-3 py-1 rounded hover:bg-green-700"
                                >
                                    Guardar
                                </button>
                                <button 
                                    onClick={() => {
                                        setIsEditing(false);
                                        setEditTitulo(task.titulo); // Cancelar revierte cambios
                                        setEditDescripcion(task.descripcion || '');
                                    }}
                                    className="text-xs bg-gray-300 text-gray-800 px-3 py-1 rounded hover:bg-gray-400"
                                >
                                    Cancelar
                                </button>
                            </div>
                        </div>
                    ) : (
                        /* --- MODO VISTA --- */
                        <>
                            <div className="flex items-center gap-2">
                                <h3 className="font-semibold">{task.titulo}</h3>
                                {task.prioridad && (
                                    <span className={`text-[10px] px-2 py-0.5 rounded font-bold uppercase ${estiloPrio.badge}`}>
                                        {task.prioridad}
                                    </span>
                                )}
                            </div>
                            {task.descripcion && (
                                <p className="text-gray-600 text-sm mt-1">{task.descripcion}</p>
                            )}
                        </>
                    )}

                    {task.fechaInicio && (
                        <p className="text-blue-600 text-xs mt-2 font-medium">
                             Inicio: {task.fechaInicio}
                        </p>
                    )}
                    {task.estatus === 'COMPLETED' && task.fechaTerminacion && (
                        <p className="text-green-600 text-xs mt-1 font-medium">
                             Terminada el: {task.fechaTerminacion}
                        </p>
                    )}
                </div>
                
                {/* Estatus Badge (Se oculta en móvil si está editando para dar espacio) */}
                {!isEditing && (
                    <span className={`text-xs px-2 py-1 rounded-full ${COLOR_ESTADO[task.estatus] ?? 'bg-gray-100'} whitespace-nowrap`}>
                        {etiquetaEstatus(task.estatus)}
                    </span>
                )}
            </div>

            {error && <p className="text-red-600 text-sm mt-2">{error}</p>}

            <div className="flex flex-wrap gap-2 mt-3 items-center">
                {opcionesDisponibles.map((status) => (
                    <button
                        key={status}
                        onClick={() => handleStatusChange(status)}
                        className="text-xs bg-gray-200 hover:bg-gray-300 px-2 py-1 rounded"
                    >
                        → {etiquetaEstatus(status)}
                    </button>
                ))}
                
                {/* Botón de Editar (Solo aparece si no estamos ya editando) */}
                {!isEditing && (
                    <button
                        onClick={() => setIsEditing(true)}
                        className="text-xs bg-blue-100 text-blue-700 hover:bg-blue-200 px-3 py-1 rounded sm:ml-auto w-full sm:w-auto mt-2 sm:mt-0"
                    >
                        Editar
                    </button>
                )}

                {/* Botón de Eliminar */}
                {!isEditing && (
                    <button
                        onClick={handleDelete}
                        className="text-xs bg-red-100 text-red-700 hover:bg-red-200 px-2 py-1 rounded w-full sm:w-auto mt-2 sm:mt-0 text-center"
                    >
                        Eliminar
                    </button>
                )}
            </div>
        </div>
    );
}

export default TareaItem;