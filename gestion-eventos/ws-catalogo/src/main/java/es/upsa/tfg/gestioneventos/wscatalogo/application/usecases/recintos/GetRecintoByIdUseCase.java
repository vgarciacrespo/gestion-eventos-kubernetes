package es.upsa.tfg.gestioneventos.wscatalogo.application.usecases.recintos;

import es.upsa.tfg.gestioneventos.domain.entities.Recinto;
import es.upsa.tfg.gestioneventos.domain.exceptions.EventosAppException;

import java.util.Optional;

public interface GetRecintoByIdUseCase
{
    Optional<Recinto> execute(String id) throws EventosAppException;
}
