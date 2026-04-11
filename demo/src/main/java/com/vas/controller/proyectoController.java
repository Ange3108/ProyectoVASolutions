package com.vas.controller;

import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.vas.model.Proyecto;
import com.vas.service.proyectoService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/proyectos")
public class proyectoController {

    private final proyectoService proyectoService;

    public proyectoController(proyectoService proyectoService) {
        this.proyectoService = proyectoService;
    }

    @GetMapping("/listado")
    public String inicio(Model model) {
        var proyectos = proyectoService.getAllProyectos();
        model.addAttribute("proyecto", proyectos);
        model.addAttribute("totalProyectos", proyectos.size());
        return "/proyectos/listado";
    }

    @PostMapping("/guardar")
    public String guardar(@Valid Proyecto proyecto, BindingResult result, Model model) {

        if (result.hasErrors()) {
            String errores = result.getFieldErrors().stream()
                    .map(e -> e.getDefaultMessage())
                    .collect(Collectors.joining(" | "));
            model.addAttribute("error", errores);
            return "/proyectos/listado";
        }

        proyectoService.save(proyecto);
        return "redirect:/proyectos/listado";
    }

    @PostMapping("/eliminar")
    public String eliminar(@RequestParam String nombreProyecto) {
        proyectoService.deleteByNombreProyecto(nombreProyecto);
        return "redirect:/proyectos/listado";
    }

    @PostMapping("/buscar")
    public String buscar(@RequestParam String nombreProyecto, Model model) {
        Optional<Proyecto> proyecto = proyectoService.findByNombreProyecto(nombreProyecto);
        proyecto.ifPresent(p -> model.addAttribute("proyecto", p));
        return "proyectos/detalle";
    }
}