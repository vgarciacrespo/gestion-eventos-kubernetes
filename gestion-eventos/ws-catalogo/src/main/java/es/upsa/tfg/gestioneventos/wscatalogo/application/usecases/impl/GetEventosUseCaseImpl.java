package es.upsa.tfg.gestioneventos.wscatalogo.application.usecases.impl;

import es.upsa.tfg.gestioneventos.domain.entities.Evento;
import es.upsa.tfg.gestioneventos.domain.exceptions.EventosAppException;
import es.upsa.tfg.gestioneventos.wscatalogo.application.respository.Repository;
import es.upsa.tfg.gestioneventos.wscatalogo.application.usecases.GetEventosUseCase;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.List;

@ApplicationScoped
public class GetEventosUseCaseImpl implements GetEventosUseCase
{
    @Inject
    Repository repository;

    @Override
    public List<Evento> execute() throws EventosAppException {
        return repository.getEventos();
    }
}
