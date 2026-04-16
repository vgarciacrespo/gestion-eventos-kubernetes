package es.upsa.tfg.gestioneventos.wscatalogo.application.usecases.recintos.impl;

import es.upsa.tfg.gestioneventos.domain.exceptions.EventosAppException;
import es.upsa.tfg.gestioneventos.wscatalogo.application.respository.Repository;
import es.upsa.tfg.gestioneventos.wscatalogo.application.usecases.recintos.RemoveRecintoUseCase;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class RemoveRecintoUseCaseImpl implements RemoveRecintoUseCase
{
    @Inject
    Repository repository;
    @Override
    public void execute(String id) throws EventosAppException {
        repository.removeRecintoById(id);
    }
}
