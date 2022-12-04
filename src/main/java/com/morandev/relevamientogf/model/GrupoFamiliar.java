package com.morandev.relevamientogf.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Collection;

@Entity
@Table(name = "grupo_familiar")
@Getter
@Setter
@NoArgsConstructor
public class GrupoFamiliar {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "gf_id")
    private long id;
    @Column(name = "gf_descripcion")
    @NotNull
    private String descripcion;
    @Column(name = "gf_barrio_id")
    private int barrioId;
    @OneToMany(mappedBy = "grupoFamiliar") //“grupoFamiliar” del lado de la clase Integrante establece la relación
    @JsonIgnore
    private Collection<Integrante> integrantes;
}
