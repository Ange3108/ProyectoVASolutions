package com.vas.service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vas.model.Cliente;
import com.vas.repository.clienteRepository;

@Service
public class clienteService {
    private final clienteRepository clienteRepo;
    
    public clienteService(clienteRepository clienteRepo) {
        this.clienteRepo = clienteRepo;
    }

    @Transactional(readOnly = true)
    public List<Cliente> getAllClientes() {
        return clienteRepo.findAll();
    }
    @Transactional(readOnly = true)
    public Optional<Cliente> findByNombreEmpresa(String nombreEmpresa) {
        return clienteRepo.findByNombreEmpresa(nombreEmpresa);
    }

    @Transactional(readOnly = true)
    public Optional<Cliente> findById(String idCliente) {
        return clienteRepo.findById(Objects.requireNonNull(idCliente, "idCliente no puede ser null"));
    }

    //Este metodo elimina fidicamente un registro de la tabla cliente
    //se hacen validaciones para prever efectivamente que se borra

    @Transactional
    public void deleteByNombreEmpresa(String nombreEmpresa){
       //se valida que el idProducto existe
        Optional<Cliente> cliente = clienteRepo.findByNombreEmpresa(nombreEmpresa);
        if(cliente.isEmpty()){
            //no existe... se lanza una excepcion para mostrar al usuario que no existe
            throw new IllegalArgumentException("No existe un cliente con el nombre de empresa: " + nombreEmpresa);
        }
        try {
            clienteRepo.deleteByNombreEmpresa(nombreEmpresa);
        } catch (DataIntegrityViolationException e) {
            //Se lanza una excepcion para indicar que hay datos asociados
            throw new IllegalStateException("No se puede eliminar el cliente " + nombreEmpresa + " porque tiene datos asociados");
        }
    }
    
   

      //Este metodo hace 2 acciones, crea un registro o lo actualiza
    //si el idproducto tiene un valor se hace un update, si viene vacio un insert
    @Transactional 
    public void save(Cliente cliente){
        Objects.requireNonNull(cliente, "cliente no puede ser null");
       
        // Validar que no exista otro cliente con el mismo nombreEmpresa
        Optional<Cliente> existente = clienteRepo.findByNombreEmpresa(cliente.getNombreEmpresa());
        if (existente.isPresent() && !existente.get().getIdCliente().equals(cliente.getIdCliente())) {
            throw new IllegalArgumentException("Ya existe un cliente con el nombre de empresa: " + cliente.getNombreEmpresa());
        }
        clienteRepo.save(cliente);
    }
}
