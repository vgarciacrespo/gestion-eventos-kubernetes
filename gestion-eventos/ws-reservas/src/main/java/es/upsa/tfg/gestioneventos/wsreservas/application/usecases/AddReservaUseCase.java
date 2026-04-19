package es.upsa.tfg.gestioneventos.wsreservas.application.usecases;

import es.upsa.tfg.gestioneventos.domain.entities.Reserva;
import es.upsa.tfg.gestioneventos.domain.exceptions.EventosAppException;

public interface AddReservaUseCase
{
    Reserva execute(Reserva reserva) throws EventosAppException;
}
