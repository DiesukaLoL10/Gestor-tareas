package com.diego.gestor_tareas.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class PeticionCrearTarea {
    
    @NotBlank(message = "El titulo es obligatorio")
    @Size(max = 255,message = "El titulo no puede sobrepasar los 255 caracteres")
    private String titulo;

    @Size(max = 500, message = "La descripción no puede superar 500 caracteres")
    private String descripcion;

    protected PeticionCrearTarea() {}

    public PeticionCrearTarea(String titulo, String descripcion) {
        this.titulo = titulo;
        this.descripcion = descripcion;
    }

    public String getTitulo() { return titulo; }
    public String getDescripcion() { return descripcion; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

}
