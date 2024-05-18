package sesiones.backend.entities;

import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Sesion {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(length = 50)
    private Timestamp inicio;
    @Column(length = 50)
    private Timestamp fin;

    @Column(length = 4000)
    private String trabajoRealizado;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<String>  multimedia;

    @Column(length = 4000)
    private String descripcion;
    private Boolean presencial;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<String>  datosSalud;

    private Long idPlan;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Sesion sesion = (Sesion) o;
        return this.id == sesion.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, idPlan);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdPlan() {
        return idPlan;
    }

    public void setIdPlan(Long idPlan) {
        this.idPlan = idPlan;
    }

    public Timestamp getInicio() {
        return inicio;
    }

    public void setInicio(Timestamp inicio) {
        this.inicio = inicio;
    }

    public Timestamp getFin() {
        return fin;
    }

    public void setFin(Timestamp fin) {
        this.fin = fin;
    }

    public String getTrabajoRealizado() {
        return trabajoRealizado;
    }

    public void setTrabajoRealizado(String trabajoRealizado) {
        this.trabajoRealizado = trabajoRealizado;
    }

    public List<String>  getMultimedia() {
        return multimedia;
    }

    public void setMultimedia(List<String>  multimedia) {
        this.multimedia = multimedia;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Boolean getPresencial() {
        return presencial;
    }

    public void setPresencial(Boolean presencial) {
        this.presencial = presencial;
    }

    public List<String>  getDatosSalud() {
        return datosSalud;
    }

    public void setDatosSalud(List<String>  datosSalud) {
        this.datosSalud = datosSalud;
    }

    @Override
    public String toString(){
        return "{id: "+id+", inicio: "+inicio+", fin: "+fin+", trabajoRealizado: "+trabajoRealizado+", multimedia: "+multimedia.toString()
        +", descripcion: "+descripcion+", presencial: "+presencial+", datos de salud: "+datosSalud.toString()+"}";
    }
}