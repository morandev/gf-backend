package com.morandev.relevamientogf.service.implementation.authentication;

import com.morandev.relevamientogf.model.authentication.Usuario;
import com.morandev.relevamientogf.repository.IUsuarioRepository;
import com.morandev.relevamientogf.service.authentication.IUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsuarioService implements IUsuarioService {

    private final IUsuarioRepository usuarioRepository;

    @Autowired
    public UsuarioService(IUsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public Optional<Usuario> buscarPorEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }

    @Override
    public Optional<Usuario> crearUsuario(Usuario usuario) {
        return Optional.of(usuarioRepository.save(usuario));
    }
}
