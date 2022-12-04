package com.morandev.relevamientogf.service;

import com.morandev.relevamientogf.model.GrupoFamiliar;

import java.util.List;
import java.util.Optional;

public interface IGrupoFamiliarService {
    Optional<GrupoFamiliar> crearGrupoFamiliar(GrupoFamiliar grupoFamiliar);

    List<GrupoFamiliar> listarTodos();

    Optional<GrupoFamiliar> buscarPorId(Long id);

    Optional<GrupoFamiliar> actualizarGrupoFamiliar(Long id, GrupoFamiliar grupoFamiliar);

    boolean borrarGrupoFamiliar(Long id);

    boolean existeElGrupoFamiliar(Long id);
}
