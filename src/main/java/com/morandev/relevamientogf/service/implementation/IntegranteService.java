package com.morandev.relevamientogf.service.implementation;

import com.morandev.relevamientogf.model.GrupoFamiliar;
import com.morandev.relevamientogf.model.Integrante;
import com.morandev.relevamientogf.repository.IIntegranteRepository;
import com.morandev.relevamientogf.service.IIntegranteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class IntegranteService implements IIntegranteService {
    private final IIntegranteRepository integranteRepository;

    @Autowired
    public IntegranteService(IIntegranteRepository integranteRepository) {
        this.integranteRepository = integranteRepository;
    }

    @Override
    public Optional<Integrante> crearIntegrante(Integrante integrante) {
        return Optional.of(integranteRepository.save(integrante));
    }

    @Override
    public List<Integrante> listarTodos() {
        return integranteRepository.findAll();
    }

    @Override
    public Optional<Integrante> buscarPorId(Long id) {
        return integranteRepository.findById(id);
    }

    @Override
    public Optional<Integrante> actualizarIntegrante(Long id, Integrante integrante) {
        Optional<Integrante> integranteEncontrado = integranteRepository.findById(id);

        if (integranteEncontrado.isPresent()) {
            integranteEncontrado.get().setDni(integrante.getDni());
            integranteEncontrado.get().setApellido(integrante.getApellido());
            integranteEncontrado.get().setNombre(integrante.getNombre());
            integranteEncontrado.get().setTieneTarjetaAlimentaria(integrante.isTieneTarjetaAlimentaria());
            integranteEncontrado.get().setRol(integrante.getRol());
            integranteEncontrado.get().setGrupoFamiliar(integrante.getGrupoFamiliar());
            integranteRepository.save(integranteEncontrado.get());
        }

        return integranteEncontrado;
    }

    @Override
    public boolean borrarIntegrante(Long id) {
        if (integranteRepository.existsById(id)) {
            integranteRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public boolean existeElIntegrante(String dni, GrupoFamiliar grupoFamiliar) {
        return integranteRepository.findByDniAndGF(dni, grupoFamiliar).isPresent();
    }
}
