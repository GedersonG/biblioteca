package com.ceiba.biblioteca.Core.DTO;

import com.ceiba.biblioteca.Core.Exceptions.MyException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.constraints.*;
import java.util.Set;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class PrestamoDTO {

    @NotBlank
    @NotNull
    @Size(min = 1, max = 10)
    private String identificaciónUsuario;

    @NotNull
    @NotBlank
    @Size(min = 1, max = 10)
    private String isbn;

    @NotNull
    @Min(value = 1, message = "Tipo de usuario no permitido en la bilioteca")
    @Max(value = 3, message = "Tipo de usuario no permitido en la bilioteca")
    private int tipoUsuario;

    public void validator() throws MyException {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<PrestamoDTO>> violations =
                validator.validate(PrestamoDTO.this);
        MensajeDTO mensajeDTO = new MensajeDTO("");
        for (ConstraintViolation<PrestamoDTO> violation : violations) {
            mensajeDTO.agregarMensaje(violation.getMessage());
        }
        if(!mensajeDTO.getMesanje().isEmpty())
            throw new MyException(mensajeDTO);
    }
}
