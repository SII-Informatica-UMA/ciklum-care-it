package jpa.backend;

import sesiones.backend.BackendApplication;
import sesiones.backend.controllers.Mapper;
import sesiones.backend.dtos.SesionDTO;
import sesiones.backend.dtos.SesionNuevaDTO;
import sesiones.backend.entities.DatosSalud;
import sesiones.backend.entities.Multimedia;
import sesiones.backend.entities.Sesion;
import sesiones.backend.repositories.SesionRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.web.util.DefaultUriBuilderFactory;
import org.springframework.web.util.UriBuilder;
import org.springframework.web.util.UriBuilderFactory;
import java.net.URI;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = BackendApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DisplayName("En el servicio de niveles y grupos")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class BackendApplicationTests {

	@Autowired
    private TestRestTemplate restTemplate;

    @Value(value = "${local.server.port}")
    private int port;

    @Autowired
    private SesionRepository sesionRepository;

    @BeforeEach
    public void initializeDatabase() {
        sesionRepository.deleteAll();
    }

    private URI uri(String scheme, String host, int port, String... paths) {
        UriBuilderFactory ubf = new DefaultUriBuilderFactory();
        UriBuilder ub = ubf.builder()
            .scheme(scheme)
            .host(host).port(port);
        for (String path : paths) {
            ub = ub.path(path);
        }
        return ub.build();
    }

    private RequestEntity<Void> get(String scheme, String host, int port, String path) {
        URI uri = uri(scheme, host, port, path);
        var peticion = RequestEntity.get(uri)
            .accept(MediaType.APPLICATION_JSON)
            .build();
        return peticion;
    }

    private RequestEntity<Void> delete(String scheme, String host, int port, String path) {
        URI uri = uri(scheme, host, port, path);
        var peticion = RequestEntity.delete(uri)
            .build();
        return peticion;
    }

    private <T> RequestEntity<T> post(String scheme, String host, int port, String path, T object) {
        URI uri = uri(scheme, host, port, path);
        var peticion = RequestEntity.post(uri)
            .contentType(MediaType.APPLICATION_JSON)
            .body(object);
        return peticion;
    }

    private <T> RequestEntity<T> put(String scheme, String host, int port, String path, T object) {
        URI uri = uri(scheme, host, port, path);
        var peticion = RequestEntity.put(uri)
            .contentType(MediaType.APPLICATION_JSON)
            .body(object);
        return peticion;
    }

    private void compruebaCampos(Sesion expected, Sesion actual) {	
		assertThat(actual.getInicio()).isEqualTo(expected.getInicio());
		assertThat(actual.getFin()).isEqualTo(expected.getFin());
		assertThat(actual.getTrabajoRealizado()).isEqualTo(expected.getTrabajoRealizado());
        assertThat(actual.getMultimedia()).isEqualTo(expected.getMultimedia());
        assertThat(actual.getDescripcion()).isEqualTo(expected.getDescripcion());
        assertThat(actual.getPresencial()).isEqualTo(expected.getPresencial());
        assertThat(actual.getDatosSalud()).isEqualTo(expected.getDatosSalud());
        assertThat(actual.getIdPlan()).isEqualTo(expected.getIdPlan());
	}

    
	@Nested
    @DisplayName("cuando la BD esta vacia")
    public class BDVacia {

		@Test
        @DisplayName("devuelve la lista de sesiones vacia")
        public void devuelveSesionesVacia() {

            var peticion = get("http", "localhost", port, "/sesion");

            var respuesta = restTemplate.exchange(peticion,
                new ParameterizedTypeReference<List<Sesion>>() {
                });

            assertThat(respuesta.getStatusCode().value()).isEqualTo(200);
            assertThat(respuesta.getBody()).isEmpty();
        }

		@Test
		@DisplayName("error al obtener un nivel educativo concreto")
		public void errorAlObtenerNivelConcreto() {
			var peticion = get("http", "localhost",port, "/sesion/1");

			var respuesta = restTemplate.exchange(peticion,
					new ParameterizedTypeReference<SesionDTO>() {});

			assertThat(respuesta.getStatusCode().value()).isEqualTo(404);
		}

        
		@Test
		@DisplayName("inserta correctamente un sesion")
		public void insertasesion() {

			// Preparamos el sesion a insertar
			var sesionNuevaDTO = SesionNuevaDTO.builder()
                    .inicio("")
                    .fin("")
                    .trabajoRealizado("3 sentadillas")
                    .multimedia(new Multimedia("imagen", "video"))
                    .descripcion("todo muy bien")
                    .presencial(false)
                    .datosSalud(new DatosSalud(25L,32L, 43L))
                    .idPlan(1L)
					.build();
            
            
			// Preparamos la petición con el sesion dentro
			var peticion = post("http", "localhost",port, "/sesiones", sesionNuevaDTO);

			// Invocamos al servicio REST 
			var respuesta = restTemplate.exchange(peticion,Void.class);

			// Comprobamos el resultado
			assertThat(respuesta.getStatusCode().value()).isEqualTo(201);
			assertThat(respuesta.getHeaders().get("Location").get(0))
			.startsWith("http://localhost:"+port+"/sesiones");

			List<Sesion> sesionesBD = sesionRepository.findAll();
			assertThat(sesionesBD).hasSize(1);
			assertThat(respuesta.getHeaders().get("Location").get(0))
			.endsWith("/"+sesionesBD.get(0).getId());
			compruebaCampos(Mapper.toSesion(sesionNuevaDTO), sesionesBD.get(0));
		}

        @Test
        @DisplayName("error al eliminar una sesión inexistente")
        public void errorEliminarSinSesiones() {

            var peticion = delete("http", "localhost", port, "/sesion/1");

            var respuesta = restTemplate.exchange(peticion,
                new ParameterizedTypeReference<Sesion>() {
                });

            assertThat(respuesta.getStatusCode().value()).isEqualTo(404);
        }

	}
    


	@Nested
    @DisplayName("cuando la BD tiene datos")
    public class BDConDatos {

		@BeforeEach
		public void insertarDatos() {
			var sesion1 = new Sesion();
			sesion1.setId((long) 1);

			var sesion2 = new Sesion();
			sesion1.setId((long) 7);

			sesionRepository.save(sesion1);
			sesionRepository.save(sesion2);
		}

		@Test
		@DisplayName("devuelve una lista de sesiones")
		public void devuelveListaSesiones() {
			var peticion = get("http", "localhost",port, "/sesion");

			var respuesta = restTemplate.exchange(peticion,
					new ParameterizedTypeReference<List<Sesion>>() {});

			assertThat(respuesta.getStatusCode().value()).isEqualTo(200);
			assertThat(respuesta.getBody().size()).isEqualTo(2);
		}

		@Test
		@DisplayName("obtiene una sesion concreta")
		public void obtenerSesionConcreta() {
			var peticion = get("http", "localhost",port, "/sesion/2");

			var respuesta = restTemplate.exchange(peticion,
					new ParameterizedTypeReference<Sesion>() {});

			assertThat(respuesta.getStatusCode().value()).isEqualTo(200);
			assertThat(respuesta.getBody().getId()).isEqualTo((long) 7);
		}

        @Test
        @DisplayName("Borra correctamente una sesion")
        public void borraSesion() {

            var peticion = delete("http", "localhost", port, "/sesion/1");

            var respuesta = restTemplate.exchange(peticion,
                new ParameterizedTypeReference<Sesion>() {
                });

            assertThat(respuesta.getStatusCode().value()).isEqualTo(200);
        }

	}

}
