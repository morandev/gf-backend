package com.morandev.relevamientogf.controller;

import com.morandev.relevamientogf.exception.BadRequestException;
import com.morandev.relevamientogf.exception.ConflictException;
import com.morandev.relevamientogf.exception.ResourceNotFoundException;
import com.morandev.relevamientogf.model.Integrante;
import com.morandev.relevamientogf.service.IIntegranteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.Collection;
import java.util.Optional;

@RestController
@RequestMapping("/api/integrantes")
class IntegranteController {
    private final IIntegranteService integranteService;

    @Autowired
    public IntegranteController(IIntegranteService integranteService) {
        this.integranteService = integranteService;
    }

    @PostMapping(path = "/crear")
    public ResponseEntity<Integrante> add(@Valid @RequestBody Integrante integrante) throws Exception {
        if (!integranteService.existeElIntegrante(integrante.getDni(), integrante.getGrupoFamiliar())) {
            Optional<Integrante> optIntegrante = integranteService.crearIntegrante(integrante);
            if (optIntegrante.isPresent()) {
                URI uri = ServletUriComponentsBuilder
                        .fromCurrentServletMapping()
                        .path("/api/integrantes/{id}")
                        .buildAndExpand(optIntegrante.get().getId())
                        .toUri();

                return ResponseEntity.created(uri).body(optIntegrante.get());
            }
            throw new Exception("Can not add a integrante!");
        }
        throw new ConflictException("Integrante existente");
    }

    @GetMapping
    public ResponseEntity<Collection<Integrante>> listarTodos() {
        return ResponseEntity.ok(integranteService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Integrante> buscarPorId(@PathVariable Long id) {
        if (id <= 0)
            throw new BadRequestException("invalid id: " + id);

        Optional<Integrante> rol = integranteService.buscarPorId(id);

        if (rol.isPresent())
            return ResponseEntity.ok(rol.get());

        throw new ResourceNotFoundException("id: " + id + " not found");
    }

    @PutMapping("/{id}")
    public ResponseEntity<Integrante> update(@PathVariable Long id, @Valid @RequestBody Integrante integrante) {
        if (id <= 0)
            throw new BadRequestException("invalid id: " + id);

        Optional<Integrante> optIntegrante = integranteService.actualizarIntegrante(id, integrante);

        if (optIntegrante.isPresent())
            return ResponseEntity.ok(optIntegrante.get());

        throw new ResourceNotFoundException("id: " + id + " not found");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        if (id <= 0)
            throw new BadRequestException("invalid id: " + id);

        if (integranteService.borrarIntegrante(id))
            return ResponseEntity.noContent().build();

        throw new ResourceNotFoundException("id: " + id + " not found");
    }
}
