package es.upsa.tfg.gestioneventos.wscatalogo.application.usecases.eventos.impl;

import es.upsa.tfg.gestioneventos.domain.entities.Evento;
import es.upsa.tfg.gestioneventos.domain.entities.EventoWithRecinto;
import es.upsa.tfg.gestioneventos.domain.entities.Recinto;
import es.upsa.tfg.gestioneventos.domain.exceptions.EventoNotFoundException;
import es.upsa.tfg.gestioneventos.domain.exceptions.EventosAppException;
import es.upsa.tfg.gestioneventos.domain.exceptions.RecintoNotFoundException;
import es.upsa.tfg.gestioneventos.wscatalogo.application.respository.Repository;
import es.upsa.tfg.gestioneventos.wscatalogo.application.usecases.eventos.GetEventoWithRecintoByIdUseCase;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;


@ApplicationScoped
public class GetEventoWithRecintoByIdUseCaseImpl implements GetEventoWithRecintoByIdUseCase
{
    @Inject
    Repository repository;
    @Override
    public EventoWithRecinto execute(String id) throws EventosAppException
    {
        Evento evento = repository.getEventoById(id)
                .orElseThrow(EventoNotFoundException::new);

        String idRecinto = evento.getRecinto();
        Recinto recinto = repository.getRecintoById(idRecinto)
                .orElseThrow(RecintoNotFoundException::new);

        return EventoWithRecinto.builder()
                                .withEvento(evento)
                                .withRecinto(recinto)
                                .build();

    }
}
