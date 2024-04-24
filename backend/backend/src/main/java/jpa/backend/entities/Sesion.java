package jpa.backend.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import jakarta.persistence.*;

@Entity
public class Sesion {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String inicio;
    private String fin;
    private String trabajoRealizado;

    @Embedded
    private Multimedia multimedia;

    private String descripcion;
    private Boolean presencial;

    @Embedded
    private DatosSalud datosSalud;

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

    public String getInicio() {
        return inicio;
    }

    public void setInicio(String inicio) {
        this.inicio = inicio;
    }

    public String getFin() {
        return fin;
    }

    public void setFin(String fin) {
        this.fin = fin;
    }

    public String getTrabajoRealizado() {
        return trabajoRealizado;
    }

    public void setTrabajoRealizado(String trabajoRealizado) {
        this.trabajoRealizado = trabajoRealizado;
    }

    public Multimedia getMultimedia() {
        return multimedia;
    }

    public void setMultimedia(Multimedia multimedia) {
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

    public DatosSalud getDatosSalud() {
        return datosSalud;
    }

    public void setDatosSalud(DatosSalud datosSalud) {
        this.datosSalud = datosSalud;
    }

    @Override
    public String toString(){
        return "{id: "+id+", inicio: "+inicio+", fin: "+fin+", trabajoRealizado: "+trabajoRealizado+", multimedia: "+multimedia.toString()
        +", descripcion: "+descripcion+", presencial: "+presencial+", datos de salud: "+datosSalud.toString()+"}";
    }
}