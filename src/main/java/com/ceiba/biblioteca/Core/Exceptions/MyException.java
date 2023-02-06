package com.ceiba.biblioteca.Core.Exceptions;

import com.ceiba.biblioteca.Core.DTO.MensajeDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
public class MyException extends Throwable {
    private MensajeDTO mensajeDTO;
}
