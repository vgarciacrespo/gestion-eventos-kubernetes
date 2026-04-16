package es.upsa.tfg.gestioneventos.wscatalogo.application.usecases.eventos;

import es.upsa.tfg.gestioneventos.domain.entities.EventoWithRecinto;
import es.upsa.tfg.gestioneventos.domain.exceptions.EventosAppException;

public interface GetEventoWithRecintoByIdUseCase
{
    EventoWithRecinto execute(String id) throws EventosAppException;
}
