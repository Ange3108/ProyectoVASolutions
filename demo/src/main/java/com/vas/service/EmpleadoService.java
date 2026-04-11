package com.vas.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.vas.model.Empleado;
import com.vas.repository.EmpleadoRepository;

import java.util.List;

@Service
public class EmpleadoService {

    @Autowired
    private EmpleadoRepository repo;

    public List<Empleado> listar() {
        return repo.findAll();
    }

    public Empleado guardar(Empleado e) {
        return repo.save(e);
    }

    public void eliminar(String id) {
        repo.deleteById(id);
    }
}