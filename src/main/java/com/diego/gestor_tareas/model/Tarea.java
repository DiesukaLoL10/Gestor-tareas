package com.diego.gestor_tareas.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;


@Entity
@Table(name = "tareas")
public class Tarea {
    //Tabla
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String titulo;


    @Column(nullable = false)
    private String descripcion;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TareaEstado estatus;

    @Column(name = "creado_el", nullable = false, updatable = false)
    private LocalDateTime creadoEl;

    @Column(name = "actualizado_el", nullable = false)
    private LocalDateTime actualizadoEl;

    //Constructor vacio por JPA
    protected Tarea() {}

    public Tarea(String titulo, String descripcion) {
    
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.estatus = TareaEstado.PENDING; // Siempre incia en PENDIENTE
        this.creadoEl = LocalDateTime.now();
        this.actualizadoEl = LocalDateTime.now();
    
    }

    // Getters 
    public Long getId() { return id; }
    public String getTitle() { return titulo; }
    public String getDescription() { return descripcion; }
    public TareaEstado getEstatus() { return estatus; }
    public LocalDateTime getCreatedAt() { return creadoEl; }
    public LocalDateTime getUpdatedAt() { return actualizadoEl; }

    //Setters
    public void setTitle(String titulo) { this.titulo = titulo; }
    public void setDescription(String descripcion) { this.descripcion = descripcion; }


    
    public void setStatus(TareaEstado estatus) {
        this.estatus = estatus;
        this.actualizadoEl = LocalDateTime.now();
    }

}
