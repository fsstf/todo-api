package com.fernando.todo.service;

import com.fernando.todo.exception.RecursoNoEncontradoException;
import com.fernando.todo.model.Estado;
import com.fernando.todo.model.Tarea;
import com.fernando.todo.repository.TareaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.assertThatThrownBy;


class TareaServiceTest {

    @Mock
    private TareaRepository tareaRepository;

    @InjectMocks
    private TareaService tareaService;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void guardarTarea_deberiaGuardarYRetornar() {
        Tarea tarea = new Tarea();
        tarea.setTitulo("test tarea");
        tarea.setDescripcion("probando guardar");
        tarea.setEstado(Estado.PENDIENTE);
        tarea.setFechaLimite(LocalDateTime.now().plusDays(1));

        when(tareaRepository.save(tarea)).thenReturn(tarea);

        Tarea resultado = tareaService.guardar(tarea);

        assertThat(resultado).isNotNull();
        assertThat(resultado.getTitulo()).isEqualTo("test tarea");
        verify(tareaRepository, times(1)).save(tarea);
    }

    @Test
    void buscarPorIdOrThrow_cuandoExiste() {
        Tarea t = new Tarea();
        t.setId(1L);
        t.setTitulo("Existe");

        when(tareaRepository.findById(1L)).thenReturn(Optional.of(t));

        Tarea res = tareaService.buscarPorIdOrThrow(1L);

        assertThat(res).isNotNull();
        assertThat(res.getTitulo()).isEqualTo("Existe");
        verify(tareaRepository).findById(1L);
    }

    @Test
    void buscarPorIdOrThrow_cuandoNoExiste_deberiaLanzarExcepcion() {
        when(tareaRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> tareaService.buscarPorIdOrThrow(99L))
                .isInstanceOf(RecursoNoEncontradoException.class)
                .hasMessageContaining("tarea con id: 99 no encontrada");

        verify(tareaRepository).findById(99L);
    }
}
