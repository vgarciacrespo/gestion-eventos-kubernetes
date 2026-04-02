package es.upsa.tfg.gestioneventos.wscatalogo.application.respository.impl;

import es.upsa.tfg.gestioneventos.domain.entities.Evento;
import es.upsa.tfg.gestioneventos.domain.entities.Recinto;
import es.upsa.tfg.gestioneventos.domain.exceptions.EventosAppException;
import es.upsa.tfg.gestioneventos.wscatalogo.adapters.output.persistence.Dao;
import es.upsa.tfg.gestioneventos.wscatalogo.application.respository.Repository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class RepositoryImpl implements Repository
{
    @Inject
    Dao dao;
    @Override
    public List<Evento> getEventos() throws EventosAppException {
        return dao.findEventos();
    }

    @Override
    public Optional<Evento> getEvento(String id) throws EventosAppException {
        return dao.findEventoById(id);
    }

    @Override
    public Optional<Recinto> getRecinto(String id) throws EventosAppException {
        return dao.findRecintoById(id);
    }
}
