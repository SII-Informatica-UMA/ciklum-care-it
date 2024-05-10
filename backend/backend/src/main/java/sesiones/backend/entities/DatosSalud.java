package sesiones.backend.entities;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
@AllArgsConstructor
@NoArgsConstructor
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
        return "{pulsaciones=" + pulsaciones + ", peso=" + peso + ", calorias=" + calorias + '}';
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((pulsaciones == null) ? 0 : pulsaciones.hashCode());
        result = prime * result + ((peso == null) ? 0 : peso.hashCode());
        result = prime * result + ((calorias == null) ? 0 : calorias.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        DatosSalud other = (DatosSalud) obj;
        if (pulsaciones == null) {
            if (other.pulsaciones != null)
                return false;
        } else if (!pulsaciones.equals(other.pulsaciones))
            return false;
        if (peso == null) {
            if (other.peso != null)
                return false;
        } else if (!peso.equals(other.peso))
            return false;
        if (calorias == null) {
            if (other.calorias != null)
                return false;
        } else if (!calorias.equals(other.calorias))
            return false;
        return true;
    }
}

