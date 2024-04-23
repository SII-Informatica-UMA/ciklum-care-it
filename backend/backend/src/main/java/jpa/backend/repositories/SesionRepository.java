package jpa.backend.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import jpa.backend.entities.Sesion;

public interface SesionRepository extends JpaRepository<Sesion, Long> {

	List<Sesion> findByInicio(String inicio);

}