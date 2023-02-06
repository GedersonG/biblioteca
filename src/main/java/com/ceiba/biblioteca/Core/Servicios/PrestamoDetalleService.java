package com.ceiba.biblioteca.Core.Servicios;

import com.ceiba.biblioteca.Core.DTO.MensajeDTO;
import com.ceiba.biblioteca.Core.Entidades.PrestamoDetalleEntidad;
import com.ceiba.biblioteca.Core.Exceptions.MyException;
import com.ceiba.biblioteca.Repositorios.PrestamoDetalleRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.text.html.Option;
import java.util.Optional;

@Service
@Transactional
public class PrestamoDetalleService {

    @Autowired
    private PrestamoDetalleRepositorio prestamoDetalleRepositorio;

    /**
     * Función que registra el detalle de un préstamo.
     * @param prestamoDetalleEntidad Objeto préstamo.
     */
    public void registrarPrestamoDetalle(PrestamoDetalleEntidad prestamoDetalleEntidad){
        this.prestamoDetalleRepositorio.save(prestamoDetalleEntidad);
    }

    /**
     * Función que comprueba si un detalle de préstamo existe por ID.
     * @param id Identificador del detalle préstamo.
     * @return True si el detalle préstamo existe, false si no existe.
     */
    public boolean siExistePorId(int id){
        return this.prestamoDetalleRepositorio.existsById(id);
    }

    /**
     * Función que obtiene un detalle préstamo por ID.
     * @param id Identificador del detalle préstamo a buscar.
     * @return Un detalle préstamo por su ID.
     * @throws MyException cuando el id a buscar no existe.
     */
    public PrestamoDetalleEntidad obtenerPrestamoPorId(int id) throws MyException {
        if(!this.siExistePorId(id))
            throw new MyException(new MensajeDTO("El id " + id + " no existe."));
        return this.prestamoDetalleRepositorio.findById(id).get();
    }
}
