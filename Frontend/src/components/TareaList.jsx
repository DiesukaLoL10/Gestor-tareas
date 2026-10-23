import { useEffect, useState } from 'react';
import { getAllTasks, getConteoPorEstado } from '../services/TareaServicio.js';
import TareaItem from './TareaItem.jsx';
import TareaForm from './TareaForm.jsx';
import { ESTATUS_FILTRO, etiquetaEstatus, mensajeListaVacia } from '../constants/estatus.js';

function mapearConteos(data) {
    return Object.fromEntries(
        data.map((item) => [item.estatus, Number(item.count)])
    );
}

function TaskList() {
    const [tasks, setTasks] = useState([]);
    const [conteos, setConteos] = useState({});
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);
    const [filtro, setFiltro] = useState(null);
    const [busqueda, setBusqueda] = useState('');

    async function cargarConteos() {
        const data = await getConteoPorEstado();
        setConteos(mapearConteos(data));
    }

    useEffect(() => {
        let cancelado = false;

        async function cargarTareas() {
            try {
                const [data, conteoData] = await Promise.all([
                    getAllTasks(filtro), // <-- Ya no le pasamos la búsqueda a la API
                    getConteoPorEstado(),
                ]);
                if (cancelado) return;
                setTasks(data);
                setConteos(mapearConteos(conteoData));
                setError(null);
            } catch (err) {
                if (!cancelado) setError(err.message);
            } finally {
                if (!cancelado) setLoading(false);
            }
        }

        cargarTareas();
        return () => {
            cancelado = true;
        };
    }, [filtro]); // <-- Quitamos 'busqueda' para no llamar al backend al teclear

    function coincideConFiltro(estatus) {
        return filtro == null || estatus === filtro;
    }

    function handleTaskCreated(newTask) {
        if (coincideConFiltro(newTask.estatus)) {
            setTasks((prev) => [...prev, newTask]);
        }
        cargarConteos().catch(() => {});
    }

    function handleTaskUpdated(updatedTask) {
        setTasks((prev) => {
            if (!coincideConFiltro(updatedTask.estatus)) {
                return prev.filter((t) => t.id !== updatedTask.id);
            }
            return prev.map((t) => (t.id === updatedTask.id ? updatedTask : t));
        });
        cargarConteos().catch(() => {});
    }

    function handleTaskDeleted(id) {
        setTasks((prev) => prev.filter((t) => t.id !== id));
        cargarConteos().catch(() => {});
    }

    const total = Object.values(conteos).reduce((sum, n) => sum + n, 0);

    function cantidadFiltro(status) {
        if (status == null) return total;
        return conteos[status] ?? 0;
    }

    // Filtrado en memoria: Rápido e instantáneo
    const tareasFiltradas = tasks.filter((task) =>
        task.titulo.toLowerCase().includes(busqueda.toLowerCase())
    );

    return (
        <div className="max-w-2xl mx-auto p-4">
            <h1 className="text-2xl font-bold mb-4">Gestor de Tareas</h1>

            {/* ¡Regresamos el formulario que Qwen borró! */}
            <TareaForm onTaskCreated={handleTaskCreated} />

            <div className="flex flex-wrap gap-2 mb-4">
                <input
                    type="text"
                    placeholder="Buscar por título..."
                    value={busqueda}
                    onChange={(e) => setBusqueda(e.target.value)}
                    className="w-full sm:w-1/3 border rounded p-2"
                />
                {ESTATUS_FILTRO.map((status) => (
                    <button
                        key={status ?? 'ALL'}
                        onClick={() => setFiltro(status)}
                        className={`text-sm px-3 py-1 rounded ${
                            filtro === status ? 'bg-blue-600 text-white' : 'bg-gray-200'
                        }`}
                    >
                        {etiquetaEstatus(status)} ({cantidadFiltro(status)})
                    </button>
                ))}
            </div>

            {loading && <p>Cargando tareas...</p>}
            {error && <p className="text-red-600">{error}</p>}

            {!loading && !error && tareasFiltradas.length === 0 && (
                <p className="text-gray-500">
                    {busqueda ? 'No se encontraron tareas con esa búsqueda.' : mensajeListaVacia(filtro)}
                </p>
            )}

            {/* Usamos el arreglo filtrado en lugar del original */}
            {!loading && tareasFiltradas.map((task) => (
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