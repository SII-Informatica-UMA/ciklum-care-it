package jpa.backend.entities;
import jakarta.persistence.*;

@Embeddable
public class Multimedia {
    private String imagen;
    private String video;
}
