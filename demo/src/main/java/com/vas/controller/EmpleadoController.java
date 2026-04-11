package com.vas.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.vas.model.Empleado;
import com.vas.service.EmpleadoService;

import java.util.List;

@RestController
@RequestMapping("/empleados")
public class EmpleadoController {

    @Autowired
    private EmpleadoService service;

    @GetMapping
    public List<Empleado> listar() {
        return service.listar();
    }

    @PostMapping
    public Empleado guardar(@RequestBody Empleado e) {
        return service.guardar(e);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable String id) {
        service.eliminar(id);
    }
}