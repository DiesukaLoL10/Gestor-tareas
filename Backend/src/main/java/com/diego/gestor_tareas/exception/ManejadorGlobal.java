package com.diego.gestor_tareas.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.List;

@RestControllerAdvice
public class ManejadorGlobal {

    // 404 - cuando una tarea no existe
    @ExceptionHandler(TareaNoEncontrada.class)
    public ResponseEntity<RespuestaError> handleNotFound(TareaNoEncontrada ex) {
        RespuestaError error = new RespuestaError(
                HttpStatus.NOT_FOUND.value(),
                "No encontrado",
                ex.getMessage(),
                null
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    // 422 - cuando una transición de estado no es válida 
    @ExceptionHandler(TransicionInvalidaEstado.class)
    public ResponseEntity<RespuestaError> handleInvalidTransition(TransicionInvalidaEstado ex) {
        RespuestaError error = new RespuestaError(
                HttpStatus.UNPROCESSABLE_CONTENT.value(),
                "Entidad INPROCESABLE",
                ex.getMessage(),
                null
        );
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_CONTENT).body(error);
    }

    // 422 - cuando @Valid falla (campos inválidos en el body, ej. título vacío)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<RespuestaError> handleValidation(MethodArgumentNotValidException ex) {
        List<String> detalles = ex.getBindingResult().getFieldErrors().stream()
                .map(fieldError -> fieldError.getField() + ": " + fieldError.getDefaultMessage())
                .toList();

        RespuestaError error = new RespuestaError(
                HttpStatus.UNPROCESSABLE_CONTENT.value(),
                "Entidad INPROCESABLE",
                "Error de validación en los datos enviados",
                detalles
        );
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_CONTENT).body(error);
    }

    // 400 - cuando el tipo de un parámetro no se puede convertir (ej. ?estatus=NOSEXISTE)
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<RespuestaError> handleTypeMismatch(MethodArgumentTypeMismatchException ex) {
        String mensaje = "El valor '" + ex.getValue() + "' no es válido para el parámetro '" + ex.getName() + "'";

        RespuestaError error = new RespuestaError(
                HttpStatus.BAD_REQUEST.value(),
                "Bad Request",
                mensaje,
                null
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    // 500 - red de seguridad para cualquier cosa no anticipada
    @ExceptionHandler(Exception.class)
    public ResponseEntity<RespuestaError> handleGeneric(Exception ex) {
        RespuestaError error = new RespuestaError(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Internal Server Error",
                "Ocurrió un error inesperado",
                null
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }
}