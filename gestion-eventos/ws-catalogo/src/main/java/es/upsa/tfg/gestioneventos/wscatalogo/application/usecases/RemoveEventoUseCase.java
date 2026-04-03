package es.upsa.tfg.gestioneventos.wscatalogo.application.usecases;

import es.upsa.tfg.gestioneventos.domain.exceptions.EventosAppException;

public interface RemoveEventoUseCase
{
    void execute(String id) throws EventosAppException;
}
