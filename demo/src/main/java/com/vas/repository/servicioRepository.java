package com.vas.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.vas.model.Servicio;
@Repository
public interface servicioRepository extends MongoRepository<Servicio, String> {
    public boolean existsByNombreServicio(String nombreServicio);
    public void deleteByNombreServicio(String nombreServicio);
    public Optional<Servicio> findByNombreServicio(String nombreServicio);
}
