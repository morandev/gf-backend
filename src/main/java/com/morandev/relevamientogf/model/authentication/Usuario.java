package com.morandev.relevamientogf.model.authentication;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "usuarios")
@Getter
@Setter
@NoArgsConstructor
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "u_id")
    private long id;
    @Column(name = "u_nombre")
    private String nombre;
    @Column(name = "u_apellido")
    private String apellido;
    @Column(name = "u_email")
    private String email;
    @Column(name = "u_password")
    private String password;
}
