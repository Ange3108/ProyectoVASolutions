package com.vas.controller;

import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/tareas")
public class TareaController {

    private List<Map<String, String>> tareas = new ArrayList<>();

    @GetMapping
    public List<Map<String, String>> listar() {
        return tareas;
    }

    @PostMapping
    public Map<String, String> agregar(@RequestBody Map<String, String> tarea) {
        tareas.add(tarea);
        return tarea;
    }
}