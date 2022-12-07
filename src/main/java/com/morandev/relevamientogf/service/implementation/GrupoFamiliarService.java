package com.morandev.relevamientogf.service.implementation;

import com.morandev.relevamientogf.model.GrupoFamiliar;
import com.morandev.relevamientogf.repository.IGrupoFamiliarRepository;
import com.morandev.relevamientogf.service.IGrupoFamiliarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GrupoFamiliarService implements IGrupoFamiliarService {
    private final IGrupoFamiliarRepository grupoFamiliarRepository;

    @Autowired
    public GrupoFamiliarService(IGrupoFamiliarRepository grupoFamiliarRepository) {
        this.grupoFamiliarRepository = grupoFamiliarRepository;
    }

    @Override
    public Optional<GrupoFamiliar> crearGrupoFamiliar(GrupoFamiliar grupoFamiliar) {
        return Optional.of(grupoFamiliarRepository.save(grupoFamiliar));
    }

    @Override
    public List<GrupoFamiliar> listarTodos() {
        return grupoFamiliarRepository.findAll();
    }

    @Override
    public Optional<GrupoFamiliar> buscarPorId(Long id) {
        return grupoFamiliarRepository.findById(id);
    }

    @Override
    public Optional<GrupoFamiliar> actualizarGrupoFamiliar(Long id, GrupoFamiliar grupoFamiliar) {
        Optional<GrupoFamiliar> gfEncontrado = grupoFamiliarRepository.findById(id);

        if (gfEncontrado.isPresent()) {
            gfEncontrado.get().setDescripcion(grupoFamiliar.getDescripcion());
            gfEncontrado.get().setBarrioId(grupoFamiliar.getBarrioId());
            gfEncontrado.get().setIntegrantes(grupoFamiliar.getIntegrantes());
            gfEncontrado.get().setFechaAct(grupoFamiliar.getFechaAct());
            grupoFamiliarRepository.save(gfEncontrado.get());
        }

        return gfEncontrado;
    }

    @Override
    public boolean borrarGrupoFamiliar(Long id) {
        if (grupoFamiliarRepository.existsById(id)) {
            grupoFamiliarRepository.deleteById(id);
            return true;
        }

        return false;
    }

    @Override
    public boolean existeElGrupoFamiliar(Long id) {
        return grupoFamiliarRepository.existsById(id);
    }
}
