package es.upsa.tfg.gestioneventos.wsreservas.application.usecases.impl;

import es.upsa.tfg.gestioneventos.domain.entities.Evento;
import es.upsa.tfg.gestioneventos.domain.entities.EventoWithRecinto;
import es.upsa.tfg.gestioneventos.domain.entities.Reserva;
import es.upsa.tfg.gestioneventos.domain.entities.ReservaWithEvento;
import es.upsa.tfg.gestioneventos.domain.exceptions.EventoNotFoundException;
import es.upsa.tfg.gestioneventos.domain.exceptions.EventosAppException;
import es.upsa.tfg.gestioneventos.domain.exceptions.ReservaNotFoundException;
import es.upsa.tfg.gestioneventos.wsreservas.application.repository.Repository;
import es.upsa.tfg.gestioneventos.wsreservas.application.usecases.GetReservaByIdUseCase;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class GetReservaByIdUseCaseImpl implements GetReservaByIdUseCase
{
    @Inject
    Repository repository;

    @Override
    public ReservaWithEvento execute(String id) throws EventosAppException {

        Reserva reserva = repository.getReservaById(id)
                                    .orElseThrow(ReservaNotFoundException::new);
        String idEvento = reserva.getId_evento();

        EventoWithRecinto eventoWithRecinto = repository.getEventoById(idEvento)
                                                        .orElseThrow(EventoNotFoundException::new);

        return ReservaWithEvento.builder()
                .withReserva(reserva)
                .withEventoWithRecinto(eventoWithRecinto)
                .build();
    }
}
