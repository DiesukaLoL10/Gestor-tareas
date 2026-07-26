package com.diego.gestor_tareas.dto;

import com.diego.gestor_tareas.model.TareaEstado;
import jakarta.validation.constraints.NotNull;

public class PeticionActualizarEstado {

    @NotNull(message = "El nuevo estado es obligatorio")
    private TareaEstado estatus;

    protected PeticionActualizarEstado() {}

    public PeticionActualizarEstado(TareaEstado estatus) {
        this.estatus = estatus;
    }

    public TareaEstado getEstatus() { return estatus; }
    public void setStatus(TareaEstado estatus) { this.estatus = estatus; }
}