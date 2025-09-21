package com.fernando.todo.dto;

import com.fernando.todo.model.Estado;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

public record TareaDTO (
        @NotBlank(message = "el titulo no puede estar vacio")
        @Size(max = 100, message = ("el titulo no puede superar los 100 caracteres"))
        String titulo,

        @Size(max = 150,message = "la descripcion no puede superar los 150 caracteres")
        String descripcion,

        @NotNull(message = "el estado es obligatorio")
        Estado estado,

        @FutureOrPresent(message = "La fecha limite no puede ser del pasado")
        LocalDateTime fechaLimite
){}
