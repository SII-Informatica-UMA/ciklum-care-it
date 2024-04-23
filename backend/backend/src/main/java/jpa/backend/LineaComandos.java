package jpa.backend;

import java.io.File;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import jpa.backend.entities.Sesion;
import jpa.backend.repositories.SesionRepository;

@Component
public class LineaComandos implements CommandLineRunner {
	private SesionRepository repository;
	public LineaComandos(SesionRepository repository) {
		this.repository = repository;
	}

	@Override
	@Transactional
	public void run(String... args) throws Exception {

		File ficheroInstrucciones = new File("jpa/backend/script.sh");
		ficheroInstrucciones.canWrite();
		ficheroInstrucciones.
	}

}
