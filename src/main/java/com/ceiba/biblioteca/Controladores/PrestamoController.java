package com.ceiba.biblioteca.Controladores;

import com.ceiba.biblioteca.Core.Exceptions.MyException;
import com.ceiba.biblioteca.Core.Entidades.PrestamoEntidad;
import com.ceiba.biblioteca.Core.Servicios.PrestamoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/prestamo")
public class PrestamoController {

    @Autowired
    private PrestamoService prestamoService;

    @PostMapping()
    public ResponseEntity<Map<String, Object>> registrarPrestamo(@RequestBody PrestamoEntidad prestamo){
        try{
            PrestamoEntidad prestamoEntidad = this.prestamoService.registrarPrestamo(prestamo);
            MultiValueMap<String, Object> multiValueMap = new LinkedMultiValueMap<>();
            multiValueMap.add("id", prestamoEntidad.getId());
            multiValueMap.add("fechaMaximaDevolucion", prestamoEntidad.getFechaMaximaDevolucion());
            return ResponseEntity.ok(multiValueMap.toSingleValueMap());
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
