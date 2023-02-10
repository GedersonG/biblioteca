package com.ceiba.biblioteca.Controladores;

import com.ceiba.biblioteca.Core.Exceptions.MyException;
import com.ceiba.biblioteca.Core.Entidades.PrestamoEntidad;
import com.ceiba.biblioteca.Core.Servicios.PrestamoService;
import com.ceiba.biblioteca.Core.Util.PrestamoResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/prestamo")
public class PrestamoController {

    @Autowired
    private PrestamoService prestamoService;


    @PostMapping()
    public ResponseEntity<PrestamoResponse> registrarPrestamo(@RequestBody PrestamoEntidad prestamo){
        try{
            PrestamoResponse prestamoResponse = this.prestamoService.registrarPrestamo(prestamo);
            return new ResponseEntity(prestamoResponse, HttpStatus.OK);
        } catch (MyException exception){
            return new ResponseEntity(exception.getMensajeDTO(), HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/{id-prestamo}")
    public ResponseEntity<PrestamoEntidad> consultarDetallePrestamo(@PathVariable("id-prestamo") int id){
        try{
            PrestamoEntidad prestamoEntidad = this.prestamoService.getPrestamoPorId(id).get();
            return new ResponseEntity(prestamoEntidad, HttpStatus.OK);
        } catch (MyException exception){
            return new ResponseEntity(exception.getMensajeDTO(), HttpStatus.BAD_REQUEST);
        }
    }
}
