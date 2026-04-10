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
        empleado.put("id", UUID.randomUUID().toString());
        empleados.add(empleado);
        return empleado;
    }

    @PutMapping("/{id}")
    public Map<String, String> actualizar(@PathVariable String id, @RequestBody Map<String, String> nuevo) {
        for (Map<String, String> emp : empleados) {
            if (emp.get("id").equals(id)) {
                emp.putAll(nuevo);
                return emp;
            }
        }
        return null;
    }

    @DeleteMapping("/{id}")
    public String eliminar(@PathVariable String id) {
        empleados.removeIf(emp -> emp.get("id").equals(id));
        return "Empleado eliminado";
    }
}