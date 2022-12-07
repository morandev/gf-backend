package com.morandev.relevamientogf.controller;

import com.morandev.relevamientogf.exception.BadRequestException;
import com.morandev.relevamientogf.exception.ConflictException;
import com.morandev.relevamientogf.exception.ResourceNotFoundException;
import com.morandev.relevamientogf.model.Rol;
import com.morandev.relevamientogf.service.IRolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.Collection;
import java.util.Optional;

@RestController
@RequestMapping("/api/roles")
class RolController {
    private final IRolService rolService;

    @Autowired
    public RolController(IRolService rolService) {
        this.rolService = rolService;
    }

    @PostMapping(path = "/crear")
    public ResponseEntity<Rol> add(@Valid @RequestBody Rol rol) throws Exception {
        if (!rolService.existeElRol(rol.getDescripcion())) {
            Optional<Rol> optRol = rolService.crearRol(rol);
            if (optRol.isPresent()) {
                URI uri = ServletUriComponentsBuilder
                        .fromCurrentServletMapping()
                        .path("/api/roles/{id}")
                        .buildAndExpand(optRol.get().getId())
                        .toUri();

                return ResponseEntity.created(uri).body(optRol.get());
            }
            throw new Exception("Can not add a rol!");
        }
        throw new ConflictException("Rol existente");
    }

    @GetMapping
    public ResponseEntity<Collection<Rol>> listarTodos() {
        return ResponseEntity.ok(rolService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Rol> buscarPorId(@PathVariable Long id) {
        if (id <= 0)
            throw new BadRequestException("invalid id: " + id);

        Optional<Rol> rol = rolService.buscarPorId(id);

        if (rol.isPresent())
            return ResponseEntity.ok(rol.get());

        throw new ResourceNotFoundException("id: " + id + " not found");
    }

    @PutMapping("/{id}")
    public ResponseEntity<Rol> update(@PathVariable Long id, @Valid @RequestBody Rol rol) {
        if (id <= 0)
            throw new BadRequestException("invalid id: " + id);

        Optional<Rol> optRol = rolService.actualizarRol(id, rol);

        if (optRol.isPresent())
            return ResponseEntity.ok(optRol.get());

        throw new ResourceNotFoundException("id: " + id + " not found");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        if (id <= 0)
            throw new BadRequestException("invalid id: " + id);

        if (rolService.borrarRol(id))
            return ResponseEntity.noContent().build();

        throw new ResourceNotFoundException("id: " + id + " not found");
    }
}
