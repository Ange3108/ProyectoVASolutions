package com.vas.service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vas.model.Empleado;
import com.vas.repository.EmpleadoRepository;

@Service
public class EmpleadoService {

    private final EmpleadoRepository empleadoRepo;

    public EmpleadoService(EmpleadoRepository empleadoRepo) {
        this.empleadoRepo = empleadoRepo;
    }

    @Transactional(readOnly = true)
    public List<Empleado> getAllEmpleados() {
        return empleadoRepo.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Empleado> findById(String idEmpleado) {
        return empleadoRepo.findById(Objects.requireNonNull(idEmpleado, "idEmpleado no puede ser null"));
    }

    @Transactional(readOnly = true)
    public Optional<Empleado> findByEmail(String email) {
        return empleadoRepo.findByEmail(email);
    }

    @Transactional(readOnly = true)
    public Optional<Empleado> findByNombre(String nombre) {
        return empleadoRepo.findByNombre(nombre);
    }

    @Transactional
    public void save(Empleado empleado) {
        if (empleado.getIdEmpleado() == null || empleado.getIdEmpleado().isEmpty()) {
            if (empleadoRepo.existsByEmail(empleado.getEmail())) {
                throw new IllegalArgumentException("Ya existe un empleado con ese email");
            }
        } else {
                Optional<Empleado> existente = empleadoRepo.findById(
                    Objects.requireNonNull(empleado.getIdEmpleado(), "idEmpleado no puede ser null"));
            if (existente.isEmpty()) {
                throw new IllegalArgumentException("Empleado no encontrado");
            }

            Empleado empleadoDB = existente.get();
            if (!empleadoDB.getEmail().equalsIgnoreCase(empleado.getEmail())
                    && empleadoRepo.existsByEmail(empleado.getEmail())) {
                throw new IllegalArgumentException("Ya existe otro empleado con ese email");
            }
        }

        empleadoRepo.save(empleado);
    }

    @Transactional
    public void deleteByEmail(String email) {
        Optional<Empleado> empleado = empleadoRepo.findByEmail(email);
        if (empleado.isEmpty()) {
            throw new IllegalArgumentException("No existe un empleado con ese email");
        }
        empleadoRepo.deleteByEmail(email);
    }
}