package com.vas.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.vas.model.Empleado;

@Repository
public interface EmpleadoRepository extends MongoRepository<Empleado, String> {

    Optional<Empleado> findByEmail(String email);

    Optional<Empleado> findByNombre(String nombre);

    void deleteByEmail(String email);

    boolean existsByEmail(String email);
}