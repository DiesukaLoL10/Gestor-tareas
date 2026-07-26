package com.diego.gestor_tareas.repository;

import com.diego.gestor_tareas.model.Tarea;
import com.diego.gestor_tareas.model.TareaEstado;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;



public interface TareasRepositorio extends JpaRepository<Tarea, Long>{
    
    List<Tarea> findByEstatus(TareaEstado estatus);
    
}
