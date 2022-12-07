package com.morandev.relevamientogf.controller;

import com.morandev.relevamientogf.exception.BadRequestException;
import com.morandev.relevamientogf.exception.ConflictException;
import com.morandev.relevamientogf.exception.ResourceNotFoundException;
import com.morandev.relevamientogf.model.GrupoFamiliar;
import com.morandev.relevamientogf.service.IGrupoFamiliarService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.Collection;
import java.util.Optional;

@RestController
@RequestMapping("/api/grupos-familiares")
public class GrupoFamiliarController {
    private final IGrupoFamiliarService grupoFamiliarService;

    @Autowired
    public GrupoFamiliarController(IGrupoFamiliarService grupoFamiliarService) {
        this.grupoFamiliarService = grupoFamiliarService;
    }

    @PostMapping(path = "/crear")
    public ResponseEntity<GrupoFamiliar> add(@Valid @RequestBody GrupoFamiliar grupoFamiliar) throws Exception {
        if (!grupoFamiliarService.existeElGrupoFamiliar(grupoFamiliar.getId())) {
            Optional<GrupoFamiliar> optGrupo = grupoFamiliarService.crearGrupoFamiliar(grupoFamiliar);
            if (optGrupo.isPresent()) {
                URI uri = ServletUriComponentsBuilder
                        .fromCurrentServletMapping()
                        .path("/api/grupos-familiares/{id}")
                        .buildAndExpand(optGrupo.get().getId())
                        .toUri();
                return ResponseEntity.created(uri).body(optGrupo.get());
            }
            throw new Exception("No se puede crear el grupo!");
        }
        throw new ConflictException("Grupo familiar existente");
    }

    @GetMapping
    public ResponseEntity<Collection<GrupoFamiliar>> listarTodos() {
        return ResponseEntity.ok(grupoFamiliarService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<GrupoFamiliar> buscarPorId(@PathVariable Long id) {
        if (id <= 0)
            throw new BadRequestException("invalid id: " + id);

        Optional<GrupoFamiliar> grupo = grupoFamiliarService.buscarPorId(id);

        if (grupo.isPresent())
            return ResponseEntity.ok(grupo.get());

        throw new ResourceNotFoundException("id: " + id + " not found");
    }

    @PutMapping("/{id}")
    public ResponseEntity<GrupoFamiliar> update(@PathVariable Long id, @Valid @RequestBody GrupoFamiliar grupoFamiliar) {
        if (id <= 0)
            throw new BadRequestException("invalid id: " + id);

        Optional<GrupoFamiliar> optGrupo = grupoFamiliarService.actualizarGrupoFamiliar(id, grupoFamiliar);

        if (optGrupo.isPresent())
            return ResponseEntity.ok(optGrupo.get());

        throw new ResourceNotFoundException("id: " + id + " not found");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        if (id <= 0)
            throw new BadRequestException("invalid id: " + id);

        if (grupoFamiliarService.borrarGrupoFamiliar(id))
            return ResponseEntity.noContent().build();

        throw new ResourceNotFoundException("id: " + id + " not found");
    }
}
