package com.desafio_libreria.libreriaAlura.repository;

import com.desafio_libreria.libreriaAlura.model.DatosIdioma;
import com.desafio_libreria.libreriaAlura.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LibroRepository extends JpaRepository<Libro,Long> {

    List<Libro> findByIdioma(DatosIdioma idioma);

    Optional<Libro> findByTitulo(String titulo);
}
