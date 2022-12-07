package com.morandev.relevamientogf.repository;

import com.morandev.relevamientogf.model.Rol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IRolRepository extends JpaRepository<Rol, Long> {
    @Query("SELECT r FROM Rol r WHERE r.descripcion = ?1")
    Optional<Rol> findByDescripcion(String descripcion);
}
