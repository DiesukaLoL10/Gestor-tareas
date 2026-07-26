package com.diego.gestor_tareas.dto;

import com.diego.gestor_tareas.model.Tarea;
import com.diego.gestor_tareas.model.TareaEstado;

import java.time.LocalDateTime;

public class RespuestaTarea {

    private final Long id;
    private final String titulo;
    private final String descripcion;
    private final TareaEstado estatus;
    private final LocalDateTime creadoEl;
    private final LocalDateTime actualizadoEl;

    public RespuestaTarea(Long id, String titulo, String descripcion, TareaEstado estatus,
                         LocalDateTime creadoEl, LocalDateTime actualizadoEl) {
        this.id = id;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.estatus = estatus;
        this.creadoEl = creadoEl;
        this.actualizadoEl = actualizadoEl;
    }

    //convierte una entidad tarea en su representación de salida
    public static RespuestaTarea fromEntity(Tarea tarea) {
        return new RespuestaTarea(
                tarea.getId(),
                tarea.getTitle(),
                tarea.getDescription(),
                tarea.getEstatus(),
                tarea.getCreatedAt(),
                tarea.getUpdatedAt()
        );
    }

    public Long getId() { return id; }
    public String getTitulo() { return titulo; }
    public String getDescripcion() { return descripcion; }
    public TareaEstado getEstatus() { return estatus; }
    public LocalDateTime getCreadoEl() { return creadoEl; }
    public LocalDateTime getActualizadoEl() { return actualizadoEl; }
}