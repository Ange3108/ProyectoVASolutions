package com.vas.service;

import java.util.List;
import java.util.Optional;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vas.model.Servicio;
import com.vas.repository.servicioRepository;

@Service
public class servicioService {
    private final servicioRepository servicioRepo;
    public servicioService(servicioRepository servicioRepo) {
        this.servicioRepo = servicioRepo;
    }

    @Transactional (readOnly = true)
    public List<Servicio> getAllServicios(){
        return servicioRepo.findAll();
    }

    @Transactional(readOnly = true)
        public Optional<Servicio> findByNombreServicio(String nombreServicio){
            return servicioRepo.findByNombreServicio(nombreServicio);
        }

    @Transactional
    public void deleteByNombreServicio(String nombreServicio){
        Optional<Servicio> servicio = servicioRepo.findByNombreServicio(nombreServicio);
        if(servicio.isEmpty()){
            throw new IllegalArgumentException("No existe un servicio con el nombre: " + nombreServicio);
        }
        try {
            servicioRepo.deleteByNombreServicio(nombreServicio);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalStateException("No se puede eliminar el servicio " + nombreServicio + " porque tiene datos asociados");
        }
    }

 

     //Este metodo hace 2 acciones, crea un registro o lo actualiza
     @Transactional
     public void save(Servicio servicio){
        if(servicio.getIdServicio() == null){
            //es una creacion
            if(servicioRepo.existsByNombreServicio(servicio.getNombreServicio())){
                throw new IllegalArgumentException("Ya existe un servicio con el nombre: " + servicio.getNombreServicio());
            }
        } else {
            //es una actualizacion
            Optional<Servicio> servicioOpt = servicioRepo.findById(servicio.getIdServicio());
            if(servicioOpt.isEmpty()){
                throw new IllegalArgumentException("No existe un servicio con el id: " + servicio.getIdServicio());
            }
            Servicio servicioDB = servicioOpt.get();
            if(!servicioDB.getNombreServicio().equals(servicio.getNombreServicio()) && 
                servicioRepo.existsByNombreServicio(servicio.getNombreServicio())){
                    throw new IllegalArgumentException("Ya existe un servicio con el nombre: " + servicio.getNombreServicio());
                }
        }
        servicioRepo.save(servicio);
     }
    
}
