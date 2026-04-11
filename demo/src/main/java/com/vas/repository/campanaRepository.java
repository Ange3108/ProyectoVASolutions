package com.vas.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.vas.model.CampanaRedes;

public interface campanaRepository extends MongoRepository<CampanaRedes, String> {

    Optional<CampanaRedes> findByTipoCampana(String tipoCampana);

    void deleteByTipoCampana(String tipoCampana);
}