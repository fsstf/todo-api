package com.fernando.todo.service;

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

    public Optional<Tarea> buscarPorId(Long id) {
        return tareaRepository.findById(id);
    }

    public Tarea guardarTarea(Tarea tarea) {
        return tareaRepository.save(tarea);
    }

    public void eliminarPorId(Long id) {
        tareaRepository.deleteById(id);
    }


}
