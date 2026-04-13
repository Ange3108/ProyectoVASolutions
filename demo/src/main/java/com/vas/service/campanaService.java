package com.vas.service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vas.model.CampanaRedes;
import com.vas.repository.campanaRepository;

@Service
public class campanaService {

    private final campanaRepository campanaRepo;

    public campanaService(campanaRepository campanaRepo) {
        this.campanaRepo = campanaRepo;
    }

    @Transactional(readOnly = true)
    public List<CampanaRedes> getAll() {
        return campanaRepo.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<CampanaRedes> findByTipoCampana(String tipo) {
        return campanaRepo.findByTipoCampana(tipo);
    }

    @Transactional
    public void save(CampanaRedes campana) {
        campanaRepo.save(Objects.requireNonNull(campana, "campana no puede ser null"));
    }

    @Transactional
    public void deleteByTipoCampana(String tipo) {
        campanaRepo.deleteByTipoCampana(tipo);
    }
}