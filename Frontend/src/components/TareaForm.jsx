import { useState } from 'react';
import { createTask } from '../services/TareaServicio.js';

function TareaForm({ onTaskCreated }) {
    const [titulo, setTitulo] = useState('');
    const [descripcion, setDescripcion] = useState('');
    const [error, setError] = useState(null);
    const [loading, setLoading] = useState(false);

    async function handleSubmit(e) {
        e.preventDefault();
        setError(null);
        setLoading(true);

        try {
            const newTask = await createTask(titulo, descripcion);
            onTaskCreated(newTask);
            setTitulo('');
            setDescripcion('');
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

            <input
                type="text"
                placeholder="Título"
                value={titulo}
                onChange={(e) => setTitulo(e.target.value)}
                className="w-full border rounded p-2 mb-2"
                required
            />

            <textarea
                placeholder="Descripción (opcional)"
                value={descripcion}
                onChange={(e) => setDescripcion(e.target.value)}
                className="w-full border rounded p-2 mb-3"
            />

            <button
                type="submit"
                disabled={loading}
                className="bg-blue-600 text-white px-4 py-2 rounded hover:bg-blue-700 disabled:opacity-50"
            >
                {loading ? 'Creando...' : 'Crear tarea'}
            </button>
        </form>
    );
}

export default TareaForm;