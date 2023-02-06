package com.ceiba.biblioteca.Repositorios;

import com.ceiba.biblioteca.Core.Entidades.PrestamoDetalleEntidad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PrestamoDetalleRepositorio extends JpaRepository<PrestamoDetalleEntidad, Integer> {
    public abstract boolean existsById (int id);
}
