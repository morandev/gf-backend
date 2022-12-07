package com.morandev.relevamientogf.service.authentication;

import com.morandev.relevamientogf.model.authentication.Usuario;

import java.util.Optional;

public interface IUsuarioService {
    Optional<Usuario> buscarPorEmail(String email);
    Optional<Usuario> crearUsuario(Usuario usuario);
}
