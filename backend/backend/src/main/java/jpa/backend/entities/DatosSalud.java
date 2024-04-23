package jpa.backend.entities;

import jakarta.persistence.Embeddable;

@Embeddable
public class DatosSalud {
    private Long pulsaciones;
    private Long peso;
    private Long calorias;

    public Long getPulsaciones() {
        return pulsaciones;
    }

    public void setPulsaciones(Long pulsaciones) {
        this.pulsaciones = pulsaciones;
    }

    public Long getPeso() {
        return peso;
    }

    public void setPeso(Long peso) {
        this.peso = peso;
    }

    public Long getCalorias() {
        return calorias;
    }

    public void setCalorias(Long calorias) {
        this.calorias = calorias;
    }

    @Override
    public String toString() {
        return "DatosSalud{" + "pulsaciones=" + pulsaciones + ", peso=" + peso + ", calorias=" + calorias + '}';
    }
}

