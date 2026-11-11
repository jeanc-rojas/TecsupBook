package com.tecsup.biblioteca.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tecsup.biblioteca.model.Libro;

public interface LibroRepository extends JpaRepository<Libro, Long> {

    List<Libro> findByTitulo(String titulo);

    List<Libro> findByAnioPublicacionGreaterThan(Integer anio);
}


