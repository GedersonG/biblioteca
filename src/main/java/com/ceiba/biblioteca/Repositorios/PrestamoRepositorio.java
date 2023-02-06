package com.ceiba.biblioteca.Repositorios;

import com.ceiba.biblioteca.Core.Entidades.PrestamoEntidad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PrestamoRepositorio extends JpaRepository<PrestamoEntidad, Integer>{
    public abstract boolean existsByIdentificaciónUsuario(String identificaciónUsuario);
    public abstract boolean existsById(int id);

}
