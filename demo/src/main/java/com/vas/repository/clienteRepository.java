package com.vas.repository;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.vas.model.Cliente;
@Repository

public interface clienteRepository extends MongoRepository<Cliente, String> {

    public Optional<Cliente> findByNombreEmpresa(String nombreEmpresa);
    
    public void deleteByNombreEmpresa(String nombreEmpresa);
    
    public boolean existsByNombreEmpresa(String nombreEmpresa);
}
