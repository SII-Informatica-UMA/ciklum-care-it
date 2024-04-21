package jpa.backend;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import jakarta.persistence.*;


public class Plan {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long idRutina;
    private String reglaRecurrencia;
    private Date fechaInicio;
    private Date fechaFin;

    @OneToMany(mappedBy = "plan")
    private List<Sesion> sesiones;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Plan plan = (Plan) o;
        return Objects.equals(id, plan.id) && Objects.equals(idRutina, plan.idRutina);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, idRutina);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdPlan() {
        return idRutina;
    }

    public void setIdRutina(Long idRutina) {
        this.idRutina = idRutina;
    }

    public String getReglaRecurrencia() {
        return reglaRecurrencia;
    }

    public void setReglaRecurrencia(String reglaRecurrencia) {
        this.reglaRecurrencia = reglaRecurrencia;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public List<Sesion> getSesiones() {
        return sesiones;
    }

    public void setSesiones(List<Sesion> sesiones) {
        this.sesiones = sesiones;
    }
}
