package com.morandev.relevamientogf.model.authentication;

import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

@AllArgsConstructor
public class UsuarioAutenticado implements UserDetails {
    private Long id;
    private String nombre;
    private String apellido;
    private String email;
    private String password;
    private GrantedAuthority authorities;

    /*
        Asigna los permisos a cada usuario,
        recibe un usuario de la bd por parametro
    */
    public static UsuarioAutenticado build(Usuario user) {
        GrantedAuthority authority = new SimpleGrantedAuthority("Administrador");

        return new UsuarioAutenticado(user.getId(),
                user.getNombre(),
                user.getApellido(),
                user.getEmail(),
                user.getPassword(),
                authority);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(authorities);
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return nombre;
    }

    public String getLastName() {
        return apellido;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
