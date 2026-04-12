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

import com.vas.model.Empleado;
import com.vas.service.EmpleadoService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/empleados")
public class EmpleadoController {

    private final EmpleadoService empleadoService;

    public EmpleadoController(EmpleadoService empleadoService) {
        this.empleadoService = empleadoService;
    }

    @GetMapping("/listado")
    public String inicio(Model model) {
        var empleados = empleadoService.getAllEmpleados();
        model.addAttribute("empleados", empleados);
        model.addAttribute("totalEmpleados", empleados.size());
        return "/empleados/listado";
    }

    @PostMapping("/guardar")
    public String guardar(@Valid Empleado empleado,
                          BindingResult bindingResult,
                          RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            String errores = bindingResult.getFieldErrors().stream()
                    .map(error -> error.getDefaultMessage())
                    .distinct()
                    .collect(Collectors.joining(" | "));

            redirectAttributes.addFlashAttribute("todoOK", "error");
            redirectAttributes.addFlashAttribute("message", errores);

            if (empleado.getIdEmpleado() != null && !empleado.getIdEmpleado().isEmpty()) {
                return "redirect:/empleados/modificar/" + empleado.getIdEmpleado();
            }
            return "redirect:/empleados/listado";
        }

        try {
            empleadoService.save(empleado);
            redirectAttributes.addFlashAttribute("todoOK", "todoOk");
            redirectAttributes.addFlashAttribute("message", "Empleado guardado correctamente");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("todoOK", "error");
            redirectAttributes.addFlashAttribute("message", e.getMessage());
        }

        return "redirect:/empleados/listado";
    }

    @PostMapping("/eliminar")
    public String eliminar(@RequestParam String email, RedirectAttributes redirectAttributes) {
        try {
            empleadoService.deleteByEmail(email);
            redirectAttributes.addFlashAttribute("todoOK", "todoOk");
            redirectAttributes.addFlashAttribute("message", "Empleado eliminado correctamente");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("todoOK", "error");
            redirectAttributes.addFlashAttribute("message", e.getMessage());
        }

        return "redirect:/empleados/listado";
    }

    @PostMapping("/buscar")
    public String buscar(@RequestParam String email,
                         RedirectAttributes redirectAttributes,
                         Model model) {

        Optional<Empleado> empleado = empleadoService.findByEmail(email);

        if (empleado.isPresent()) {
            model.addAttribute("empleado", empleado.get());
            return "/empleados/detalle";
        }

        redirectAttributes.addFlashAttribute("todoOK", "error");
        redirectAttributes.addFlashAttribute("message", "No se encontró un empleado con ese email");
        return "redirect:/empleados/listado";
    }

    @GetMapping("/modificar/{idEmpleado}")
    public String modificar(@PathVariable String idEmpleado,
                            RedirectAttributes redirectAttributes,
                            Model model) {

        Optional<Empleado> empleado = empleadoService.findById(idEmpleado);

        if (empleado.isEmpty()) {
            redirectAttributes.addFlashAttribute("todoOK", "error");
            redirectAttributes.addFlashAttribute("message", "Empleado no encontrado");
            return "redirect:/empleados/listado";
        }

        model.addAttribute("empleado", empleado.get());
        return "/empleados/modifica";
    }
}