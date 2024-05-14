package sesiones.backend.controllers;

import sesiones.backend.dtos.SesionDTO;
import sesiones.backend.dtos.SesionNuevaDTO;
import sesiones.backend.entities.Sesion;

public class Mapper {
    public static SesionDTO toSesionDTO(Sesion sesion) {
        return SesionDTO.builder()
                .id(sesion.getId())
                .inicio(sesion.getInicio())
                .fin(sesion.getFin())
                .trabajoRealizado(sesion.getTrabajoRealizado())
                .multimedia(sesion.getMultimedia())
                .descripcion(sesion.getDescripcion())
                .presencial(sesion.getPresencial())
                .datosSalud(sesion.getDatosSalud())
                .idPlan(sesion.getIdPlan())
                .build();
    }

    public static Sesion toSesion(SesionNuevaDTO sesionNuevaDTO) {
        return Sesion.builder()
                .inicio(sesionNuevaDTO.getInicio())
                .fin(sesionNuevaDTO.getFin())
                .trabajoRealizado(sesionNuevaDTO.getTrabajoRealizado())
                .multimedia(sesionNuevaDTO.getMultimedia())
                .descripcion(sesionNuevaDTO.getDescripcion())
                .presencial(sesionNuevaDTO.getPresencial())
                .datosSalud(sesionNuevaDTO.getDatosSalud())
                .idPlan(sesionNuevaDTO.getIdPlan())
                .build();
    }

    public static Sesion toSesion(SesionDTO sesionDTO) {
        return Sesion.builder()
                .id(sesionDTO.getId())
                .inicio(sesionDTO.getInicio())
                .fin(sesionDTO.getFin())
                .trabajoRealizado(sesionDTO.getTrabajoRealizado())
                .multimedia(sesionDTO.getMultimedia())
                .descripcion(sesionDTO.getDescripcion())
                .presencial(sesionDTO.getPresencial())
                .datosSalud(sesionDTO.getDatosSalud())
                .idPlan(sesionDTO.getIdPlan())
                .build();
    }
}
