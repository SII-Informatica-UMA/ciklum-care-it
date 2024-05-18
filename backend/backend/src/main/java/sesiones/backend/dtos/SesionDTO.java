package sesiones.backend.dtos;
import java.sql.Timestamp;

import lombok.*;
import sesiones.backend.entities.DatosSalud;
import sesiones.backend.entities.Multimedia;

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
    private Multimedia multimedia; //List<String> //¿Hay que crear un DTO para Multimedia y Datos Salud?
    private String descripcion;
    private Boolean presencial;
    private DatosSalud datosSalud; //List<String>
    private Long idPlan;

}

