package com.morandev.relevamientogf.service;

import com.morandev.relevamientogf.model.Rol;

import java.util.List;
import java.util.Optional;

public interface IRolService {
    Optional<Rol> crearRol(Rol rol);

    List<Rol> listarTodos();

    Optional<Rol> buscarPorId(Long id);

    Optional<Rol> actualizarRol(Long id, Rol rol);

    boolean borrarRol(Long id);

    boolean existeElRol(String descripcion);
}
