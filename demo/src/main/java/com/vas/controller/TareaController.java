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
        tarea.put("id", UUID.randomUUID().toString());
        tareas.add(tarea);
        return tarea;
    }

    @PutMapping("/{id}")
    public Map<String, String> actualizar(@PathVariable String id, @RequestBody Map<String, String> nuevo) {
        for (Map<String, String> t : tareas) {
            if (t.get("id").equals(id)) {
                t.putAll(nuevo);
                return t;
            }
        }
        return null;
    }

    @DeleteMapping("/{id}")
    public String eliminar(@PathVariable String id) {
        tareas.removeIf(t -> t.get("id").equals(id));
        return "Tarea eliminada";
    }
}