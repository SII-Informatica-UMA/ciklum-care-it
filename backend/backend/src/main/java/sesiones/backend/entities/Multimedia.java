package sesiones.backend.entities;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class Multimedia {
    @Column(length = 2048)
    private String imagen;
    @Column(length = 2048)
    private String video;


    public String getImagen(){
        return this.imagen;
    }

    public void setImagen(String img){
        this.imagen = img;
    }

    public String getVideo(){
        return this.video;
    }

    public void setVideo(String vid){
        this.video = vid;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((imagen == null) ? 0 : imagen.hashCode());
        result = prime * result + ((video == null) ? 0 : video.hashCode());
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
        Multimedia other = (Multimedia) obj;
        if (imagen == null) {
            if (other.imagen != null)
                return false;
        } else if (!imagen.equals(other.imagen))
            return false;
        if (video == null) {
            if (other.video != null)
                return false;
        } else if (!video.equals(other.video))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "{imagen=" + this.imagen + ", video=" + this.video+'}';
    }
}
