package com.vas.model;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Document(collection = "campanas_redes")
public class CampanaRedes implements Serializable {

    @Id
    private String idCampana;

    @NotNull
    private String cliente;

    @NotNull
    private String plataforma;

    @NotNull
    private String tipoCampana;

    @NotNull
    private double presupuesto;

    public CampanaRedes() {}
}