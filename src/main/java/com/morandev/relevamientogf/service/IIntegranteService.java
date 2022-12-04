package com.morandev.relevamientogf.service;

import com.morandev.relevamientogf.model.GrupoFamiliar;
import com.morandev.relevamientogf.model.Integrante;

import java.util.List;
import java.util.Optional;

public interface IIntegranteService {
    Optional<Integrante> crearIntegrante(Integrante integrante);

    List<Integrante> listarTodos();

    Optional<Integrante> buscarPorId(Long id);

    Optional<Integrante> actualizarIntegrante(Long id, Integrante integrante);

    boolean borrarIntegrante(Long id);

    boolean existeElIntegrante(String dni, GrupoFamiliar grupoFamiliar);
}
