package com.morandev.relevamientogf.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "integrantes")
@Getter
@Setter
@NoArgsConstructor
public class Integrante {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "i_id")
    private long id;
    @NotNull
    @Column(name = "i_dni")
    private String dni;
    @Column(name = "i_apellido", length = 11)
    private String apellido;
    @Column(name = "i_nombre")
    private String nombre;
    @Column(name = "i_tiene_tarjeta_alimentaria")
    private boolean tieneTarjetaAlimentaria;
    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "roles_rol_id" , referencedColumnName="rol_id")
    private Rol rol;
    @ManyToOne
    @JoinColumn(name = "grupo_familiar_gf_id", nullable = false)
    private GrupoFamiliar grupoFamiliar;
}
