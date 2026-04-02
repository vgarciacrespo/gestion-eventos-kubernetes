package es.upsa.tfg.gestioneventos.wscatalogo.application.usecases;

import es.upsa.tfg.gestioneventos.domain.entities.EventoWithRecinto;
import es.upsa.tfg.gestioneventos.domain.exceptions.EventosAppException;

import java.util.Optional;

public interface GetEventoWithRecintoByIdUseCase
{
    EventoWithRecinto execute(String id) throws EventosAppException;
}
