package com.desafio_libreria.libreriaAlura.model;

import jakarta.persistence.*;

@Entity
@Table (name ="libros")
public class Libro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "autor_id")
    private Autor autor;

    @Enumerated(EnumType.STRING)
    private DatosIdioma idioma;

    private Integer numeroDeDescargas;

    public Libro() {
    }

    public Libro(DatosLibros datosLibros){
        this.titulo = datosLibros.titulo();
        this.idioma = datosLibros.idioma().get(0);
        this.numeroDeDescargas = datosLibros.numeroDeDescargas();



    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n");
        sb.append(" ========= Libro ==========\n");
        sb.append(" Titulo: ").append(titulo).append("\n");
        sb.append(" Autor: ").append(autor != null ? autor.getNombre() : "Desconocido").append("\n");
        sb.append(" Idioma: ").append(idioma).append("\n");
        sb.append(" Numero de descargas: ").append(numeroDeDescargas).append("\n");
        sb.append(" ==========================");

        return sb.toString();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    public DatosIdioma getIdioma() {
        return idioma;
    }

    public void setIdioma(DatosIdioma idioma) {
        this.idioma = idioma;
    }

    public Integer getNumeroDeDescargas() {
        return numeroDeDescargas;
    }

    public void setNumeroDeDescargas(Integer numeroDeDescargas) {
        this.numeroDeDescargas = numeroDeDescargas;
    }
}
