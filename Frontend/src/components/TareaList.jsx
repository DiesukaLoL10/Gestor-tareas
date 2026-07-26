import { useEffect, useState } from 'react';
import { getAllTasks } from '../services/TareaServicio.js';
import TareaItem from './TareaItem.jsx';
import TareaForm from './TareaForm.jsx';

function TaskList() {
    const [tasks, setTasks] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);
    const [filtro, setFiltro] = useState(null);

    useEffect(() => {
        cargarTareas();
    }, [filtro]);

    async function cargarTareas() {
        setLoading(true);
        setError(null);
        try {
            const data = await getAllTasks(filtro);
            setTasks(data);
        } catch (err) {
            setError(err.message);
        } finally {
            setLoading(false);
        }
    }

    function handleTaskCreated(newTask) {
        setTasks((prev) => [...prev, newTask]);
    }

    function handleTaskUpdated(updatedTask) {
        setTasks((prev) =>
            prev.map((t) => (t.id === updatedTask.id ? updatedTask : t))
        );
    }

    function handleTaskDeleted(id) {
        setTasks((prev) => prev.filter((t) => t.id !== id));
    }

    return (
        <div className="max-w-2xl mx-auto p-4">
            <h1 className="text-2xl font-bold mb-4">Gestor de Tareas</h1>

            <TareaForm onTaskCreated={handleTaskCreated} />

            <div className="flex gap-2 mb-4">
                {[null, 'PENDING', 'IN_PROGRESS', 'COMPLETED', 'CANCELLED'].map((status) => (
                    <button
                        key={status ?? 'ALL'}
                        onClick={() => setFiltro(status)}
                        className={`text-sm px-3 py-1 rounded ${
                            filtro === status ? 'bg-blue-600 text-white' : 'bg-gray-200'
                        }`}
                    >
                        {status ?? 'Todas'}
                    </button>
                ))}
            </div>

            {loading && <p>Cargando tareas...</p>}
            {error && <p className="text-red-600">{error}</p>}

            {!loading && tasks.length === 0 && (
                <p className="text-gray-500">No hay tareas todavía.</p>
            )}

            {tasks.map((task) => (
                <TareaItem
                    key={task.id}
                    task={task}
                    onTaskUpdated={handleTaskUpdated}
                    onTaskDeleted={handleTaskDeleted}
                />
            ))}
        </div>
    );
}

export default TaskList;