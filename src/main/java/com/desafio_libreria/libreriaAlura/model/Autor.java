package com.desafio_libreria.libreriaAlura.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table (name = "autores")
public class Autor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String nombre;

    private Integer anioDeNacimiento;

    private Integer anioDeFallecimiento;

    @OneToMany(mappedBy = "autor", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Libro> libros;

    public Autor(){

    }
    public Autor (DatosAutor datosAutor){
        this.nombre = datosAutor.nombreAutor();
        this.anioDeNacimiento = datosAutor.anioDeNacimiento();
        this.anioDeFallecimiento = datosAutor.anioDeFallecimiento();
    }

    @Override
    public String toString() {

        StringBuilder librosStr = new StringBuilder();
        librosStr.append(" Libros: ");

        for (int i = 0; i < libros.size(); i++){
            librosStr.append(libros.get(i).getTitulo());
            if(i<libros.size() -1){
                librosStr.append(", ");

            }

        }
        return String.format("********** Autor **********%nNombre:"+
                " %s%n%nFecha de Nacimiento: %s%nFecha de Fallecimiento:"+
                " %s%n**********%n", nombre, librosStr.toString(), anioDeNacimiento,anioDeFallecimiento);



    }

    public Long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public Integer getAnioDeNacimiento() {
        return anioDeNacimiento;
    }

    public Integer getAnioDeFallacimiento() {
        return anioDeFallecimiento;
    }

    public List<Libro> getLibros() {
        return libros;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setAnioDeNacimiento(Integer anioDeNacimiento) {
        this.anioDeNacimiento = anioDeNacimiento;
    }

    public void setAnioDeFallacimiento(Integer anioDeFallacimiento) {
        this.anioDeFallecimiento = anioDeFallacimiento;
    }

    public void setLibros(List<Libro> libros) {
        this.libros = libros;
    }
}
