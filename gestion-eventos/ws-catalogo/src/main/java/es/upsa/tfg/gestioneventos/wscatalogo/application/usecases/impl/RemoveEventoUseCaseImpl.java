package es.upsa.tfg.gestioneventos.wscatalogo.application.usecases.impl;

import es.upsa.tfg.gestioneventos.domain.exceptions.EventosAppException;
import es.upsa.tfg.gestioneventos.wscatalogo.application.respository.Repository;
import es.upsa.tfg.gestioneventos.wscatalogo.application.usecases.RemoveEventoUseCase;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class RemoveEventoUseCaseImpl implements RemoveEventoUseCase
{
    @Inject
    Repository repository;

    @Override
    public void execute(String id) throws EventosAppException
    {
        repository.removeEventoById(id);

    }
}
