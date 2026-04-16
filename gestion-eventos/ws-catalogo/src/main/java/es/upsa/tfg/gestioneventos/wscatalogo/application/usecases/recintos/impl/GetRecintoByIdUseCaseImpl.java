package es.upsa.tfg.gestioneventos.wscatalogo.application.usecases.recintos.impl;

import es.upsa.tfg.gestioneventos.domain.entities.Recinto;
import es.upsa.tfg.gestioneventos.domain.exceptions.EventosAppException;
import es.upsa.tfg.gestioneventos.wscatalogo.application.respository.Repository;
import es.upsa.tfg.gestioneventos.wscatalogo.application.usecases.recintos.GetRecintoByIdUseCase;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.Optional;

@ApplicationScoped
public class GetRecintoByIdUseCaseImpl implements GetRecintoByIdUseCase
{
    @Inject
    Repository repository;

    @Override
    public Optional<Recinto> execute(String id) throws EventosAppException {
        return repository.getRecintoById(id);
    }
}
