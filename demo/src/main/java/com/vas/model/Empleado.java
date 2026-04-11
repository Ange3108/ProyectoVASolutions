package com.vas.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import jakarta.validation.constraints.NotBlank;

@Document(collection = "empleados")
public class Empleado {

    @Id
    private String id;

    @NotBlank(message = "Nombre obligatorio")
    private String nombre;

    @NotBlank(message = "Puesto obligatorio")
    private String puesto;

    @NotBlank(message = "Especialidad obligatoria")
    private String especialidad;

    @NotBlank(message = "Email obligatorio")
    private String email;

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getPuesto() { return puesto; }
    public void setPuesto(String puesto) { this.puesto = puesto; }

    public String getEspecialidad() { return especialidad; }
    public void setEspecialidad(String especialidad) { this.especialidad = especialidad; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}