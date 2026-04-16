package es.upsa.tfg.gestioneventos.wscatalogo.application.usecases.recintos.impl;

import es.upsa.tfg.gestioneventos.domain.entities.Recinto;
import es.upsa.tfg.gestioneventos.domain.exceptions.EventosAppException;
import es.upsa.tfg.gestioneventos.wscatalogo.application.respository.Repository;
import es.upsa.tfg.gestioneventos.wscatalogo.application.usecases.recintos.GetRecintosUseCase;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.List;

@ApplicationScoped
public class GetRecintosUseCaseImpl implements GetRecintosUseCase
{
    @Inject
    Repository repository;

    @Override
    public List<Recinto> execute() throws EventosAppException {
        return repository.getRecintos();
    }
}
