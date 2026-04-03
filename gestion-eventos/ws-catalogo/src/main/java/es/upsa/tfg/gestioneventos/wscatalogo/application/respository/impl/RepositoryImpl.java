package es.upsa.tfg.gestioneventos.wscatalogo.application.respository.impl;

import es.upsa.tfg.gestioneventos.domain.entities.Evento;
import es.upsa.tfg.gestioneventos.domain.entities.Recinto;
import es.upsa.tfg.gestioneventos.domain.exceptions.EventoNotFoundException;
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
    public List<Recinto> getRecintos() throws EventosAppException {
        return dao.findRecintos();
    }

    @Override
    public Optional<Evento> getEventoById(String id) throws EventosAppException {
        return dao.findEventoById(id);
    }

    @Override
    public Optional<Recinto> getRecintoById(String id) throws EventosAppException {
        return dao.findRecintoById(id);
    }

    @Override
    public Evento save(Evento evento) throws EventosAppException {
        return (evento.getId_evento() == null) ? dao.insertEvento(evento) : dao.updateEvento(evento)
                                                                               .orElseThrow(EventoNotFoundException::new);
    }

    @Override
    public Recinto save(Recinto recinto) throws EventosAppException {
        return dao.insertRecinto(recinto);
    }

    @Override
    public void removeEventoById(String id) throws EventosAppException {
        dao.deleteEvento(id);
    }
}
