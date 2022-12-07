package com.morandev.relevamientogf.service.implementation;

import com.morandev.relevamientogf.model.Rol;
import com.morandev.relevamientogf.repository.IRolRepository;
import com.morandev.relevamientogf.service.IRolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RolService implements IRolService {
    private final IRolRepository rolRepository;

    @Autowired
    public RolService(IRolRepository rolRepository) {
        this.rolRepository = rolRepository;
    }

    @Override
    public Optional<Rol> crearRol(Rol rol) {
        return Optional.of(rolRepository.save(rol));
    }

    @Override
    public List<Rol> listarTodos() {
        return rolRepository.findAll();
    }

    @Override
    public Optional<Rol> buscarPorId(Long id) {
        return rolRepository.findById(id);
    }

    @Override
    public Optional<Rol> actualizarRol(Long id, Rol rol) {
        Optional<Rol> rolEncontrado = rolRepository.findById(id);

        if (rolEncontrado.isPresent()) {
            rolEncontrado.get().setDescripcion(rol.getDescripcion());
            rolRepository.save(rolEncontrado.get());
        }

        return rolEncontrado;
    }

    @Override
    public boolean borrarRol(Long id) {
        if (rolRepository.existsById(id)) {
            rolRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public boolean existeElRol(String desc) {
        return rolRepository.findByDescripcion(desc).isPresent();
    }
}
