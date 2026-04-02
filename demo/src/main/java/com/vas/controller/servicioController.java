package com.vas.controller;

import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.vas.model.Servicio;
import com.vas.service.servicioService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/servicios")
public class servicioController {
    @Autowired
    private servicioService servicioService;

    @GetMapping("/listado")
    public String inicio(Model model) {
        var servicios = servicioService.getAllServicios();
        model.addAttribute("servicio", servicios);
        model.addAttribute("totalServicios", servicios.size());
        return "/servicios/listado";
    }

    @PostMapping("/guardar")
    public String guardar(@Valid Servicio servicio, BindingResult bindingResult,
            RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            String errores = bindingResult.getFieldErrors().stream()
                    .map(error -> error.getDefaultMessage())
                    .distinct()
                    .collect(Collectors.joining(" | "));
            redirectAttributes.addFlashAttribute("todoOK", "error");
            redirectAttributes.addFlashAttribute("message", errores);

            if (servicio.getIdServicio() != null && !servicio.getIdServicio().isEmpty()) {
                
                return "/servicios/modificar" + servicio.getIdServicio();
            }
            return "redirect:/servicios/listado";
        }

        try {
            servicioService.save(servicio);
            redirectAttributes.addFlashAttribute("todoOK", "todoOk");
            redirectAttributes.addFlashAttribute("message", "Servicio guardado exitosamente");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("todoOK", "error");
            redirectAttributes.addFlashAttribute("message", "Error inesperado al guardar el servicio");
        }

        return "redirect:/servicios/listado";   
    }

    @PostMapping("/eliminar")
    public String eliminar(@RequestParam String nombreServicio, RedirectAttributes redirectAttributes){
        try {
            servicioService.deleteByNombreServicio(nombreServicio);
            redirectAttributes.addFlashAttribute("todoOK", "todoOk");
            redirectAttributes.addFlashAttribute("message", "Servicio eliminado exitosamente");
        } catch (IllegalArgumentException | IllegalStateException e) {
            redirectAttributes.addFlashAttribute("todoOK", "error");
            redirectAttributes.addFlashAttribute("message", e.getMessage());
        }
        return "redirect:/servicios/listado";
    }

    @PostMapping("/buscar")
    public String buscar(@RequestParam String nombreServicio, RedirectAttributes redirectAttributes){
        try {
            Optional<Servicio> servicio = servicioService.findByNombreServicio(nombreServicio);
            if(servicio.isPresent()){
                redirectAttributes.addFlashAttribute("servicio", servicio.get());
                return "redirect:/servicios/detalle";
            } else {
                redirectAttributes.addFlashAttribute("todoOK", "error");
                redirectAttributes.addFlashAttribute("message", "No se encontró un servicio con el nombre: " + nombreServicio);
                return "redirect:/servicios/listado";
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("todoOK", "error");
            redirectAttributes.addFlashAttribute("message", "Error inesperado al buscar el servicio");
            return "redirect:/servicios/listado";
        }
    }

    @GetMapping("/modificar/{nombreServicio}")
    public String modificar(@PathVariable ("nombreServicio") String nombreServicio, 
    RedirectAttributes redirectAttributes, Model model) {
       
     Optional<Servicio> servicioOpt = servicioService.findByNombreServicio(nombreServicio);
            if(servicioOpt.isEmpty()){
            redirectAttributes.addFlashAttribute("error", 
            "No se encontró un servicio con el nombre: "+nombreServicio);
            return "redirect:/servicios/listado";
        }
        Servicio servicio = servicioOpt.get();
        model.addAttribute("servicio", servicio);
        return "/servicio/modifica";

    }
}
