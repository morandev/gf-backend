package com.morandev.relevamientogf.repository;

import com.morandev.relevamientogf.model.GrupoFamiliar;
import com.morandev.relevamientogf.model.Integrante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IIntegranteRepository extends JpaRepository<Integrante, Long> {
    @Query("SELECT i FROM Integrante i WHERE (i.dni = ?1 AND i.grupoFamiliar = ?2)")
    Optional<Integrante> findByDniAndGF(String dni, GrupoFamiliar grupoFamiliar);
}
