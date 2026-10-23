import { useState } from 'react';
import { createTask } from '../services/TareaServicio.js';

function TareaForm({ onTaskCreated }) {
    const [titulo, setTitulo] = useState('');
    const [descripcion, setDescripcion] = useState('');
    const [fechaInicio, setFechaInicio] = useState('');
    const [prioridad, setPrioridad] = useState('MEDIA'); // Nuevo estado
    const [error, setError] = useState(null);
    const [loading, setLoading] = useState(false);

    const fechaActual = new Date().toISOString().split('T')[0];

    async function handleSubmit(e) {
        e.preventDefault();
        setError(null);
        setLoading(true);

        try {
            // IMPORTANTE: Asegúrate de que tu TareaServicio reciba y envíe esta prioridad
            const newTask = await createTask(titulo, descripcion, fechaInicio, prioridad);
            onTaskCreated(newTask);
            setTitulo('');
            setDescripcion('');
            setFechaInicio('');
            setPrioridad('MEDIA'); // Resetear
        } catch (err) {
            setError(err.message);
        } finally {
            setLoading(false);
        }
    }

    return (
        <form onSubmit={handleSubmit} className="bg-white p-4 rounded-lg shadow mb-6">
            <h2 className="text-lg font-semibold mb-3">Nueva tarea</h2>

            {error && (
                <div className="bg-red-100 text-red-700 p-2 rounded mb-3 text-sm">
                    {error}
                </div>
            )}

            <div className="flex flex-col sm:flex-row gap-3 mb-3">
                <input
                    type="text"
                    placeholder="Título"
                    value={titulo}
                    onChange={(e) => setTitulo(e.target.value)}
                    className="w-full sm:w-2/3 border rounded p-2"
                    required
                />
                <select
                    value={prioridad}
                    onChange={(e) => setPrioridad(e.target.value)}
                    className="w-full sm:w-1/3 border rounded p-2 bg-white"
                >
                    <option value="ALTA">Prioridad: ALTA</option>
                    <option value="MEDIA">Prioridad: MEDIA</option>
                    <option value="BAJA">Prioridad: BAJA</option>
                </select>
            </div>

            <textarea
                placeholder="Descripción (opcional)"
                value={descripcion}
                onChange={(e) => setDescripcion(e.target.value)}
                className="w-full border rounded p-2 mb-3"
            />

            <input
                type="date"
                placeholder="Fecha de inicio"
                value={fechaInicio}
                min={fechaActual}
                onChange={(e) => setFechaInicio(e.target.value)}
                className="w-full border rounded p-2 mb-3"
                required
            />

            <button
                type="submit"
                disabled={loading}
                className="bg-blue-600 text-white px-4 py-2 rounded hover:bg-blue-700 disabled:opacity-50 w-full sm:w-auto"
            >
                {loading ? 'Creando...' : 'Crear tarea'}
            </button>
        </form>
    );
}

export default TareaForm;