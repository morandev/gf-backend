package com.morandev.relevamientogf.service.implementation;

import com.morandev.relevamientogf.model.authentication.Usuario;
import com.morandev.relevamientogf.model.authentication.UsuarioAutenticado;
import com.morandev.relevamientogf.service.authentication.IUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final IUsuarioService userService;

    @Autowired
    public UserDetailsServiceImpl(IUsuarioService userService) {
        this.userService = userService;
    }

    /**
     * Carga los datos del usuario si existe
     *
     * @param email the email identifying the user whose data is required.
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<Usuario> user = userService.buscarPorEmail(email);

        if (user.isPresent()) {
            return UsuarioAutenticado.build(user.get());
        }

        throw new UsernameNotFoundException("Email: " + email + ": not found");
    }
}
