package com.vas.controller;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.vas.model.CampanaRedes;
import com.vas.model.Cliente;
import com.vas.model.Empleado;
import com.vas.model.Proyecto;
import com.vas.model.Servicio;
import com.vas.model.Tarea;
import com.vas.service.EmpleadoService;
import com.vas.service.TareaService;
import com.vas.service.campanaService;
import com.vas.service.clienteService;
import com.vas.service.proyectoService;
import com.vas.service.servicioService;

@RestController
@RequestMapping("/api")
public class ApiCrudController {

    private final clienteService clienteService;
    private final servicioService servicioService;
    private final EmpleadoService empleadoService;
    private final proyectoService proyectoService;
    private final campanaService campanaService;
    private final TareaService tareaService;

    public ApiCrudController(
            clienteService clienteService,
            servicioService servicioService,
            EmpleadoService empleadoService,
            proyectoService proyectoService,
            campanaService campanaService,
            TareaService tareaService) {
        this.clienteService = clienteService;
        this.servicioService = servicioService;
        this.empleadoService = empleadoService;
        this.proyectoService = proyectoService;
        this.campanaService = campanaService;
        this.tareaService = tareaService;
    }

    @PostMapping("/clientes/guardar")
    public ResponseEntity<?> guardarCliente(Cliente cliente) {
        try {
            clienteService.save(cliente);
            return ResponseEntity.ok(Map.of("message", "Cliente guardado"));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping("/clientes/eliminar")
    public ResponseEntity<?> eliminarCliente(@RequestParam String nombreEmpresa) {
        try {
            clienteService.deleteByNombreEmpresa(nombreEmpresa);
            return ResponseEntity.ok(Map.of("message", "Cliente eliminado"));
        } catch (IllegalArgumentException | IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping("/servicios/guardar")
    public ResponseEntity<?> guardarServicio(Servicio servicio) {
        try {
            servicioService.save(servicio);
            return ResponseEntity.ok(Map.of("message", "Servicio guardado"));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping("/servicios/eliminar")
    public ResponseEntity<?> eliminarServicio(@RequestParam String nombreServicio) {
        try {
            servicioService.deleteByNombreServicio(nombreServicio);
            return ResponseEntity.ok(Map.of("message", "Servicio eliminado"));
        } catch (IllegalArgumentException | IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping("/empleados/guardar")
    public ResponseEntity<?> guardarEmpleado(Empleado empleado) {
        try {
            empleadoService.save(empleado);
            return ResponseEntity.ok(Map.of("message", "Empleado guardado"));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping("/empleados/eliminar")
    public ResponseEntity<?> eliminarEmpleado(@RequestParam String email) {
        try {
            empleadoService.deleteByEmail(email);
            return ResponseEntity.ok(Map.of("message", "Empleado eliminado"));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping("/proyectos/guardar")
    public ResponseEntity<?> guardarProyecto(Proyecto proyecto) {
        try {
            proyectoService.save(proyecto);
            return ResponseEntity.ok(Map.of("message", "Proyecto guardado"));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping("/proyectos/eliminar")
    public ResponseEntity<?> eliminarProyecto(@RequestParam String nombreProyecto) {
        try {
            proyectoService.deleteByNombreProyecto(nombreProyecto);
            return ResponseEntity.ok(Map.of("message", "Proyecto eliminado"));
        } catch (IllegalArgumentException | IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping("/campanas/guardar")
    public ResponseEntity<?> guardarCampana(CampanaRedes campana) {
        try {
            campanaService.save(campana);
            return ResponseEntity.ok(Map.of("message", "Campana guardada"));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping("/campanas/eliminar")
    public ResponseEntity<?> eliminarCampana(@RequestParam String tipoCampana) {
        try {
            campanaService.deleteByTipoCampana(tipoCampana);
            return ResponseEntity.ok(Map.of("message", "Campana eliminada"));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping("/tareas/guardar")
    public ResponseEntity<?> guardarTarea(Tarea tarea) {
        try {
            tareaService.save(tarea);
            return ResponseEntity.ok(Map.of("message", "Tarea guardada"));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping("/tareas/eliminar")
    public ResponseEntity<?> eliminarTarea(@RequestParam String titulo) {
        try {
            tareaService.deleteByTitulo(titulo);
            return ResponseEntity.ok(Map.of("message", "Tarea eliminada"));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", e.getMessage()));
        }
    }
}