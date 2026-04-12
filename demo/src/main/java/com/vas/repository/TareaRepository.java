package com.vas.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.vas.model.Tarea;

@Repository
public interface TareaRepository extends MongoRepository<Tarea, String> {

    Optional<Tarea> findByTitulo(String titulo);

    Optional<Tarea> findByProyecto(String proyecto);

    void deleteByTitulo(String titulo);

    boolean existsByTitulo(String titulo);
}