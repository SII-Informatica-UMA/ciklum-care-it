package sesiones.backend.dtos;
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
    private String inicio;
    private String fin;
    private String trabajoRealizado;
    private Multimedia multimedia; //List<String>
    private String descripcion;
    private Boolean presencial;
    private DatosSalud datosSalud; //List<String>
    private Long idPlan;

}
