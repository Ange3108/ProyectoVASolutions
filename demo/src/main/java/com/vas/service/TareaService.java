package com.vas.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.vas.model.Tarea;
import com.vas.repository.TareaRepository;

import java.util.List;

@Service
public class TareaService {

    @Autowired
    private TareaRepository repo;

    public List<Tarea> listar() {
        return repo.findAll();
    }

    public Tarea guardar(Tarea t) {
        return repo.save(t);
    }

    public void eliminar(String id) {
        repo.deleteById(id);
    }
}