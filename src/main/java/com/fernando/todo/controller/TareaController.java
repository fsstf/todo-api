package com.fernando.todo.controller;

import com.fernando.todo.model.Tarea;
import com.fernando.todo.service.TareaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tareas")
public class TareaController {
    private final TareaService tareaService;

    public TareaController(TareaService tareaService) {
        this.tareaService = tareaService;
    }

    @GetMapping
    public List<Tarea> listarTareas() {
        return tareaService.listarTareas();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Tarea> buscarPorId(@PathVariable Long id) {
        return tareaService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Tarea crearTarea(@RequestBody Tarea tarea) {
        return tareaService.guardarTarea(tarea);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Tarea> atualizarPorId(@PathVariable Long id, @RequestBody Tarea tarea) {
        return tareaService.buscarPorId(id)
                .map(existente -> {
                    existente.setTitulo(tarea.getTitulo());
                    existente.setDescripcion(tarea.getDescripcion());
                    existente.setEstado(tarea.getEstado());
                    existente.setFechaLimite(tarea.getFechaLimite());
                    return ResponseEntity.ok(tareaService.guardarTarea(existente));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarPorId(@PathVariable Long id) {
        return tareaService.buscarPorId(id)
                .map(existente -> {
                    tareaService.eliminarPorId(id);
                    return ResponseEntity.noContent().<Void>build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
