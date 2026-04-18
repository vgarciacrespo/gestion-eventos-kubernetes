package es.upsa.tfg.gestioneventos.wsreservas.application.repository.impl;

import es.upsa.tfg.gestioneventos.domain.entities.Evento;
import es.upsa.tfg.gestioneventos.domain.entities.Reserva;
import es.upsa.tfg.gestioneventos.domain.exceptions.EventosAppException;
import es.upsa.tfg.gestioneventos.wsreservas.adapters.output.persistence.Dao;
import es.upsa.tfg.gestioneventos.wsreservas.application.repository.Repository;
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
    public List<Reserva> getReserva() throws EventosAppException {
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
}
