package com.vas.model;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Document(collection = "proyectos")
public class Proyecto implements Serializable {

    @Id
    private String idProyecto;

    @NotNull(message = "El cliente es obligatorio")
    private String cliente;

    @NotNull(message = "El servicio es obligatorio")
    private String servicio;

    @NotNull(message = "El nombre del proyecto es obligatorio")
    @Size(max = 150)
    private String nombreProyecto;

    @NotNull(message = "El estado es obligatorio")
    private String estado;

    @NotNull(message = "El precio es obligatorio")
    private double precio;

    public Proyecto() {}
}