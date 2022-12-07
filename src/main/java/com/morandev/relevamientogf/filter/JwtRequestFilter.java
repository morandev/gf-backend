package com.morandev.relevamientogf.filter;

import com.morandev.relevamientogf.utils.JWTUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {
    private final UserDetailsService userDetailsService;
    private final JWTUtils jwtUtilService;

    @Autowired
    public JwtRequestFilter(UserDetailsService userDetailsService, JWTUtils jwtUtilService) {
        this.userDetailsService = userDetailsService;
        this.jwtUtilService = jwtUtilService;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain chain) throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");

        String username = "";
        String jwt = "";

        if (authHeader != null && authHeader.startsWith("Bearer")) {
            jwt = authHeader.substring(7);
            //obtengo el usuario del token
            username = jwtUtilService.extractUsername(jwt);
        }

        // si existe el usuario del token y no hay un contexto de seguridad inicializado
        if (!username.isEmpty() && (SecurityContextHolder.getContext().getAuthentication() == null)) {
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

            if (jwtUtilService.validateToken(jwt, userDetails)) {
                UsernamePasswordAuthenticationToken authenticationToken;

                authenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                //inicializa el security context holder
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }

        //continuamos con la cadena de ejecucion
        chain.doFilter(request, response);
    }

}
