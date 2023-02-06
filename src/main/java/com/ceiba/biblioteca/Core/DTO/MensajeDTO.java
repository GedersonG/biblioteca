package com.ceiba.biblioteca.Core.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class MensajeDTO {
    private String mesanje;

    public void agregarMensaje(String mensaje){
        this.mesanje += mensaje;
    }
}
