package com.diego.gestor_tareas.dto;

import com.diego.gestor_tareas.model.TareaEstado;

public class PeticionConteoEstado {

    private TareaEstado estatus;
    private long count;

    protected PeticionConteoEstado() {}

    public PeticionConteoEstado(TareaEstado estatus, long count) {
        this.estatus = estatus;
        this.count = count;
    }


    public long getCount() { return count; }
    public TareaEstado getEstatus() { return estatus; }


    
}
