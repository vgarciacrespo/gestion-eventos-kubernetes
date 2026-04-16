package es.upsa.tfg.gestioneventos.wscatalogo.application.usecases.eventos;

import es.upsa.tfg.gestioneventos.domain.entities.Evento;
import es.upsa.tfg.gestioneventos.domain.exceptions.EventosAppException;

public interface AddEventoUseCase
{
    Evento execute(Evento evento) throws EventosAppException;
}
