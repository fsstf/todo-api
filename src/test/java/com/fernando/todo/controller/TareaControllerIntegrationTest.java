package com.fernando.todo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fernando.todo.model.Estado;
import com.fernando.todo.model.Tarea;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;



import java.time.LocalDateTime;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class TareaControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void deberiaCrearYObtenerTarea() throws Exception {

        Tarea tarea = new Tarea();
        tarea.setTitulo("Tarea de integración");
        tarea.setDescripcion("Probando integración");
        tarea.setEstado(Estado.PENDIENTE);
        tarea.setFechaLimite(LocalDateTime.now().plusDays(2));

        mockMvc.perform(post("/api/tareas")
                        .with(httpBasic("admin", "admin"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(tarea)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.titulo").value("Tarea de integración"));

        mockMvc.perform(get("/api/tareas")
                        .with(httpBasic("fernando", "2126")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].titulo").value("Tarea de integración"));

    }
}
