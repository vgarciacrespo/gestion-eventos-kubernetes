package es.upsa.tfg.gestioneventos.wscatalogo.application.usecases.recintos;

import es.upsa.tfg.gestioneventos.domain.exceptions.EventosAppException;

public interface RemoveRecintoUseCase
{
    void execute(String id) throws EventosAppException;
}
