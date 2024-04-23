package jpa.backend.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import jpa.backend.entities.Sesion;

public interface SesionRepository extends JpaRepository<Sesion, Long> {
	Optional<Sesion> findById(Long id);

}