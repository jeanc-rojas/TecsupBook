package com.tecsup.biblioteca.service;

import java.time.Year;
import java.util.List;

import org.springframework.stereotype.Service;

import com.tecsup.biblioteca.exception.RecursoNoEncontradoException;
import com.tecsup.biblioteca.exception.ReglaNegocioException;
import com.tecsup.biblioteca.model.Autor;
import com.tecsup.biblioteca.model.Libro;
import com.tecsup.biblioteca.repository.AutorRepository;
import com.tecsup.biblioteca.repository.LibroRepository;

@Service
public class LibroService {

    private final LibroRepository libroRepository;
    private final AutorRepository autorRepository;

    public LibroService(LibroRepository libroRepository, AutorRepository autorRepository) {
        this.libroRepository = libroRepository;
        this.autorRepository = autorRepository;
    }

    public Libro registrar(Libro libro) {
        // Regla 1: título obligatorio
        if (libro.getTitulo() == null || libro.getTitulo().isBlank()) {
            throw new ReglaNegocioException("El título del libro es obligatorio");
        }
        // Regla 2: editorial no vacía
        if (libro.getEditorial() == null || libro.getEditorial().isBlank()) {
            throw new ReglaNegocioException("La editorial del libro no puede estar vacía");
        }
        // Regla 3: año obligatorio y no mayor al actual
        if (libro.getAnioPublicacion() == null) {
            throw new ReglaNegocioException("El año de publicación es obligatorio");
        }
        if (libro.getAnioPublicacion() > Year.now().getValue()) {
            throw new ReglaNegocioException("El año de publicación no puede ser mayor al año actual");
        }
        // Regla 4: debe pertenecer a un autor existente
        if (libro.getAutor() == null || libro.getAutor().getId() == null) {
            throw new ReglaNegocioException("El libro debe indicar un autor (autor.id)");
        }
        Long autorId = libro.getAutor().getId();
        Autor autor = autorRepository.findById(autorId)
                .orElseThrow(() -> new RecursoNoEncontradoException("Autor no encontrado con id " + autorId));

        libro.setAutor(autor);
        libro.setId(null);
        return libroRepository.save(libro);
    }

    public List<Libro> listar() {
        return libroRepository.findAll();
    }

    public Libro buscarPorId(Long id) {
        return libroRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Libro no encontrado con id " + id));
    }

    public void eliminar(Long id) {
        if (!libroRepository.existsById(id)) {
            throw new RecursoNoEncontradoException("Libro no encontrado con id " + id);
        }
        libroRepository.deleteById(id);
    }

    public List<Libro> buscarPorTitulo(String titulo) {
        return libroRepository.findByTitulo(titulo);
    }

    public List<Libro> buscarPosterioresA(Integer anio) {
        return libroRepository.findByAnioPublicacionGreaterThan(anio);
    }
}

