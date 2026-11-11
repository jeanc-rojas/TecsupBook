package com.tecsup.biblioteca.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tecsup.biblioteca.model.Autor;

public interface AutorRepository extends JpaRepository<Autor, Long> {
}

