package com.diego.gestor_tareas.service;

import com.diego.gestor_tareas.model.Tarea;
import com.diego.gestor_tareas.model.TareaEstado;
import com.diego.gestor_tareas.exception.TareaNoEncontrada;
import com.diego.gestor_tareas.exception.TransicionInvalidaEstado;
import com.diego.gestor_tareas.repository.TareasRepositorio;
import com.diego.gestor_tareas.dto.PeticionConteoEstado;
import com.diego.gestor_tareas.model.TareaPrioridad;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Service
public class TareaServicio {
    
    private final TareasRepositorio tareaRepositorio;
    
    public TareaServicio(TareasRepositorio tareasRepositorio){
        this.tareaRepositorio = tareasRepositorio;
    }

    @Transactional
    public Tarea crearTarea(String titulo, String descripcion, LocalDate fechaInicio, TareaPrioridad prioridad){
        Tarea tarea = new Tarea();
        tarea.setTitulo(titulo);
        tarea.setDescripcion(descripcion);
        tarea.setFechaInicio(fechaInicio);
        tarea.setPrioridad(prioridad);
        return tareaRepositorio.save(tarea);
    }

    @Transactional
    public Tarea actualizarDetalles(Long id, String titulo, String descripcion) {
        Tarea tarea = getTareaById(id); // Usa tu método que lanza la excepción si no existe
        tarea.setTitulo(titulo);
        tarea.setDescripcion(descripcion);
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
    public long getTareasCountByEstatus(TareaEstado estatus) {
        return tareaRepositorio.countByEstatus(estatus);
    }

    @Transactional(readOnly = true)
    public List<PeticionConteoEstado> contarTareasPorEstado() {
        return tareaRepositorio.contarTareasPorEstado();
    }

    @Transactional(readOnly = true)
    public Tarea getTareaById(Long id) {
        return tareaRepositorio.findById(id)
                .orElseThrow(() -> new TareaNoEncontrada(id));
    }

    @Transactional
    public Tarea actualizarEstatus(Long id, TareaEstado nuevoEstado) {
        Tarea tarea = getTareaById(id);
        
        
        tarea.setEstatus(nuevoEstado); 
        
        return tareaRepositorio.save(tarea);
    }

    @Transactional
    public void borrarTarea(Long id) {
        Tarea tarea = getTareaById(id); // valida que exista antes de borrar
        tareaRepositorio.delete(tarea);
    }
}