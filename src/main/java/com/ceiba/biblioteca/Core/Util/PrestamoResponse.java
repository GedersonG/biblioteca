package com.ceiba.biblioteca.Core.Util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// Clase construida para responder a un registro de préstamo.
@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
public class PrestamoResponse {
    int id;
    String fechaMaximaDevolucion;
}
