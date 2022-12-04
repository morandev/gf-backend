package com.morandev.relevamientogf.repository;

import com.morandev.relevamientogf.model.GrupoFamiliar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IGrupoFamiliarRepository extends JpaRepository<GrupoFamiliar, Long> {
}
