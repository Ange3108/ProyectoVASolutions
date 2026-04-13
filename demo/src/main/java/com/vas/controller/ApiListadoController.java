package com.vas.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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
public class ApiListadoController {

    private final clienteService clienteService;
    private final servicioService servicioService;
    private final EmpleadoService empleadoService;
    private final proyectoService proyectoService;
    private final campanaService campanaService;
    private final TareaService tareaService;

    public ApiListadoController(
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

    @GetMapping("/clientes/listado")
    public List<Cliente> listadoClientes() {
        return clienteService.getAllClientes();
    }

    @GetMapping("/servicios/listado")
    public List<Servicio> listadoServicios() {
        return servicioService.getAllServicios();
    }

    @GetMapping("/empleados/listado")
    public List<Empleado> listadoEmpleados() {
        return empleadoService.getAllEmpleados();
    }

    @GetMapping("/proyectos/listado")
    public List<Proyecto> listadoProyectos() {
        return proyectoService.getAllProyectos();
    }

    @GetMapping("/campanas/listado")
    public List<CampanaRedes> listadoCampanas() {
        return campanaService.getAll();
    }

    @GetMapping("/tareas/listado")
    public List<Tarea> listadoTareas() {
        return tareaService.getAllTareas();
    }
}