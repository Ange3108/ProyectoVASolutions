package com.vas.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vas.model.Tarea;
import com.vas.repository.TareaRepository;

@Service
public class TareaService {

    private final TareaRepository tareaRepo;

    public TareaService(TareaRepository tareaRepo) {
        this.tareaRepo = tareaRepo;
    }

    @Transactional(readOnly = true)
    public List<Tarea> getAllTareas() {
        return tareaRepo.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Tarea> findById(String idTarea) {
        return tareaRepo.findById(idTarea);
    }

    @Transactional(readOnly = true)
    public Optional<Tarea> findByTitulo(String titulo) {
        return tareaRepo.findByTitulo(titulo);
    }

    @Transactional(readOnly = true)
    public Optional<Tarea> findByProyecto(String proyecto) {
        return tareaRepo.findByProyecto(proyecto);
    }

    @Transactional
    public void save(Tarea tarea) {
        if (tarea.getIdTarea() == null || tarea.getIdTarea().isEmpty()) {
            if (tareaRepo.existsByTitulo(tarea.getTitulo())) {
                throw new IllegalArgumentException("Ya existe una tarea con ese título");
            }
        } else {
            Optional<Tarea> existente = tareaRepo.findById(tarea.getIdTarea());
            if (existente.isEmpty()) {
                throw new IllegalArgumentException("Tarea no encontrada");
            }

            Tarea tareaDB = existente.get();
            if (!tareaDB.getTitulo().equalsIgnoreCase(tarea.getTitulo())
                    && tareaRepo.existsByTitulo(tarea.getTitulo())) {
                throw new IllegalArgumentException("Ya existe otra tarea con ese título");
            }
        }

        tareaRepo.save(tarea);
    }

    @Transactional
    public void deleteByTitulo(String titulo) {
        Optional<Tarea> tarea = tareaRepo.findByTitulo(titulo);
        if (tarea.isEmpty()) {
            throw new IllegalArgumentException("No existe una tarea con ese título");
        }
        tareaRepo.deleteByTitulo(titulo);
    }
}