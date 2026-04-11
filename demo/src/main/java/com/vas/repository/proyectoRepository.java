package com.vas.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.vas.model.Proyecto;

@Repository
public interface proyectoRepository extends MongoRepository<Proyecto, String> {

    Optional<Proyecto> findByNombreProyecto(String nombreProyecto);

    void deleteByNombreProyecto(String nombreProyecto);

    boolean existsByNombreProyecto(String nombreProyecto);
}