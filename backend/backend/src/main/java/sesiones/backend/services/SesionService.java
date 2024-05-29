package sesiones.backend.services;

import java.net.URI;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
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
import sesiones.backend.exceptions.NoAutorizadoException;
import sesiones.backend.exceptions.SesionInexistenteException;
import sesiones.backend.repositories.SesionRepository;
import sesiones.backend.security.JwtUtil;
import sesiones.backend.security.SecurityConfguration;

@Service
public class SesionService {
    private SesionRepository repoSesion;
    private final Logger log =Logger.getLogger(SesionService.class.getName());
    private final JwtUtil jwtUtil;

    private final RestTemplate restTemplate;

    private final int puertoCentro=8081;
    private final int puertoCliente=8082;
    private final int puertoEntrenador=8083;
    private final int puertoEntrena=8084;

    @Autowired
    public SesionService(SesionRepository repo, JwtUtil jwtUtil, RestTemplate restTemplate) {
        this.repoSesion=repo;
        this.jwtUtil = jwtUtil;
        this.restTemplate = restTemplate;
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
        this.comprobarSeguridad(idPlan);

        return repoSesion.findByIdPlan(idPlan);
    }

    public Sesion aniadirSesion(Sesion sesion) {
        comprobarSeguridad(sesion.getIdPlan());
        return this.repoSesion.save(sesion);
    }

    public Optional<Sesion> obtenerSesion(Long id) {
		Optional<Sesion> s = repoSesion.findById(id);
		if (s.isPresent()){
            this.comprobarSeguridad(s.get().getIdPlan());

            return s;
		} else {
			throw new SesionInexistenteException();
		}
	}

    public Sesion editarSesion(Sesion sesion) {
        comprobarSeguridad(sesion.getIdPlan());

        if(!repoSesion.existsById(sesion.getId()) || !repoSesion.findById(sesion.getId()).get().getIdPlan().equals(sesion.getIdPlan())){
            throw new SesionInexistenteException();//Si pide una sesión que existe pero no está asociado al plan del usuario, devolvemos que no existe. El usuario no tiene por qué saber que existe.
        }else{
            return this.repoSesion.save(sesion);
        }
    }

    public void deleteSesion(Long id){
        Optional<Sesion> sesion = repoSesion.findById(id);

        if(sesion.isPresent()){
            this.comprobarSeguridad(sesion.get().getIdPlan());
            repoSesion.deleteById(id);
        }else{
            throw new SesionInexistenteException();
        }
    }



    private void comprobarSeguridad(Long idPlan){
        //Inicio comprobación seguridad
        if(idPlan==null){
            throw new NoAutorizadoException();
        }

        Set<Long> planesAsociados = new HashSet<>();

        Optional<UserDetails> user = SecurityConfguration.getAuthenticatedUser();
        user.ifPresent(u -> log.info("Usuario autenticado: " + u.getUsername()));
        String token = jwtUtil.generateToken(user.get());
        user.ifPresent(u -> log.info("token: "+token));

        //microservicio centrogerente ejecutado en el puerto 8081 con --server.port=8081
        var peticionCentros = get("http","localhost",puertoCentro,"/centro",token);
        restTemplate.exchange(peticionCentros,
        new ParameterizedTypeReference<List<Map<String, Object>>>() {
        }).getBody().stream()
            .forEach(centro -> {
                Long idCentro = Long.parseLong(centro.get("idCentro").toString());
                var peticionClientes = get("http","localhost",puertoCliente,"/cliente",token, "centro", idCentro);
                restTemplate.exchange(peticionClientes,
                new ParameterizedTypeReference<List<Map<String, Object>>>(){
                }).getBody().stream().forEach(cliente -> {
                    if(Long.valueOf(cliente.get("idUsuario").toString()).equals(Long.valueOf(user.get().getUsername()))){
                        Long idCliente = Long.parseLong(cliente.get("id").toString());
                        var peticionEntrena = get("http","localhost",puertoEntrena,"/entrena",token, "cliente", idCliente);
                        restTemplate.exchange(peticionEntrena, new ParameterizedTypeReference<List<Map<String,Object>>>() {           
                        }).getBody().stream().forEach(entrena -> {
                            @SuppressWarnings("unchecked")
                            List<Map<String, Object>> planes = (List<Map<String, Object>>) entrena.get("planes");
                            planes.stream().forEach(plan -> {
                                planesAsociados.add(Long.valueOf(plan.get("id").toString()));
                            }
                            );
                        } 
                        );
                    }
                }
                );

                var peticionEntrenadores = get("http","localhost",puertoEntrenador,"/entrenador",token, "centro", idCentro);
                restTemplate.exchange(peticionEntrenadores,
                new ParameterizedTypeReference<List<Map<String, Object>>>(){
                }).getBody().stream().forEach(entrenador -> {
                    if(Long.valueOf(entrenador.get("idUsuario").toString()).equals(Long.valueOf(user.get().getUsername()))){
                        Long idEntrenador = Long.parseLong(entrenador.get("id").toString());
                        var peticionEntrena = get("http","localhost",puertoEntrena,"/entrena",token, "entrenador", idEntrenador);
                        restTemplate.exchange(peticionEntrena, new ParameterizedTypeReference<List<Map<String,Object>>>() {  
                        }).getBody().stream().forEach(entrena -> {
                            @SuppressWarnings("unchecked")
                            List<Map<String, Object>> planes = (List<Map<String, Object>>) entrena.get("planes");
                            planes.stream().forEach(plan -> {
                                planesAsociados.add(Long.valueOf(plan.get("id").toString()));
                            }
                            );
                        } 
                        );
                    }
                }
                );
            }
        );

        if(!planesAsociados.contains(idPlan)){
            throw new NoAutorizadoException();
        }

        //Fin comprobación seguridad

    }
}

