package com.diego.gestor_tareas.exception;

import java.time.LocalDateTime;
import java.util.List;

public class RespuestaError {

    private final LocalDateTime tiempo;
    private final int estatus;
    private final String error;
    private final String mensaje;
    private final List<String> detalles; // para errores de validación con varios campos

    public RespuestaError(int status, String error, String message, List<String> details) {
        this.tiempo = LocalDateTime.now();
        this.estatus = status;
        this.error = error;
        this.mensaje = message;
        this.detalles = details;
    }

    public LocalDateTime getTimestamp() { return tiempo; }
    public int getEstatus() { return estatus; }
    public String getError() { return error; }
    public String getMensaje() { return mensaje; }
    public List<String> getDetalles() { return detalles; }
}