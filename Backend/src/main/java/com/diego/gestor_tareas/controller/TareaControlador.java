package com.diego.gestor_tareas.controller;

import com.diego.gestor_tareas.dto.PeticionCrearTarea;
import com.diego.gestor_tareas.dto.RespuestaTarea;
import com.diego.gestor_tareas.dto.PeticionActualizarEstado;
import com.diego.gestor_tareas.model.Tarea;
import com.diego.gestor_tareas.model.TareaEstado;
import com.diego.gestor_tareas.service.TareaServicio;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tareas")
public class TareaControlador {

    private final TareaServicio tareaServicio;

    public TareaControlador(TareaServicio tareaServicio) {
        this.tareaServicio = tareaServicio;
    }

    // POST /api/tareas
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RespuestaTarea crearTarea(@Valid @RequestBody PeticionCrearTarea request) {
        Tarea tarea = tareaServicio.crearTarea(request.getTitulo(), request.getDescripcion());
        return RespuestaTarea.fromEntity(tarea);
    }

    // GET /api/tareas
    // GET /api/tareas?estatus=PENDING
    @GetMapping
    public List<RespuestaTarea> getTodasTareas(@RequestParam(required = false) TareaEstado estatus) {
        List<Tarea> tareas = (estatus != null)
                ? tareaServicio.getTareaByEstatus(estatus)
                : tareaServicio.getTodasTareas();

        return tareas.stream()
                .map(RespuestaTarea::fromEntity)
                .toList();
    }

    // GET /api/tareas/(numero ID)
    @GetMapping("/{id}")
    public RespuestaTarea getTareaById(@PathVariable Long id) {
        Tarea tarea = tareaServicio.getTareaById(id);
        return RespuestaTarea.fromEntity(tarea);
    }

    // PATCH /api/tareas/(numero ID/estatus
    @PatchMapping("/{id}/estatus")
    public RespuestaTarea actualizarEstatus(@PathVariable Long id, @Valid @RequestBody PeticionActualizarEstado request) {
        Tarea tarea = tareaServicio.actualizarEstatus(id, request.getEstatus());
        return RespuestaTarea.fromEntity(tarea);
    }

    // DELETE /api/tareas/(numero ID)
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTask(@PathVariable Long id) {
        tareaServicio.borrarTarea(id);
    }
}