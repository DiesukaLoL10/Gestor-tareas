package com.diego.gestor_tareas.repository;

import com.diego.gestor_tareas.model.Tarea;
import com.diego.gestor_tareas.model.TareaEstado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import com.diego.gestor_tareas.dto.PeticionConteoEstado;



public interface TareasRepositorio extends JpaRepository<Tarea, Long>{
    
    List<Tarea> findByEstatus(TareaEstado estatus);

    public long countByEstatus(TareaEstado estatus);

    @Query("SELECT new com.diego.gestor_tareas.dto.PeticionConteoEstado(t.estatus, COUNT(t)) FROM Tarea t GROUP BY t.estatus")
    List<PeticionConteoEstado> contarTareasPorEstado();
    
}
