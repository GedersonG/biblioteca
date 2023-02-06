package com.ceiba.biblioteca.Core.Entidades;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter @Setter
public class PrestamoEntidad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String isbn;
    private String identificaciónUsuario;
    private int tipoUsuario;

}