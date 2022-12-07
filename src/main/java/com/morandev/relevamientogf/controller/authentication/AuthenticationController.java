package com.morandev.relevamientogf.controller.authentication;

import com.morandev.relevamientogf.dto.UsuarioAutenticadoDto;
import com.morandev.relevamientogf.exception.ConflictException;
import com.morandev.relevamientogf.model.authentication.Usuario;
import com.morandev.relevamientogf.service.authentication.IUsuarioService;
import com.morandev.relevamientogf.utils.JWTUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/authenticate")
public class AuthenticationController {
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final IUsuarioService userService;
    private final JWTUtils jwtUtils;

    @Autowired
    public AuthenticationController(
            AuthenticationManager authenticationManager,
            UserDetailsService userDetailsService,
            IUsuarioService userService,
            JWTUtils jwtUtils) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.userService = userService;
        this.jwtUtils = jwtUtils;
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@Valid @RequestBody UsuarioAutenticadoDto authenticationRequest) {
        Map<String, String> body = new HashMap<>();
        Authentication auth;

        auth = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getEmail(), authenticationRequest.getPassword()));

        if (auth.isAuthenticated()) {
            final UserDetails userDetails;
            userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getEmail());

            String token = jwtUtils.generateToken(userDetails);
            body.put("jwt", token);

            return ResponseEntity.status(201).body(body);
        }

        throw new BadCredentialsException("Invalid Credentials");
    }

    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> register(@Valid @RequestBody Usuario user) {

        if (userService.buscarPorEmail(user.getEmail()).isPresent())
            throw new ConflictException("Email already exists");

        Map<String, String> body = new HashMap<>();

        Usuario _user = new Usuario(); //crear

        _user.setNombre(user.getNombre()); //nombre
        _user.setApellido(user.getApellido()); //apellido
        _user.setEmail(user.getEmail()); //email
        _user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword())); //pass

        userService.crearUsuario(_user); //crear

        UserDetails userDetails = userDetailsService.loadUserByUsername(user.getEmail());

        String token = jwtUtils.generateToken(userDetails);
        body.put("jwt", token);

        return ResponseEntity.status(201).body(body);
    }

}
