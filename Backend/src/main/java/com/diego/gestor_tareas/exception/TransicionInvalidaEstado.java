package com.diego.gestor_tareas.exception;

import com.diego.gestor_tareas.model.TareaEstado;

public class TransicionInvalidaEstado extends RuntimeException{

    public TransicionInvalidaEstado(TareaEstado desde, TareaEstado hacia) {
        super("No se puede cambiar desde " + desde + "hacia" + hacia);
    }
    
}
