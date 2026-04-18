package es.upsa.tfg.gestioneventos.wsreservas.application.usecases;

import es.upsa.tfg.gestioneventos.domain.entities.ReservaWithEvento;
import es.upsa.tfg.gestioneventos.domain.exceptions.EventosAppException;

public interface GetReservaByIdUseCase
{
    ReservaWithEvento execute(String id) throws EventosAppException;
}
