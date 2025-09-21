package com.fernando.todo.service;

import com.fernando.todo.dto.TareaDTO;
import com.fernando.todo.exception.RecursoNoEncontradoException;
import com.fernando.todo.model.Tarea;
import com.fernando.todo.repository.TareaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class TareaService {
    private final TareaRepository tareaRepository;

    public TareaService(TareaRepository tareaRepository) {
        this.tareaRepository = tareaRepository;
    }

    public List<Tarea> listarTareas() {
        return tareaRepository.findAll();
    }

    public Tarea buscarPorIdOrThrow(Long id) {
        return tareaRepository.findById(id)
                .orElseThrow(()-> new RecursoNoEncontradoException("Tarea con id: " + id + " no encontrada"));
    }

    public Tarea guardarTarea(Tarea tarea) {
        return tareaRepository.save(tarea);
    }

    public void eliminarPorId(Long id) {
        Tarea existente = buscarPorIdOrThrow(id);
        tareaRepository.delete(existente);
    }

    public Tarea toEntity(TareaDTO dto){
        return Tarea.builder()
                .titulo(dto.titulo())
                .descripcion(dto.descripcion())
                .estado(dto.estado())
                .fechaLimite(dto.fechaLimite())
                .build();
    }

    public TareaDTO toDTO(Tarea tarea){
        return new TareaDTO(
                tarea.getTitulo(),
                tarea.getDescripcion(),
                tarea.getEstado(),
                tarea.getFechaLimite()
        );
    }


}
