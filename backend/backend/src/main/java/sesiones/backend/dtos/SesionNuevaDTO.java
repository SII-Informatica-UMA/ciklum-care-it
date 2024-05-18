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
public class SesionNuevaDTO {
    private Timestamp inicio;
    private Timestamp fin;
    private String trabajoRealizado;
    private Multimedia multimedia; //List<String>
    private String descripcion;
    private Boolean presencial;
    private DatosSalud datosSalud; //List<String>
    private Long idPlan;

}
