package es.upsa.tfg.gestioneventos.wscatalogo.application.usecases;

import es.upsa.tfg.gestioneventos.domain.entities.Recinto;
import es.upsa.tfg.gestioneventos.domain.exceptions.EventosAppException;

import java.util.List;

public interface GetRecintosUseCase
{
    List<Recinto> execute() throws EventosAppException;
}
