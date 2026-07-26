package com.diego.gestor_tareas.exception;

public class TareaNoEncontrada extends RuntimeException{
    public TareaNoEncontrada(Long id){
        super("No se encontro la tarea con el id " + id);
    }
}
