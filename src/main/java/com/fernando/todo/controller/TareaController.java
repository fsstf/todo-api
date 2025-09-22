package com.fernando.todo.controller;

import com.fernando.todo.dto.TareaDTO;
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
    public List<Tarea> listar() {
        return tareaService.listar();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Tarea> buscarPorId(@PathVariable Long id) {
        Tarea tarea = tareaService.buscarPorIdOrThrow(id);
        return ResponseEntity.ok(tarea);
    }

    @PostMapping
    public ResponseEntity<Tarea> crear(@RequestBody @Valid TareaDTO dto) {
        Tarea tarea = tareaService.toEntity(dto);
        Tarea guardada = tareaService.guardar(tarea);
        return ResponseEntity.ok(guardada);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Tarea> atualizar(@PathVariable Long id, @RequestBody @Valid TareaDTO dto) {
        Tarea existente = tareaService.buscarPorIdOrThrow(id);
        existente.setTitulo(dto.titulo());
        existente.setDescripcion(dto.descripcion());
        existente.setEstado(dto.estado());
        existente.setFechaLimite(dto.fechaLimite());

        Tarea actualizada = tareaService.guardar(existente);
        return ResponseEntity.ok(actualizada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        tareaService.eliminarPorId(id);
        return ResponseEntity.noContent().<Void>build();
    }
}
