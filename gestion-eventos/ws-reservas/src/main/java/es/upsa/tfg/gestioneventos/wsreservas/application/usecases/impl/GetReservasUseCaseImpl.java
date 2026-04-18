package es.upsa.tfg.gestioneventos.wsreservas.application.usecases.impl;

import es.upsa.tfg.gestioneventos.domain.entities.Reserva;
import es.upsa.tfg.gestioneventos.domain.exceptions.EventosAppException;
import es.upsa.tfg.gestioneventos.wsreservas.application.repository.Repository;
import es.upsa.tfg.gestioneventos.wsreservas.application.usecases.GetReservasUseCase;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.List;

@ApplicationScoped
public class GetReservasUseCaseImpl implements GetReservasUseCase
{

    @Inject
    Repository repository;
    @Override
    public List<Reserva> execute() throws EventosAppException {
        return repository.getReservas();
    }
}
