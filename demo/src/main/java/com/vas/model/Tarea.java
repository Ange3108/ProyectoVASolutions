package com.vas.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Document(collection = "tareas")
public class Tarea {

    @Id
    private String idTarea;

    @NotBlank(message = "El proyecto es obligatorio")
    @Size(min = 3, max = 80, message = "El proyecto debe tener entre 3 y 80 caracteres")
    private String proyecto;

    @NotBlank(message = "El título es obligatorio")
    @Size(min = 3, max = 80, message = "El título debe tener entre 3 y 80 caracteres")
    private String titulo;

    @NotBlank(message = "El empleado es obligatorio")
    @Size(min = 3, max = 60, message = "El empleado debe tener entre 3 y 60 caracteres")
    private String empleado;

    @NotBlank(message = "El estado es obligatorio")
    @Size(min = 3, max = 30, message = "El estado debe tener entre 3 y 30 caracteres")
    private String estado;

    public Tarea() {
    }

    public String getIdTarea() {
        return idTarea;
    }

    public void setIdTarea(String idTarea) {
        this.idTarea = idTarea;
    }

    public String getProyecto() {
        return proyecto;
    }

    public void setProyecto(String proyecto) {
        this.proyecto = proyecto;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getEmpleado() {
        return empleado;
    }

    public void setEmpleado(String empleado) {
        this.empleado = empleado;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}