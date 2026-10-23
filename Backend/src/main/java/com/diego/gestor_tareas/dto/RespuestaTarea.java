package com.diego.gestor_tareas.dto;

import com.diego.gestor_tareas.model.Tarea;
import com.diego.gestor_tareas.model.TareaEstado;
import com.diego.gestor_tareas.model.TareaPrioridad;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class RespuestaTarea {

    private final Long id;
    private final String titulo;
    private final String descripcion;
    private final TareaEstado estatus;
    private final LocalDateTime creadoEl;
    private final LocalDateTime actualizadoEl;
    private final LocalDate fechaInicio;
    private final LocalDate fechaTerminacion;
    private final TareaPrioridad prioridad;

    public RespuestaTarea(Long id, String titulo, String descripcion, TareaEstado estatus,
                         LocalDateTime creadoEl, LocalDateTime actualizadoEl, LocalDate fechaInicio, LocalDate fechaTerminacion, TareaPrioridad prioridad) {
        this.id = id;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.estatus = estatus;
        this.creadoEl = creadoEl;
        this.actualizadoEl = actualizadoEl;
        this.fechaInicio = fechaInicio;
        this.fechaTerminacion = fechaTerminacion;
        this.prioridad = prioridad;
    }

    //convierte una entidad tarea en su representación de salida
    public static RespuestaTarea fromEntity(Tarea tarea) {
        return new RespuestaTarea(
                tarea.getId(),
                tarea.getTitulo(),
                tarea.getDescripcion(),
  
                tarea.getEstatus() != null ? tarea.getEstatus() : TareaEstado.PENDING,
                tarea.getCreadoEl(),
                tarea.getActualizadoEl(),
                tarea.getFechaInicio(),
                tarea.getFechaTerminacion(),
  
                tarea.getPrioridad() != null ? tarea.getPrioridad() : TareaPrioridad.MEDIA 
        );
    }

    public Long getId() { return id; }
    public String getTitulo() { return titulo; }
    public String getDescripcion() { return descripcion; }
    public TareaEstado getEstatus() { return estatus; }
    public LocalDateTime getCreadoEl() { return creadoEl; }
    public LocalDateTime getActualizadoEl() { return actualizadoEl; }
    public LocalDate getFechaInicio() { return fechaInicio; }
    public LocalDate getFechaTerminacion() { return fechaTerminacion; }
    public TareaPrioridad getPrioridad() { return prioridad; }
}









