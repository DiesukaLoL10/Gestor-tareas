package com.diego.gestor_tareas.model;

import jakarta.persistence.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "tareas")
// Magia del Soft Delete:
@SQLDelete(sql = "UPDATE tareas SET activo = false WHERE id = ?")
@SQLRestriction("activo = true") 
public class Tarea {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String titulo;

    @Column(nullable = false)
    private String descripcion;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
   private TareaEstado estatus = TareaEstado.PENDING;

    // Nuevo campo: Prioridad
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TareaPrioridad prioridad = TareaPrioridad.MEDIA;

    // Nuevo campo: Borrado lógico
    @Column(nullable = false)
    private boolean activo = true;

    @Column(name = "creado_el", nullable = false, updatable = false)
    private LocalDateTime creadoEl = LocalDateTime.now();

    @Column(name = "actualizado_el", nullable = false)
    private LocalDateTime actualizadoEl = LocalDateTime.now();

    @Column(name = "fecha_inicio")
    private LocalDate fechaInicio;

    @Column(name = "fecha_terminacion")
    private LocalDate fechaTerminacion;

    public Tarea() {}

    // Getters
    public Long getId() { return id; }
    public String getTitulo() { return titulo; }
    public String getDescripcion() { return descripcion; }
    public TareaEstado getEstatus() { return estatus; }
    public TareaPrioridad getPrioridad() { return prioridad; }
    public boolean isActivo() { return activo; }
    public LocalDateTime getCreadoEl() { return creadoEl; }
    public LocalDateTime getActualizadoEl() { return actualizadoEl; }
    public LocalDate getFechaInicio() { return fechaInicio; }
    public LocalDate getFechaTerminacion() { return fechaTerminacion; }

    // Setters
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public void setFechaInicio(LocalDate fechaInicio) { this.fechaInicio = fechaInicio; }
    public void setPrioridad(TareaPrioridad prioridad) { this.prioridad = prioridad; }
    public void setActivo(boolean activo) { this.activo = activo; }

    @PreUpdate
    public void preUpdate() {
        this.actualizadoEl = LocalDateTime.now();
    }

    public void setEstatus(TareaEstado nuevoEstado) {
        this.estatus = nuevoEstado;
        if (nuevoEstado == TareaEstado.COMPLETED) {
            this.fechaTerminacion = LocalDate.now();
        } else {
            this.fechaTerminacion = null;
        }
    }
}