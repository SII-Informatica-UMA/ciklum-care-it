package sesiones.backend.services;

import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;
import org.springframework.web.util.UriBuilder;
import org.springframework.web.util.UriBuilderFactory;
import org.springframework.web.util.UriComponentsBuilder;

import sesiones.backend.entities.Sesion;
import sesiones.backend.exceptions.SesionInexistenteException;
import sesiones.backend.exceptions.SesionNoAsociadaException;
import sesiones.backend.repositories.SesionRepository;
import sesiones.backend.security.JwtUtil;
import sesiones.backend.security.SecurityConfguration;

@Service
public class SesionService {
    private SesionRepository repoSesion;
    private final Logger log =Logger.getLogger(SesionService.class.getName());
    private final JwtUtil jwtUtil;

    private final RestTemplate restTemplate = new RestTemplate();

    @Autowired
    public SesionService(SesionRepository repo, JwtUtil jwtUtil) {
        this.repoSesion=repo;
        this.jwtUtil = jwtUtil;
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

        private RequestEntity<Void> get(String scheme, String host, int port, String path, String token) {
        URI uri = uri(scheme, host, port, path);

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token); // Añadir token de autenticación como Bearer

        var peticion = RequestEntity.get(uri)
            .accept(MediaType.APPLICATION_JSON)
            .headers(headers)
            .build();
        return peticion;
    }

    private RequestEntity<Void> get(String scheme, String host, int port, String path, String token, String parametro, Long valor_parametro) {
        URI uri = uri(scheme, host, port, path);

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token); // Añadir token de autenticación como Bearer

        String urlTemplate = UriComponentsBuilder.fromHttpUrl(uri.toString())
                .queryParam(parametro, valor_parametro)
                .encode()
                .toUriString();

        var peticion = RequestEntity.get(urlTemplate)
            .accept(MediaType.APPLICATION_JSON)
            .headers(headers)
            .build();
        return peticion;
    }


    public List<Sesion> getSesiones(Long idPlan){
        return repoSesion.findByIdPlan(idPlan);
    }

    public Sesion aniadirSesion(Sesion sesion) {
        if(sesion.getIdPlan() == null){
            throw new SesionNoAsociadaException();
        }
        return this.repoSesion.save(sesion);
    }

    public Optional<Sesion> obtenerSesion(Long id) {
        Optional<UserDetails> user = SecurityConfguration.getAuthenticatedUser();
        user.ifPresent(u -> log.info("Usuario autenticado: " + u.getUsername()));
        String token = jwtUtil.generateToken(user.get());
        user.ifPresent(u -> log.info("token: "+token));

        //microservicio centrogerente ejecutado en el puerto 8081 con --server.port=8081
        var peticionCentros = get("http","localhost",8081,"/centro",token);

        var respuestaCentros = restTemplate.exchange(peticionCentros,
        new ParameterizedTypeReference<List<Map<String, Object>>>() {
        });

        log.info("Respuesta: " + respuestaCentros.getStatusCode());
        if(respuestaCentros.getStatusCode().value()==200){
            for(Map<String,Object> centro : respuestaCentros.getBody()){
                log.info("Centro " + centro.get("idCentro").toString());

                Long idCentro =  Long.parseLong(centro.get("idCentro").toString());

                //ahora vemos los clientes y entrenamientos de cada uno de los centros

                //microservicio clientes en 8082 con --server.port=8082
                var peticionClientes = get("http","localhost",8082,"/cliente",token, "centro", idCentro);

                 var respuestaClientes = restTemplate.exchange(peticionClientes,
                new ParameterizedTypeReference<List<Map<String, Object>>>() {
                });

                log.info("Respuesta: " + respuestaClientes.getStatusCode());

                for(Map<String, Object> cliente: respuestaClientes.getBody()){
                    log.info("Cliente " + cliente.get("idUsuario").toString());
                }
            }
        }
        

		Optional<Sesion> s = repoSesion.findById(id);
		if (s.isPresent()){
			return s;
		} else {
			throw new SesionInexistenteException();
		}
	}

    public Sesion editarSesion(Sesion sesion) {
        if(!repoSesion.existsById(sesion.getId())){
            throw new SesionInexistenteException();
        }else{
            return this.repoSesion.save(sesion);
        }
    }

    public void deleteSesion(Long id){
        var sesion = repoSesion.findById(id);

        if(sesion.isPresent()){
            repoSesion.deleteById(id);
        }else{
            throw new SesionInexistenteException();
        }
    }

}
