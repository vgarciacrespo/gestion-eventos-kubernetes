package es.upsa.tfg.gestioneventos.wscatalogo.application.usecases;

import es.upsa.tfg.gestioneventos.domain.entities.Evento;
import es.upsa.tfg.gestioneventos.domain.exceptions.EventosAppException;

public interface UpdateEventoUseCase
{
    Evento execute(Evento evento) throws EventosAppException;
}
