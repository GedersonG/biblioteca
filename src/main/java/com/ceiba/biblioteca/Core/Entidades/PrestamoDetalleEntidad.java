package com.ceiba.biblioteca.Core.Entidades;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class PrestamoDetalleEntidad {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String isbn;
    private String identificaciónUsuario;
    private int tipoUsuario;
    private String fechaMaximaDevolucion;

    public PrestamoDetalleEntidad(PrestamoEntidad prestamoEntidad, String fechaMaximaDevolucion){
        this.isbn = prestamoEntidad.getIsbn();
        this.identificaciónUsuario = prestamoEntidad.getIdentificaciónUsuario();
        this.tipoUsuario = prestamoEntidad.getTipoUsuario();
        this.fechaMaximaDevolucion = fechaMaximaDevolucion;
    }
}
