package com.vas.model;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Document(collection = "clientes")
public class Cliente implements Serializable {

@Id
  private String idCliente;

  @NotNull(message = "El nombre de la empresa es obligatorio")
  @Size(max = 120, message = "El nombre de la empresa no puede superar 120 caracteres")
  private String nombreEmpresa;

  @NotNull(message = "El contacto es obligatorio")
  @Size(max = 120, message = "El contacto no puede superar 120 caracteres")
  private String contacto;

  @NotNull(message = "El telefono es obligatorio")
  private String telefono;

  @NotNull(message = "El email es obligatorio")
  @Email(message = "El email no es valido")
  private String email;

  @NotNull(message = "La direccion es obligatoria")
  @Size(max = 200, message = "La direccion no puede superar 200 caracteres")
  private String direccion;


  @NotNull(message = "El estado es obligatorio")
  @Size(max = 50, message = "El estado no puede superar 50 caracteres")
  private String estado;

  public Cliente(String idCliente, String nombreEmpresa, String contacto, String telefono, String email, String direccion, String estado) {
        this.idCliente = idCliente;
        this.nombreEmpresa = nombreEmpresa;
        this.contacto = contacto;
        this.telefono = telefono;
        this.email = email;
        this.direccion = direccion;
        this.estado = estado;
    }

    public Cliente() {
    }

 
}
