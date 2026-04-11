package com.vas.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.vas.model.Tarea;
import com.vas.service.TareaService;

import java.util.List;

@RestController
@RequestMapping("/tareas")
public class TareaController {

    @Autowired
    private TareaService service;

    @GetMapping
    public List<Tarea> listar() {
        return service.listar();
    }

    @PostMapping
    public Tarea guardar(@RequestBody Tarea t) {
        return service.guardar(t);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable String id) {
        service.eliminar(id);
    }
}