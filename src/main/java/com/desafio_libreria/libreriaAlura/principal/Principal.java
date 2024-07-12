package com.desafio_libreria.libreriaAlura.principal;

import com.desafio_libreria.libreriaAlura.model.*;
import com.desafio_libreria.libreriaAlura.repository.AutorRepository;
import com.desafio_libreria.libreriaAlura.repository.LibroRepository;
import com.desafio_libreria.libreriaAlura.service.ConsumoAPI;
import com.desafio_libreria.libreriaAlura.service.ConvierteDatos;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Principal {

    Scanner teclado = new Scanner(System.in);
    private ConsumoAPI consumoAPI = new ConsumoAPI();
    private static final String URL_BASE = "https://gutendex.com/books/?search=";
    private ConvierteDatos convierteDatos = new ConvierteDatos();
    private List<Libro> libros;
    private List<Autor> autores;
    private LibroRepository libroRepository;
    private AutorRepository autorRepository;

    public Principal(LibroRepository libroRepository, AutorRepository autorRepository){
        this.libroRepository = libroRepository;
        this.autorRepository = autorRepository;

    }

    public void mostrarMenuOpciones(){

        var opcion = -1;
        while (opcion !=0){
            var menu = """
                    1. Buscar libro por Titulo
                    2. Listar libros registrados
                    3. Listar autores registrados
                    4. Listar autores vivos en el anio que indiques
                    5. Listar libros por idioma
                    0. Salir
                    """;
            System.out.println(menu);
            System.out.println("Por favor ingrese la opcion deseada");
            opcion = teclado.nextInt();
            teclado.nextLine();
            switch (opcion){
                case 1:
                    buscarLibroPorTitulo();
                    break;
                case 2:
                    listarLibrosRegistrados();
                    break;
                case 3:
                    listarAutoresRegistrados();
                    break;
                case 4:
                    listarAutoresVivos();
                    break;
                case 5:
                    listarLibrosPorIdioma();
                    break;
                case 0:
                    System.out.println("Gracias por usar la aplicacion: ");
                    break;
                default:
                    System.out.println("Opcion invalida:");
            }
        }


    }

    private Datos getDatos(){
        System.out.println("Escriba el titulo del libro: ");
        var nombreLibro = teclado.nextLine();
        var json = consumoAPI.obtenerDatosLibros(URL_BASE + nombreLibro.replace(" ","%20"));
        Datos datos = convierteDatos.obtenerDatosLibros(json, Datos.class);
        return datos;

    }

    private void buscarLibroPorTitulo(){
        Datos datos = getDatos();
        if(datos != null && !datos.resultados().isEmpty()){
            DatosLibros primerLibro = datos.resultados().get(0);

            Libro libro = new Libro(primerLibro);

            Optional<Libro> libroExiste = libroRepository.findByTitulo(libro.getTitulo());
            if(libroExiste.isPresent()){
                System.out.println("\nEl libro ya está registrado");

            }else{
                if(!primerLibro.autor().isEmpty()){
                    DatosAutor autor = primerLibro.autor().get(0);
                    Autor autor1 = new Autor(autor);

                    Optional<Autor> autorOptional = autorRepository.findByNombre(autor1.getNombre());

                    if(autorOptional.isPresent()){
                        Autor autorExiste = autorOptional.get();
                        libro.setAutor(autorExiste);
                        libroRepository.save(libro);
                    }else{
                        Autor autorNuevo = autorRepository.save(autor1);
                        libro.setAutor(autorNuevo);
                        libroRepository.save(libro);
                    }

                    Integer numeroDescargas = libro.getNumeroDeDescargas() != null ? libro.getNumeroDeDescargas() : 0;
                    System.out.println("---- LIBRO -----");
                    System.out.printf("Titulo: %s%nAutor: %s%nIdioma: %s%Numero de Descargas: %s%n",
                            libro.getTitulo(),autor1.getNombre(), libro.getIdioma(), libro.getNumeroDeDescargas());
                    System.out.println("----------------");

                }else {
                    System.out.println("sin autor");
                }
            }

        }else{
            System.out.println("Libro no encontrado");
        }
    }

    private void listarAutoresRegistrados(){
        autores = autorRepository.findAll();
        autores.stream().forEach(System.out::println);
    }

    private void listarLibrosRegistrados(){
        libros = libroRepository.findAll();
        libros.stream().forEach(System.out::println);

        if(libros.isEmpty()){
            System.out.println("No hay libros registrados");

        }
    }

    private void listarAutoresVivos(){
        System.out.println("Ingrese el año que desea buscar los autores: ");
        var anio = teclado.nextInt();
        autores = autorRepository.listaAutoresVivos(anio);
        autores.stream()
                .forEach(System.out::println);
    }

    private void listarLibrosPorIdioma(){
        String menuIdioma = """
                Ingrese el idioma a buscar:
                es >> Español
                en >> Ingles
                fr >> Frances
                pt >> Portugues
                """;
        System.out.println(menuIdioma);
        String idiomaBuscado = teclado.nextLine();
        DatosIdioma idioma = null;

        switch (idiomaBuscado){
            case "es":
                idioma = DatosIdioma.valueOf("ESPAÑOL");
                break;
            case "en":
                idioma = DatosIdioma.valueOf("INGLES");
                break;
            case "fr":
                idioma = DatosIdioma.valueOf("FRANCES");
                break;
            case "pt":
                idioma = DatosIdioma.valueOf("PORTUGUES");
                break;
            default:
                System.out.println("Entrada Invalida");
                return;
        }
        buscarPorIdioma(idioma);
    }

    private void buscarPorIdioma(DatosIdioma idioma){
        libros = libroRepository.findByIdioma(idioma);
        if (libros.isEmpty()){
            System.out.println("No hay libros registrados");

        }else {
            libros.stream().forEach(System.out::println);
        }
    }

}
