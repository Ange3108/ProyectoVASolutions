package com.vas.controller;

import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.vas.model.Tarea;
import com.vas.service.TareaService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/tareas")
public class TareaController {

    private final TareaService tareaService;

    public TareaController(TareaService tareaService) {
        this.tareaService = tareaService;
    }

    @GetMapping("/listado")
    public String inicio(Model model) {
        var tareas = tareaService.getAllTareas();
        model.addAttribute("tareas", tareas);
        model.addAttribute("totalTareas", tareas.size());
        return "/tareas/listado";
    }

    @PostMapping("/guardar")
    public String guardar(@Valid Tarea tarea,
                          BindingResult bindingResult,
                          RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            String errores = bindingResult.getFieldErrors().stream()
                    .map(error -> error.getDefaultMessage())
                    .distinct()
                    .collect(Collectors.joining(" | "));

            redirectAttributes.addFlashAttribute("todoOK", "error");
            redirectAttributes.addFlashAttribute("message", errores);

            if (tarea.getIdTarea() != null && !tarea.getIdTarea().isEmpty()) {
                return "redirect:/tareas/modificar/" + tarea.getIdTarea();
            }
            return "redirect:/tareas/listado";
        }

        try {
            tareaService.save(tarea);
            redirectAttributes.addFlashAttribute("todoOK", "todoOk");
            redirectAttributes.addFlashAttribute("message", "Tarea guardada correctamente");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("todoOK", "error");
            redirectAttributes.addFlashAttribute("message", e.getMessage());
        }

        return "redirect:/tareas/listado";
    }

    @PostMapping("/eliminar")
    public String eliminar(@RequestParam String titulo, RedirectAttributes redirectAttributes) {
        try {
            tareaService.deleteByTitulo(titulo);
            redirectAttributes.addFlashAttribute("todoOK", "todoOk");
            redirectAttributes.addFlashAttribute("message", "Tarea eliminada correctamente");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("todoOK", "error");
            redirectAttributes.addFlashAttribute("message", e.getMessage());
        }

        return "redirect:/tareas/listado";
    }

    @PostMapping("/buscar")
    public String buscar(@RequestParam String titulo,
                         RedirectAttributes redirectAttributes,
                         Model model) {

        Optional<Tarea> tarea = tareaService.findByTitulo(titulo);

        if (tarea.isPresent()) {
            model.addAttribute("tarea", tarea.get());
            return "/tareas/detalle";
        }

        redirectAttributes.addFlashAttribute("todoOK", "error");
        redirectAttributes.addFlashAttribute("message", "No se encontró una tarea con ese título");
        return "redirect:/tareas/listado";
    }

    @GetMapping("/modificar/{idTarea}")
    public String modificar(@PathVariable String idTarea,
                            RedirectAttributes redirectAttributes,
                            Model model) {

        Optional<Tarea> tarea = tareaService.findById(idTarea);

        if (tarea.isEmpty()) {
            redirectAttributes.addFlashAttribute("todoOK", "error");
            redirectAttributes.addFlashAttribute("message", "Tarea no encontrada");
            return "redirect:/tareas/listado";
        }

        model.addAttribute("tarea", tarea.get());
        return "/tareas/modifica";
    }
}