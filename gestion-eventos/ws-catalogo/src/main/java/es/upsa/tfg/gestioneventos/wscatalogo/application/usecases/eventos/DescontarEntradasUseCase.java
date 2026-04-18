package es.upsa.tfg.gestioneventos.wscatalogo.application.usecases.eventos;

import es.upsa.tfg.gestioneventos.domain.exceptions.EventosAppException;

public interface DescontarEntradasUseCase
{
    boolean execute(String id, int cantidad) throws EventosAppException;
}
