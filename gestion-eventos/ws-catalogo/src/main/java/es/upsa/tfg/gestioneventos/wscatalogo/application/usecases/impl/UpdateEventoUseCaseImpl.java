package es.upsa.tfg.gestioneventos.wscatalogo.application.usecases.impl;

import es.upsa.tfg.gestioneventos.domain.entities.Evento;
import es.upsa.tfg.gestioneventos.domain.exceptions.EventosAppException;
import es.upsa.tfg.gestioneventos.wscatalogo.application.respository.Repository;
import es.upsa.tfg.gestioneventos.wscatalogo.application.usecases.UpdateEventoUseCase;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class UpdateEventoUseCaseImpl implements UpdateEventoUseCase
{
    @Inject
    Repository repository;

    @Override
    public Evento execute(Evento evento) throws EventosAppException {
        return repository.save(evento);
    }
}
