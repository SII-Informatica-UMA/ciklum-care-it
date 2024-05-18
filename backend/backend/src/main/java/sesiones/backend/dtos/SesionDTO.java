package sesiones.backend.dtos;
import java.sql.Timestamp;
import java.util.List;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode
@ToString
public class SesionDTO {
    private Long id;
    private Timestamp inicio;
    private Timestamp fin;
    private String trabajoRealizado;
    private List<String>  multimedia; //List<String> //¿Hay que crear un DTO para Multimedia y Datos Salud?
    private String descripcion;
    private Boolean presencial;
    private List<String>  datosSalud; //List<String>
    private Long idPlan;

}

