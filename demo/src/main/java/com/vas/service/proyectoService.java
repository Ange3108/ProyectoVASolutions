package com.vas.service;

import java.util.List;
import java.util.Optional;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vas.model.Proyecto;
import com.vas.repository.proyectoRepository;

@Service
public class proyectoService {

    private final proyectoRepository proyectoRepo;

    public proyectoService(proyectoRepository proyectoRepo) {
        this.proyectoRepo = proyectoRepo;
    }

    @Transactional(readOnly = true)
    public List<Proyecto> getAllProyectos() {
        return proyectoRepo.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Proyecto> findByNombreProyecto(String nombreProyecto) {
        return proyectoRepo.findByNombreProyecto(nombreProyecto);
    }

    @Transactional(readOnly = true)
    public Optional<Proyecto> findById(String idProyecto) {
        return proyectoRepo.findById(idProyecto);
    }

    @Transactional
    public void deleteByNombreProyecto(String nombreProyecto) {
        Optional<Proyecto> proyecto = proyectoRepo.findByNombreProyecto(nombreProyecto);
        if (proyecto.isEmpty()) {
            throw new IllegalArgumentException("No existe el proyecto: " + nombreProyecto);
        }
        try {
            proyectoRepo.deleteByNombreProyecto(nombreProyecto);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalStateException("No se puede eliminar el proyecto");
        }
    }

    @Transactional
    public void save(Proyecto proyecto) {
        Optional<Proyecto> existente = proyectoRepo.findByNombreProyecto(proyecto.getNombreProyecto());
        if (existente.isPresent() && !existente.get().getIdProyecto().equals(proyecto.getIdProyecto())) {
            throw new IllegalArgumentException("Ya existe un proyecto con ese nombre");
        }
        proyectoRepo.save(proyecto);
    }
}