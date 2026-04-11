package com.vas.controller;

import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.vas.model.CampanaRedes;
import com.vas.service.campanaService;

@Controller
@RequestMapping("/campanas")
public class campanaController {

    private final campanaService campanaService;

    public campanaController(campanaService campanaService) {
        this.campanaService = campanaService;
    }

    @GetMapping("/listado")
    public String inicio(Model model) {
        var lista = campanaService.getAll();
        model.addAttribute("campanas", lista);
        return "/campanas/listado";
    }

    @PostMapping("/guardar")
    public String guardar(CampanaRedes campana) {
        campanaService.save(campana);
        return "redirect:/campanas/listado";
    }

    @PostMapping("/eliminar")
    public String eliminar(@RequestParam String tipoCampana) {
        campanaService.deleteByTipoCampana(tipoCampana);
        return "redirect:/campanas/listado";
    }

    @PostMapping("/buscar")
    public String buscar(@RequestParam String tipoCampana, Model model) {
        Optional<CampanaRedes> campana = campanaService.findByTipoCampana(tipoCampana);
        campana.ifPresent(c -> model.addAttribute("campana", c));
        return "campanas/detalle";
    }
}