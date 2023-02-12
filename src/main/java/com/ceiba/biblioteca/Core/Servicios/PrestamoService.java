package com.ceiba.biblioteca.Core.Servicios;

import com.ceiba.biblioteca.Core.DTO.MensajeDTO;
import com.ceiba.biblioteca.Core.Exceptions.MyException;
import com.ceiba.biblioteca.Core.DTO.PrestamoDTO;
import com.ceiba.biblioteca.Core.Entidades.PrestamoEntidad;
import com.ceiba.biblioteca.Repositorios.PrestamoRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
@Transactional
public class PrestamoService {

    @Autowired
    private PrestamoRepositorio prestamoRepositorio;

    @Value("${prestamo.MAXIMO_PLAZO_USUARIO_AFILIADO}")
    private byte MAXIMO_PLAZO_USUARIO_AFILIADO;

    @Value("${prestamo.MAXIMO_PLAZO_USUARIO_EMPLEADO}")
    private byte MAXIMO_PLAZO_USUARIO_EMPLEADO;

    @Value("${prestamo.MAXIMO_PLAZO_USUARIO_INVITADO}")
    private byte MAXIMO_PLAZO_USUARIO_INVITADO;

    @Value("${prestamo.USUARIO_AFILIADO}")
    private byte USUARIO_AFILIADO;

    @Value("${prestamo.USUARIO_EMPLEADO}")
    private byte USUARIO_EMPLEADO;

    @Value("${prestamo.USUARIO_INVITADO}")
    private byte USUARIO_INVITADO;

    /**
     * Función que comprueba si un préstamo existe por su ID.
     * @param id Identificador del préstamo.
     * @return True si existe, False si no existe.
     */
    public boolean siExiste(int id){
        return this.prestamoRepositorio.existsById(id);
    }

    /**
     * Función que busca un prestamo por id.
     * @param id: El ID del prestamo a buscar.
     * @return El prestamo perteneciente al ID.
     * @throws Exception cuando el id no referencia a ningún prestamo.
     */
    public Optional<PrestamoEntidad> getPrestamoPorId(int id) throws MyException {
        if(!this.siExiste(id))
            throw new MyException(new MensajeDTO("El id " + id + " no existe."));
        return this.prestamoRepositorio.findById(id);
    }

    /**
     * Funcion que registra un prestamo nuevo
     * @param prestamo Prestamo a registrar
     * @return Prestamo registrado
     * @throws Exception cuando los datos de llegada son inválidos, por ejemplo isbn='abcdefghijk'
     */
    public PrestamoEntidad registrarPrestamo(PrestamoEntidad prestamo) throws MyException {
        try{
            // Creacion de objeto PrestamoDTO, el cual va a comprobar que los datos son válidos.
            PrestamoDTO prestamoDTO = new PrestamoDTO(prestamo.getIdentificaciónUsuario(),
                    prestamo.getIsbn(), prestamo.getTipoUsuario());
            prestamoDTO.validator();
            this.comprobarPrestamo(prestamo.getIdentificaciónUsuario(), prestamo.getTipoUsuario());
        } catch (MyException exception){
            throw new MyException(exception.getMensajeDTO());
        }
        // Calculamos el plazo maximo dependiendo el tipo de usuario.
        String fechaPlazoMaxima = calcularMaximaFechaEntrega(calcularPlazoUsuario(prestamo.getTipoUsuario()));
        prestamo.setFechaMaximaDevolucion(fechaPlazoMaxima);
        return this.prestamoRepositorio.save(prestamo);
    }

    /**
     * Función que calculcula el plazo máximo de entrega de un libro.
     * @param plazoPorUsuario Entero con el plazo establecido por el tipo de usuario
     * @return String con la máxima fecha de entrega en el formato dd/mm/yyyy ejemplo, 15/02/2021.
     */
    public String calcularMaximaFechaEntrega(int plazoPorUsuario){
        // Iniciamos la fecha de entrega con la fecha actual.
        Calendar fechaActual = new GregorianCalendar();
        int diasContados = 0;
        while(diasContados < plazoPorUsuario){
            int diaSemana = fechaActual.get(Calendar.DAY_OF_WEEK);
            if(diaSemana != Calendar.SUNDAY && diaSemana != Calendar.SATURDAY)
                ++diasContados;
            fechaActual.add(Calendar.DATE, 1);
        }
        String maximaFechaEntrega = new SimpleDateFormat("dd/MM/yyyy").format(fechaActual.getTime());
        return maximaFechaEntrega;
    }

    /**
     * Función que calcula la cantidad de días de plazo máximo, dependiendo el tipo de usuario
     * @param tipoUsuario Entero que representa el tipo de usuario.
     * @return El plazo máximo por tipo de usuario, ejemplo Tipo de usuario 3 = Invitado, plazo máximo = 7 días.
     */
    public byte calcularPlazoUsuario(int tipoUsuario){
        if(tipoUsuario == this.USUARIO_AFILIADO)
            return this.MAXIMO_PLAZO_USUARIO_AFILIADO;
        if(tipoUsuario == this.USUARIO_EMPLEADO)
            return this.MAXIMO_PLAZO_USUARIO_EMPLEADO;
        return this.MAXIMO_PLAZO_USUARIO_INVITADO; // default
    }

    /**
     * Función que comprueba si un usuario ya tiene un préstamo actualmente.
     * @param identificaciónUsuario Identificación del usuario
     * @return True si ya tiene un préstamo actualmente o false si no tiene ningún prestamo actualmente.
     */
    public boolean tienePrestamo(String identificaciónUsuario){
        return this.prestamoRepositorio.existsByIdentificaciónUsuario(identificaciónUsuario);
    }

    /**
     * Función que comprueba si se le puede realizar un préstamo al usuario.
     * @param identificaciónUsuario Identificación del usuario que solicita el préstamo.
     * @param tipoUsuario Tipo de usuario que solicita el préstamo.
     * @throws MyException cuando el usuario es de tipo invitado y ya tiene un préstamo.
     */
    public void comprobarPrestamo(String identificaciónUsuario, int tipoUsuario) throws MyException {
        if(tienePrestamo(identificaciónUsuario) && tipoUsuario == this.USUARIO_INVITADO)
            throw new MyException(new MensajeDTO("El usuario con identificación " +
                    identificaciónUsuario + " ya tiene un libro prestado por lo cual no se le " +
                    "puede realizar otro préstamo"));
    }
}