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

import com.vas.model.Cliente;
import com.vas.service.clienteService;

import jakarta.validation.Valid;

    @Controller
    @RequestMapping("/clientes")
public class clienteController {
    @Autowired
    private clienteService clienteService;

    @GetMapping("/listado")
    public String inicio(Model model) {
        var clientes = clienteService.getAllClientes();
        model.addAttribute("cliente", clientes);
        model.addAttribute("totalClientes", clientes.size());
        return "/clientes/listado";
    }

    @PostMapping("/guardar")
    public String guardar(@Valid Cliente cliente, BindingResult bindingResult,
            RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            String errores = bindingResult.getFieldErrors().stream()
                    .map(error -> error.getDefaultMessage())
                    .distinct()
                    .collect(Collectors.joining(" | "));
            redirectAttributes.addFlashAttribute("todoOK", "error");
            redirectAttributes.addFlashAttribute("message", errores);
            if (cliente.getIdCliente() != null && !cliente.getIdCliente().isEmpty()) {
                
                return "/clientes/modificar" + cliente.getIdCliente();
            }
            return "redirect:/clientes/listado";
        }

        try {
            clienteService.save(cliente);
            redirectAttributes.addFlashAttribute("todoOK", "todoOk");
            redirectAttributes.addFlashAttribute("message", "Cliente guardado exitosamente");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("todoOK", "error");
            redirectAttributes.addFlashAttribute("message", "Error inesperado al guardar el cliente");
        }

        return "redirect:/clientes/listado";

    }
    @PostMapping ("/eliminar")
    public String eliminar(@RequestParam String nombreEmpresa, RedirectAttributes redirectAttributes){
        try {
            clienteService.deleteByNombreEmpresa(nombreEmpresa);
            redirectAttributes.addFlashAttribute("todoOK", "todoOk");
            redirectAttributes.addFlashAttribute("message", "Cliente eliminado exitosamente");
        } catch (IllegalArgumentException | IllegalStateException e) {
            redirectAttributes.addFlashAttribute("todoOK", "error");
            redirectAttributes.addFlashAttribute("message", e.getMessage());
        } 
        return "redirect:/clientes/listado";
    }
    
    @PostMapping("/buscar")
    public String buscar(@RequestParam String nombreEmpresa, RedirectAttributes redirectAttributes, Model model){
        try {
            Optional<Cliente> cliente = clienteService.findByNombreEmpresa(nombreEmpresa);
            if(cliente.isPresent()){
                model.addAttribute("clientes", cliente.get());
                return "clientes/detalle";
            } else {
                redirectAttributes.addFlashAttribute("todoOK", "error");
                redirectAttributes.addFlashAttribute("message", "No se encontró cliente con ese nombre de empresa");
                return "redirect:/clientes/listado";
            }
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("todoOK", "error");
            redirectAttributes.addFlashAttribute("message", e.getMessage());
            return "redirect:/clientes/listado";
        }
    }
    
    @GetMapping ("/modificar/{idCliente}")
    public String modificar(@PathVariable("idCliente") String idCliente,
            RedirectAttributes redirectAttributes, Model model){
        Optional<Cliente> clienteOpt = clienteService.findById(idCliente);
        if(clienteOpt.isEmpty()){
            redirectAttributes.addFlashAttribute("error", 
            "No se encontró un cliente con el id: "+idCliente);
            return "redirect:/clientes/listado";
        }
        Cliente cliente = clienteOpt.get();
        model.addAttribute("cliente", cliente);
        return "/clientes/modifica";
    }

}
