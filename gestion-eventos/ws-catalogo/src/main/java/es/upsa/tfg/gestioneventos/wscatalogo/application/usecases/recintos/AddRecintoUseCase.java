package es.upsa.tfg.gestioneventos.wscatalogo.application.usecases.recintos;

import es.upsa.tfg.gestioneventos.domain.entities.Recinto;
import es.upsa.tfg.gestioneventos.domain.exceptions.EventosAppException;

public interface AddRecintoUseCase
{
    Recinto execute(Recinto recinto) throws EventosAppException;
}
