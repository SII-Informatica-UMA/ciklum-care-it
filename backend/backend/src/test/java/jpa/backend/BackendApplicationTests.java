package jpa.backend;

import sesiones.backend.BackendApplication;
import sesiones.backend.controllers.Mapper;
import sesiones.backend.dtos.SesionDTO;
import sesiones.backend.dtos.SesionNuevaDTO;
import sesiones.backend.entities.Sesion;
import sesiones.backend.exceptions.NoAutorizadoException;
import sesiones.backend.repositories.SesionRepository;
import sesiones.backend.services.SesionService;

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
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;
import org.springframework.web.util.UriBuilder;
import org.springframework.web.util.UriBuilderFactory;
import org.springframework.web.util.UriComponentsBuilder;


import java.net.URI;
import java.sql.Timestamp;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.*;      
import static org.springframework.test.web.client.response.MockRestResponseCreators.*;

@SpringBootTest(classes = BackendApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DisplayName("En el servicio de sesiones")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class BackendApplicationTests {

	@Autowired
    private TestRestTemplate restTemplate;

    @Value(value = "${local.server.port}")
    private int port;

    private String token = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxIiwiaWF0IjoxNzE2MDMzNTQ4LCJleHAiOjE3MTg2MjU1NDh9.0EMk13GFbgr5vN2Si-pJrPQemHxzfbpq0kFJYExgsSY-K6p-J-LzeEjWhSFkw4WhIoWuKZKSQkupY-Y7FKaxjg";

    @Autowired
    private SesionRepository sesionRepository;

    @Autowired
    private SesionService sesionService;

    @Autowired
    private RestTemplate restTemplateServicios;

    private MockRestServiceServer mockServer;

    @BeforeEach
    public void initializeDatabase() {
        sesionRepository.deleteAll();
        mockServer = MockRestServiceServer.createServer(restTemplateServicios);
        mockServer.expect( 
            requestTo(UriComponentsBuilder.fromUriString("http://localhost:8081/centro").build().toUri()))
            .andExpect(method(HttpMethod.GET))
            .andRespond(withStatus(HttpStatus.OK)
            .contentType(MediaType.APPLICATION_JSON)
            .body("[{\"nombre\": \"Centro 1\", \"direccion\": \"Calle Internet\", \"idCentro\": 1}]")
          );  
          
          mockServer.expect( 
            requestTo(UriComponentsBuilder.fromUriString("http://localhost:8082/cliente?centro=1").build().toUri()))
            .andExpect(method(HttpMethod.GET))
            .andRespond(withStatus(HttpStatus.OK)
            .contentType(MediaType.APPLICATION_JSON)
            .body("["
                    + "{"
                    + "\"idUsuario\": 1,"
                    + "\"telefono\": \"string\","
                    + "\"direccion\": \"string\","
                    + "\"dni\": \"string\","
                    + "\"fechaNacimiento\": \"2024-05-28\","
                    + "\"sexo\": \"HOMBRE\","
                    + "\"id\": 1"
                    + "},"
                    + "{"
                    + "\"idUsuario\": 2,"
                    + "\"telefono\": \"string\","
                    + "\"direccion\": \"string\","
                    + "\"dni\": \"string\","
                    + "\"fechaNacimiento\": \"2024-05-28\","
                    + "\"sexo\": \"HOMBRE\","
                    + "\"id\": 2"
                    + "}"
                    + "]")
          );   

          mockServer.expect( 
            requestTo(UriComponentsBuilder.fromUriString("http://localhost:8084/entrena?cliente=1").build().toUri()))
            .andExpect(method(HttpMethod.GET))
            .andRespond(withStatus(HttpStatus.OK)
            .contentType(MediaType.APPLICATION_JSON)
            .body("["
                    + "{"
                    + "\"idEntrenador\": 1,"
                    + "\"idCliente\": 1,"
                    + "\"especialidad\": \"piernas\","
                    + "\"id\": 1,"
                    + "\"planes\": ["
                    + "{"
                    + "\"fechaInicio\": \"2024-05-28\","
                    + "\"fechaFin\": \"2024-05-28\","
                    + "\"reglaRecurrencia\": \"string\","
                    + "\"idRutina\": 1,"
                    + "\"id\": 1"
                    + "}"
                    + "]"
                    + "}"
                    + "]")
          ); 

          mockServer.expect( 
            requestTo(UriComponentsBuilder.fromUriString("http://localhost:8083/entrenador?centro=1").build().toUri()))
            .andExpect(method(HttpMethod.GET))
            .andRespond(withStatus(HttpStatus.OK)
            .contentType(MediaType.APPLICATION_JSON)
            .body("["
                    + "{"
                    + "\"idUsuario\": 1,"
                    + "\"telefono\": \"string\","
                    + "\"direccion\": \"string\","
                    + "\"dni\": \"string\","
                    + "\"fechaNacimiento\": \"2024-05-28T21:51:14.178Z\","
                    + "\"fechaAlta\": \"2024-05-28T21:51:14.178Z\","
                    + "\"fechaBaja\": \"2024-05-28T21:51:14.178Z\","
                    + "\"especialidad\": \"string\","
                    + "\"titulacion\": \"string\","
                    + "\"experiencia\": \"string\","
                    + "\"observaciones\": \"string\","
                    + "\"id\": 1"
                    + "},"
                    + "{"
                    + "\"idUsuario\": 2,"
                    + "\"telefono\": \"string\","
                    + "\"direccion\": \"string\","
                    + "\"dni\": \"string\","
                    + "\"fechaNacimiento\": \"2024-05-28T21:51:14.178Z\","
                    + "\"fechaAlta\": \"2024-05-28T21:51:14.178Z\","
                    + "\"fechaBaja\": \"2024-05-28T21:51:14.178Z\","
                    + "\"especialidad\": \"string\","
                    + "\"titulacion\": \"string\","
                    + "\"experiencia\": \"string\","
                    + "\"observaciones\": \"string\","
                    + "\"id\": 2"
                    + "}"
                    + "]")
          ); 

          mockServer.expect( 
            requestTo(UriComponentsBuilder.fromUriString("http://localhost:8084/entrena?entrenador=1").build().toUri()))
            .andExpect(method(HttpMethod.GET))
            .andRespond(withStatus(HttpStatus.OK)
            .contentType(MediaType.APPLICATION_JSON)
            .body("["
                    + "{"
                    + "\"idEntrenador\": 1,"
                    + "\"idCliente\": 1,"
                    + "\"especialidad\": \"piernas\","
                    + "\"id\": 1,"
                    + "\"planes\": ["
                    + "{"
                    + "\"fechaInicio\": \"2024-05-28\","
                    + "\"fechaFin\": \"2024-05-28\","
                    + "\"reglaRecurrencia\": \"string\","
                    + "\"idRutina\": 1,"
                    + "\"id\": 1"
                    + "}"
                    + "]"
                    + "}"
                    + "]")
          );
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

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token); // Añadir token de autenticación como Bearer

        var peticion = RequestEntity.get(uri)
            .accept(MediaType.APPLICATION_JSON)
            .headers(headers)
            .build();
        return peticion;
    }

    private RequestEntity<Void> get(String scheme, String host, int port, String path,Long idPlan) {
        URI uri = uri(scheme, host, port, path);

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token); // Añadir token de autenticación como Bearer

        String urlTemplate = UriComponentsBuilder.fromHttpUrl(uri.toString())
                .queryParam("plan", idPlan)
                .encode()
                .toUriString();

        var peticion = RequestEntity.get(urlTemplate)
            .accept(MediaType.APPLICATION_JSON)
            .headers(headers)
            .build();
        return peticion;
    }

    private RequestEntity<Void> delete(String scheme, String host, int port, String path) {
        URI uri = uri(scheme, host, port, path);

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token); // Añadir token de autenticación como Bearer

        var peticion = RequestEntity.delete(uri)
            .headers(headers)
            .build();
        return peticion;
    }

    private <T> RequestEntity<T> put(String scheme, String host, int port, String path, T object) {
        URI uri = uri(scheme, host, port, path);

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token); // Añadir token de autenticación como Bearer

        var peticion = RequestEntity.put(uri)
            .contentType(MediaType.APPLICATION_JSON)
            .headers(headers)
            .body(object);
        return peticion;
    }

      private <T> RequestEntity<T> post(String scheme, String host, int port, String path, T object, Long idPlan) {
        URI uriAux = uri(scheme, host, port, path);

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token); // Añadir token de autenticación como Bearer

        String urlTemplate = UriComponentsBuilder.fromHttpUrl(uriAux.toString())
                .queryParam("plan", idPlan)
                .encode()
                .toUriString();

                var peticion = RequestEntity.post(urlTemplate)
                    .contentType(MediaType.APPLICATION_JSON)
                    .headers(headers)
                    .body(object);
                return peticion;
        }

                
    

    private void compruebaCampos(Sesion expected, Sesion actual) {	
		assertThat(actual.getInicio()).isEqualTo(expected.getInicio());
		assertThat(actual.getFin()).isEqualTo(expected.getFin());
		assertThat(actual.getTrabajoRealizado()).isEqualTo(expected.getTrabajoRealizado());
        assertThat(new ArrayList<>(actual.getMultimedia())).isEqualTo(expected.getMultimedia());
        assertThat(actual.getDescripcion()).isEqualTo(expected.getDescripcion());
        assertThat(actual.getPresencial()).isEqualTo(expected.getPresencial());
        assertThat(new ArrayList<>(actual.getDatosSalud())).isEqualTo(expected.getDatosSalud());
        assertThat(actual.getIdPlan()).isEqualTo(expected.getIdPlan());
	}

    
	@Nested
    @DisplayName("cuando la BD esta vacia")
    public class BDVacia {

		@Test
        @DisplayName("devuelve la lista de sesiones vacia")
        public void devuelveSesionesVacia() {

            var peticion = get("http","localhost",port,"/sesion",1L);

            var respuesta = restTemplate.exchange(peticion,
                new ParameterizedTypeReference<List<SesionDTO>>() {
                });

            assertThat(respuesta.getStatusCode().value()).isEqualTo(200);
            assertThat(respuesta.getBody()).isEmpty();
        }

		@Test
		@DisplayName("error al obtener una sesion concreta")
		public void errorAlObtenerSesionConcreta() {
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
                    .inicio(Timestamp.valueOf("2024-03-31 08:00:00"))
                    .fin(Timestamp.valueOf("2024-03-31 08:00:01"))
                    .trabajoRealizado("3 sentadillas")
                    .multimedia(List.of("imagen", "video"))
                    .descripcion("todo muy bien")
                    .presencial(false)
                    .datosSalud(List.of("25","32", "43"))
                    .idPlan(1L)
					.build();
            
            
			// Preparamos la petición con el sesion dentro
            var peticion = post("http","localhost",port,"/sesion",sesionNuevaDTO,1L);
			// Invocamos al servicio REST 
			//var respuesta = restTemplate.exchange(peticion,Void.class);
            var respuesta = restTemplate.exchange(peticion,
                new ParameterizedTypeReference<SesionDTO>() {
                });

			// Comprobamos el resultado
			assertThat(respuesta.getStatusCode().value()).isEqualTo(201);
			assertThat(respuesta.getHeaders().get("Location").get(0))
			.startsWith("http://localhost:"+port+"/sesion/1");

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
                new ParameterizedTypeReference<SesionDTO>() {
                });

            assertThat(respuesta.getStatusCode().value()).isEqualTo(404);
        }

        @Test
		@DisplayName("devuelve error al editar una sesión inexistente")
		public void errorActualizaSesionInexistente() {
			
			// Preparamos la sesión a actualizar
			var alum = SesionDTO.builder()
									.inicio(Timestamp.valueOf("2024-03-30 08:00:00"))
                                    .idPlan(1L)
									.build();
			// Preparamos la petición con la sesión dentro
			var peticion = put("http", "localhost",port, "/sesion/1", alum);
			
			// Invocamos al servicio REST 
			var respuesta = restTemplate.exchange(peticion,Void.class);
			
			// Comprobamos el resultado
			assertThat(respuesta.getStatusCode().value()).isEqualTo(404);
		}

        @Test
		@DisplayName("devuelve error al editar una sesión con idplan nulo")
		public void actualizaSesionSinIdPlan() {
			
			// Preparamos la sesión a actualizar
			var alum = SesionDTO.builder()
									.inicio(Timestamp.valueOf("2024-03-30 08:00:00"))
									.build();
			// Preparamos la petición con la sesión dentro
			var peticion = put("http", "localhost",port, "/sesion/1", alum);
			
			// Invocamos al servicio REST 
			var respuesta = restTemplate.exchange(peticion,Void.class);
			
			// Comprobamos el resultado
			assertThat(respuesta.getStatusCode().value()).isEqualTo(403);
		}

        @Test
		@DisplayName("devuelve error al editar una sesión con un idplan al que no se tiene permiso")
		public void actualizaSesionIdPlanNoAutorizado() {
			
			// Preparamos la sesión a actualizar
			var alum = SesionDTO.builder()
									.inicio(Timestamp.valueOf("2024-03-30 08:00:00"))
                                    .idPlan(2L)
									.build();
			// Preparamos la petición con la sesión dentro
			var peticion = put("http", "localhost",port, "/sesion/1", alum);
			
			// Invocamos al servicio REST 
			var respuesta = restTemplate.exchange(peticion,Void.class);
			
			// Comprobamos el resultado
			assertThat(respuesta.getStatusCode().value()).isEqualTo(403);
		}

	}
    


	@Nested
    @DisplayName("cuando la BD tiene datos")
    public class BDConDatos {

		@BeforeEach
		public void insertarDatos() {
			var sesion1 = new Sesion();
			sesion1.setDescripcion("Piernas");
            sesion1.setIdPlan((long) 1);

			var sesion2 = new Sesion();
			sesion2.setDescripcion("Pecho");
            sesion2.setInicio(Timestamp.valueOf("2024-03-30 08:00:00"));
            sesion2.setIdPlan((long) 1);

			sesionRepository.save(sesion1);
			sesionRepository.save(sesion2);

            var sesion3= new Sesion();
			sesion3.setDescripcion("hombros");
            sesion3.setIdPlan((long) 2);

            sesionRepository.save(sesion3);
		}

		@Test
		@DisplayName("devuelve una lista de sesiones")
		public void devuelveListaSesiones() {
            var peticion = get("http","localhost",port,"/sesion",1L);

			var respuesta = restTemplate.exchange(peticion,
					new ParameterizedTypeReference<List<SesionDTO>>() {});

			assertThat(respuesta.getStatusCode().value()).isEqualTo(200);
			assertThat(respuesta.getBody()).hasSize(2);
		}

        @Test
		@DisplayName("devuelve error al obtener lista de sesiones en la que no se tiene permiso al plan")
		public void errorListaSesionesPlanDistinto() {
            var peticion = get("http","localhost",port,"/sesion",2L);

			var respuesta = restTemplate.exchange(peticion,
					new ParameterizedTypeReference<List<SesionDTO>>() {});

			assertThat(respuesta.getStatusCode().value()).isEqualTo(403);
		}

        @Test
		@DisplayName("obtiene una sesion concreta")
		public void obtenerSesionConcreta() {
			var peticion = get("http", "localhost",port, "/sesion/2");

			var respuesta = restTemplate.exchange(peticion,
					new ParameterizedTypeReference<SesionDTO>() {});

			assertThat(respuesta.getStatusCode().value()).isEqualTo(200);
			assertThat(respuesta.getBody().getDescripcion()).isEqualTo("Pecho");
		}

        @Test
		@DisplayName("devuelve error al obtener una sesion concreta en la que no se tiene permiso al plan")
		public void errorSesionPlanDistinto() {
            var peticion = get("http","localhost",port,"/sesion/3");

			var respuesta = restTemplate.exchange(peticion,
					new ParameterizedTypeReference<SesionDTO>() {});

			assertThat(respuesta.getStatusCode().value()).isEqualTo(403);
		}


        @Test
        @DisplayName("Borra correctamente una sesion")
        public void borraSesion() {

            var peticion = delete("http", "localhost", port, "/sesion/1");

            var respuesta = restTemplate.exchange(peticion,
                new ParameterizedTypeReference<SesionDTO>() {
                });

            assertThat(respuesta.getStatusCode().value()).isEqualTo(200);

            var peticionComprobacion = get("http", "localhost", port, "/sesion/1");

            var respuestaComprobacion = restTemplate.exchange(peticionComprobacion,
                new ParameterizedTypeReference<SesionDTO>() {
                });

            assertThat(respuestaComprobacion.getStatusCode().value()).isEqualTo(404);
        }

        @Test
		@DisplayName("edita una sesión existente")
		public void actualizaSesion() {

			// Preparamos la sesión a actualizar
			var sesion = SesionDTO.builder()
									.descripcion("Pecho")
                                    .inicio(Timestamp.valueOf("2024-03-31 08:00:00"))
                                    .idPlan(1L)
									.build();
			//Buscamos la sesión en la bbdd para obtener el id y preparamos la petición
			Sesion sesionExist = sesionRepository.findByInicio(Timestamp.valueOf("2024-03-30 08:00:00")).get(0);
			var peticion = put("http", "localhost",port, "/sesion/"+sesionExist.getId(),sesion);
			
			// Invocamos al servicio REST 
			var respuesta = restTemplate.exchange(peticion,Void.class);
			
			// Comprobamos el resultado
			assertThat(respuesta.getStatusCode().value()).isEqualTo(200);
			var listaSesiones = sesionRepository.findByInicio(Timestamp.valueOf("2024-03-31 08:00:00"));
			assertThat(listaSesiones).hasSize(1);
            assertThat(listaSesiones.get(0).getInicio()).isEqualTo(Timestamp.valueOf("2024-03-31 08:00:00"));
		}

        @Test
		@DisplayName("edita una sesión existente con idPlan incorrecto")
		public void actualizaSesionidPlanIncorrecto() {
            var sesion3 = new Sesion();
			sesion3.setDescripcion("Hombros");
            sesion3.setIdPlan((long) 2);
            sesion3.setInicio(Timestamp.valueOf("2024-02-30 08:00:00"));
            sesionRepository.save(sesion3);

			// Preparamos la sesión a actualizar
			var sesion = SesionDTO.builder()
									.descripcion("Hombros")
                                    .inicio(Timestamp.valueOf("2024-02-30 08:00:00"))
                                    .idPlan(1L)
									.build();
			//Buscamos la sesión en la bbdd para obtener el id y preparamos la petición
			Sesion sesionExist = sesionRepository.findByInicio(Timestamp.valueOf("2024-02-30 08:00:00")).get(0);
			var peticion = put("http", "localhost",port, "/sesion/"+sesionExist.getId(),sesion);
			
			// Invocamos al servicio REST 
			var respuesta = restTemplate.exchange(peticion,Void.class);
			
			// Comprobamos el resultado
			assertThat(respuesta.getStatusCode().value()).isEqualTo(404);
		}

        @Test
		@DisplayName("devuelve error al eliminar una sesión en la que no se tiene permiso al plan")
		public void eliminaSesionNoAutorizada() {
			
			// Preparamos la petición con la sesión dentro
			var peticion = delete("http", "localhost",port, "/sesion/3");
			
			// Invocamos al servicio REST 
			var respuesta = restTemplate.exchange(peticion,Void.class);
			
			// Comprobamos el resultado
			assertThat(respuesta.getStatusCode().value()).isEqualTo(403);
		}

	}

    @Nested
    @DisplayName("independientes de la BD")
    public class independienteDeBD {
        @DisplayName("el Mapper es instanciado correctamente")
        @Test
        public void instanciarMapper() {
            Mapper mapper = new Mapper();
            assertNotNull(mapper);
        }
    
   

        @Test
		@DisplayName("devuelve error al insertar sesion no asociada a plan")
		public void errorinsertasesion() {

			// Preparamos el sesion a insertar
			var sesionNuevaDTO = SesionNuevaDTO.builder()
                    .inicio(Timestamp.valueOf("2024-03-31 08:00:00"))
                    .fin(Timestamp.valueOf("2024-03-31 08:00:01"))
                    .trabajoRealizado("3 sentadillas")
                    .multimedia(List.of("imagen", "video"))
                    .descripcion("todo muy bien")
                    .presencial(false)
                    .datosSalud(List.of("25","32", "43"))
                    .idPlan(null)
					.build();
            
            
			// Preparamos la petición con el sesion dentro
            var peticion = post("http","localhost",port,"/sesion",sesionNuevaDTO, null);
			// Invocamos al servicio REST 
			//var respuesta = restTemplate.exchange(peticion,Void.class);
            var respuesta = restTemplate.exchange(peticion,
                new ParameterizedTypeReference<SesionDTO>() {
                });

			// Comprobamos el resultado
			assertThat(respuesta.getStatusCode().value()).isEqualTo(403);
			List<Sesion> sesionesBD = sesionRepository.findAll();
			assertThat(sesionesBD).hasSize(0);
			
		}


        @Test
		@DisplayName("servicio lanza excepcion al guardar una sesion no asociada a plan")
		public void errorGuardaSesionServicio() {

			// Preparamos el sesion a insertar
			var sesionNuevaDTO = SesionNuevaDTO.builder()
                    .inicio(Timestamp.valueOf("2024-03-31 08:00:00"))
                    .fin(Timestamp.valueOf("2024-03-31 08:00:01"))
                    .trabajoRealizado("3 sentadillas")
                    .multimedia(List.of("imagen", "video"))
                    .descripcion("todo muy bien")
                    .presencial(false)
                    .datosSalud(List.of("25","32", "43"))
                    .idPlan(null)
					.build();
                    Sesion sesionNueva = Mapper.toSesion(sesionNuevaDTO);
            
            
			

			// Comprobamos el resultado
			assertThrows(NoAutorizadoException.class, () -> sesionService.aniadirSesion(sesionNueva));
		
			
		}
    }

}
