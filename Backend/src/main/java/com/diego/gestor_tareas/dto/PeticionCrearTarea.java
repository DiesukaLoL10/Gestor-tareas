package com.diego.gestor_tareas.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import com.diego.gestor_tareas.model.TareaPrioridad;
import java.time.LocalDate;

public class PeticionCrearTarea {
    
    @NotBlank(message = "El titulo es obligatorio")
    @Size(max = 255, message = "El titulo no puede sobrepasar los 255 caracteres")
    private String titulo;

    @Size(max = 500, message = "La descripción no puede superar 500 caracteres")
    private String descripcion;

    @NotNull(message = "La fecha de inicio es obligatoria")
    private LocalDate fechaInicio;

    @NotNull(message = "La prioridad es obligatoria")
    private TareaPrioridad prioridad;

    protected PeticionCrearTarea() {}

    public PeticionCrearTarea(String titulo, String descripcion, LocalDate fechaInicio, TareaPrioridad prioridad) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.fechaInicio = fechaInicio;
        this.prioridad = prioridad;
    }

    public String getTitulo() { return titulo; }
    public String getDescripcion() { return descripcion; }
    public LocalDate getFechaInicio() { return fechaInicio; }
    public TareaPrioridad getPrioridad() { return prioridad; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public void setFechaInicio(LocalDate fechaInicio) { this.fechaInicio = fechaInicio; }

}