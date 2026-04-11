package com.vas.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import jakarta.validation.constraints.NotBlank;

@Document(collection = "tareas")
public class Tarea {

    @Id
    private String id;

    @NotBlank(message = "Proyecto obligatorio")
    private String proyecto;

    @NotBlank(message = "Título obligatorio")
    private String titulo;

    @NotBlank(message = "Empleado obligatorio")
    private String empleado;

    @NotBlank(message = "Estado obligatorio")
    private String estado;

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getProyecto() { return proyecto; }
    public void setProyecto(String proyecto) { this.proyecto = proyecto; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getEmpleado() { return empleado; }
    public void setEmpleado(String empleado) { this.empleado = empleado; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
}