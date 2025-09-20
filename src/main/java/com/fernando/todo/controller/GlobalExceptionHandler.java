package com.fernando.todo.controller;

import com.fernando.todo.exception.RecursoNoEncontradoException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String,Object>>
    handleValidationErrors(MethodArgumentNotValidException ex){
        List<String> errores = ex.getBindingResult()
                .getAllErrors()
                .stream()
                .map(error -> {
                    String campo = error instanceof FieldError ?
                            ((FieldError) error).getField() : error.getObjectName();
                    String mensaje = error.getDefaultMessage();
                    return  campo + ": " + mensaje;
                }).collect(Collectors.toList());
        Map<String, Object> body = new HashMap<>();
        body.put("error", "Validación Fallida");
        body.put("detalles", errores);

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RecursoNoEncontradoException.class)
    public ResponseEntity<Map<String, Object>> handleNotFound(RecursoNoEncontradoException ex) {
        Map<String, Object> body = new HashMap<>();
        body.put("error", "Recurso no encontrado");
        body.put("detalles", List.of(ex.getMessage()));
        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }
}
