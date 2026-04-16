package es.upsa.tfg.gestioneventos.wscatalogo.application.usecases.recintos.impl;

import es.upsa.tfg.gestioneventos.domain.entities.Recinto;
import es.upsa.tfg.gestioneventos.domain.exceptions.EventosAppException;
import es.upsa.tfg.gestioneventos.wscatalogo.application.respository.Repository;
import es.upsa.tfg.gestioneventos.wscatalogo.application.usecases.recintos.AddRecintoUseCase;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class AddRecintoUseCaseImpl implements AddRecintoUseCase
{
    @Inject
    Repository repository;

    @Override
    public Recinto execute(Recinto recinto) throws EventosAppException
    {
        return repository.save(recinto);
    }
}
