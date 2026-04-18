package es.upsa.tfg.gestioneventos.wsreservas.application.repository.impl;

import es.upsa.tfg.gestioneventos.domain.dtos.DescontarEntradasDto;
import es.upsa.tfg.gestioneventos.domain.entities.Evento;
import es.upsa.tfg.gestioneventos.domain.entities.EventoWithRecinto;
import es.upsa.tfg.gestioneventos.domain.entities.Reserva;
import es.upsa.tfg.gestioneventos.domain.exceptions.EventosAppException;
import es.upsa.tfg.gestioneventos.wsreservas.adapters.output.persistence.Dao;
import es.upsa.tfg.gestioneventos.wsreservas.adapters.output.rest.EventosDao;
import es.upsa.tfg.gestioneventos.wsreservas.application.repository.Repository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.List;
import java.util.Optional;


@ApplicationScoped
public class RepositoryImpl implements Repository
{
    @Inject
    EventosDao eventosDao;
    @Inject
    Dao dao;

    @Override
    public List<Reserva> getReservas() throws EventosAppException {
        return dao.findReservas();
    }

    @Override
    public Optional<Reserva> getReservaById(String id) throws EventosAppException {
        return dao.findReservaById(id);
    }

    @Override
    public Reserva insertReserva(Reserva reserva) throws EventosAppException {
        return dao.insertReserva(reserva);
    }

    @Override
    public boolean actualizarEstadoReserva(String idReserva, String nuevoEstado) throws EventosAppException {
        return dao.actualizarEstadoReserva(idReserva, nuevoEstado);
    }

    @Override
    public Optional<EventoWithRecinto> getEventoById(String id) throws EventosAppException {
        return eventosDao.findById(id);
    }

    @Override
    public void updateEvento(String id, DescontarEntradasDto dto) throws EventosAppException {
        eventosDao.updateEvento(id, dto);
    }


}
