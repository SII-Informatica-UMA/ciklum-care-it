package sesiones.backend;

import java.sql.Timestamp;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import sesiones.backend.entities.Sesion;
import sesiones.backend.repositories.SesionRepository;

@Component
public class LineaComandos implements CommandLineRunner {
	private SesionRepository repository;
	public LineaComandos(SesionRepository repository) {
		this.repository = repository;
	}

	@Override
	@Transactional
	public void run(String... args) throws Exception {

		for (String s: args) {
			System.out.println(s);
		}

		if (args.length > 0) {
			for (Sesion s: repository.findByInicio(Timestamp.valueOf(args[0]))) {
				System.out.println(s);
			}
		}
	}

}
