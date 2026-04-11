package com.vas.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.vas.model.Tarea;

public interface TareaRepository extends MongoRepository<Tarea, String> {
}