package com.fernando.todo.controller;

import com.fernando.todo.model.Tarea;
import com.fernando.todo.service.TareaService;
import jakarta.validation.Valid;
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
        Tarea tarea = tareaService.buscarPorIdOrThrow(id);
        return ResponseEntity.ok(tarea);
    }

    @PostMapping
    public Tarea crearTarea(@RequestBody @Valid Tarea tarea) {
        return tareaService.guardarTarea(tarea);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Tarea> atualizarPorId(@PathVariable Long id, @RequestBody @Valid Tarea tarea) {
        Tarea existente = tareaService.buscarPorIdOrThrow(id);
        existente.setTitulo(tarea.getTitulo());
        existente.setDescripcion(tarea.getDescripcion());
        existente.setEstado(tarea.getEstado());
        existente.setFechaLimite(tarea.getFechaLimite());
        Tarea actualizada = tareaService.guardarTarea(existente);

        return ResponseEntity.ok(actualizada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarPorId(@PathVariable Long id) {
        tareaService.eliminarPorId(id);
        return ResponseEntity.noContent().<Void>build();
    }
}
