package com.morandev.relevamientogf.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Setter
@Getter
@NoArgsConstructor
public class UsuarioAutenticadoDto {
    @NotEmpty(message = "Por favor ingrese un  email")
    @Email(message = "Por favor ingrese un email valido")
    private String email;
    @NotEmpty(message = "Por favor ingrese un password")
    private String password;
}
