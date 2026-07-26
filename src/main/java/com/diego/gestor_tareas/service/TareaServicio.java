package com.diego.gestor_tareas.service;


import com.diego.gestor_tareas.model.Tarea;
import com.diego.gestor_tareas.model.TareaEstado;
import com.diego.gestor_tareas.exception.TareaNoEncontrada;
import com.diego.gestor_tareas.exception.TransicionInvalidaEstado;
import com.diego.gestor_tareas.repository.TareasRepositorio;

import org.springframework.scheduling.config.Task;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.EnumMap;
import java.util.EnumSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class TareaServicio {
    
    private final TareasRepositorio tareaRepositorio;
    
    //Mapa del ciclo de vida de cada estado
    private static final Map<TareaEstado, Set<TareaEstado>> TransicionesValidas = new EnumMap<>(TareaEstado.class);


    static {
        TransicionesValidas.put(TareaEstado.PENDING, EnumSet.of(TareaEstado.IN_PROGRESS, TareaEstado.CANCELLED));
        TransicionesValidas.put(TareaEstado.IN_PROGRESS, EnumSet.of(TareaEstado.COMPLETED, TareaEstado.CANCELLED));
        TransicionesValidas.put(TareaEstado.COMPLETED, EnumSet.noneOf(TareaEstado.class)); // estado final
        TransicionesValidas.put(TareaEstado.CANCELLED, EnumSet.noneOf(TareaEstado.class)); // estado final
    }

    public TareaServicio(TareasRepositorio tareasRepositorio){
        this.tareaRepositorio = tareasRepositorio;
    }

    @Transactional
    public Tarea crearTarea(String titulo, String descripcion){
        Tarea tarea = new Tarea(titulo, descripcion);
        return tareaRepositorio.save(tarea);
    }

    @Transactional(readOnly = true)
    public List<Tarea> getTodasTareas() {
        return tareaRepositorio.findAll();
    }

    @Transactional(readOnly = true)
    public List<Tarea> getTareaByEstatus(TareaEstado estatus) {
        return tareaRepositorio.findByEstatus(estatus);
    }

    @Transactional(readOnly = true)
    public Tarea getTareaById(Long id) {
        return tareaRepositorio.findById(id)
                .orElseThrow(() -> new TareaNoEncontrada(id));
    }


    @Transactional
    public Tarea actualizarEstatus(Long id, TareaEstado nuevoEstado) {
        Tarea tarea = getTareaById(id);
        TareaEstado estadoActual = tarea.getEstatus();

        Set<TareaEstado> estadosPermitidos = TransicionesValidas.get(estadoActual);

        if (!estadosPermitidos.contains(nuevoEstado)) {
            throw new TransicionInvalidaEstado(estadoActual, nuevoEstado);
        }

        tarea.setStatus(nuevoEstado);
        return tareaRepositorio.save(tarea);
    }

    @Transactional
    public void borrarTarea(Long id) {
        Tarea tarea = getTareaById(id); // valida que exista antes de borrar
        tareaRepositorio.delete(tarea);
    }

}
