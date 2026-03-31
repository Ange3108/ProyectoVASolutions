package com.vas.model;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Document(collection = "servicios")
public class Servicio implements Serializable {

    @Id
    private String idServicio;
    @NotNull(message = "El nombre del servicio es obligatorio")
    @Size(max = 120, message = "El nombre del servicio no puede superar 120 caracteres")
    private String nombreServicio;
    @NotNull(message = "La descripcion es obligatoria")
    @Size(max = 500, message = "La descripcion no puede superar 500 caracteres")
    private String descripcion;
    @NotNull(message = "El precio base es obligatorio")
    @Size(max = 10, message = "El precio base no puede superar 10 caracteres")
    private int precioBase;

    public Servicio(String nombreServicio, String descripcion, int precioBase) {
        this.nombreServicio = nombreServicio;
        this.descripcion = descripcion;
        this.precioBase = precioBase;
    }

    public Servicio() {
    }

}
