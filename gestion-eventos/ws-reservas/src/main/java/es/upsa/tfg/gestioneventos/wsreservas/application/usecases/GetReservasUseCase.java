package es.upsa.tfg.gestioneventos.wsreservas.application.usecases;

import es.upsa.tfg.gestioneventos.domain.entities.Reserva;
import es.upsa.tfg.gestioneventos.domain.exceptions.EventosAppException;

import java.util.List;

public interface GetReservasUseCase
{
    List<Reserva> execute() throws EventosAppException;
}
