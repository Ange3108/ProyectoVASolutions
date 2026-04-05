package com.vas.controller;

import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/empleados")
public class EmpleadoController {

    private List<Map<String, String>> empleados = new ArrayList<>();

    @GetMapping
    public List<Map<String, String>> listar() {
        return empleados;
    }

    @PostMapping
    public Map<String, String> agregar(@RequestBody Map<String, String> empleado) {
        empleados.add(empleado);
        return empleado;
    }
}