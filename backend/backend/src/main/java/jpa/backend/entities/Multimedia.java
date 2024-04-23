package jpa.backend.entities;
import jakarta.persistence.*;

@Embeddable
public class Multimedia {
    private String imagen;
    private String video;


    private String getImagen(){
        return this.imagen;
    }

    private void setImagen(String img){
        this.imagen = img;
    }

    private String getVideo(){
        return this.video;
    }

    private void setVideo(String vid){
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
}