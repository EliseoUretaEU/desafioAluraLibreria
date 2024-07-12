package com.desafio_libreria.libreriaAlura;

import com.desafio_libreria.libreriaAlura.principal.Principal;
import com.desafio_libreria.libreriaAlura.repository.AutorRepository;
import com.desafio_libreria.libreriaAlura.repository.LibroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LibreriaAluraApplication implements CommandLineRunner {

	@Autowired
	private AutorRepository autorRepository;

	@Autowired
	private LibroRepository libroRepository;

	public static void main(String[] args) {
		SpringApplication.run(LibreriaAluraApplication.class, args);
	}


	@Override
	public void run(String... args) throws Exception {

		Principal principal = new Principal(libroRepository,autorRepository);
		principal.mostrarMenuOpciones();
	}
}
