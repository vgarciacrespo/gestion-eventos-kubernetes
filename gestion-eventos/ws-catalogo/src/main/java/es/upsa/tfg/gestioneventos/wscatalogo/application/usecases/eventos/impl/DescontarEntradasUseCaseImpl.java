package es.upsa.tfg.gestioneventos.wscatalogo.application.usecases.eventos.impl;

import es.upsa.tfg.gestioneventos.domain.exceptions.EventosAppException;
import es.upsa.tfg.gestioneventos.wscatalogo.application.respository.Repository;
import es.upsa.tfg.gestioneventos.wscatalogo.application.usecases.eventos.DescontarEntradasUseCase;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class DescontarEntradasUseCaseImpl implements DescontarEntradasUseCase
{
    @Inject
    Repository repository;

    @Override
    public boolean execute(String id, int cantidad) throws EventosAppException {
        return repository.descontarEntradas(id, cantidad);
    }
}
