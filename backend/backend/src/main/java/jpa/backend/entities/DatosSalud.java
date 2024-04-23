package jpa.backend.entities;

import jakarta.persistence.Embeddable;

@Embeddable
public class DatosSalud {
    private Long pulsaciones;
    private Long peso;
    private Long calorias;
}
