package es.upsa.tfg.gestioneventos.wsreservas.application.repository;

import es.upsa.tfg.gestioneventos.domain.dtos.DescontarEntradasDto;
import es.upsa.tfg.gestioneventos.domain.entities.Evento;
import es.upsa.tfg.gestioneventos.domain.entities.EventoWithRecinto;
import es.upsa.tfg.gestioneventos.domain.entities.Reserva;
import es.upsa.tfg.gestioneventos.domain.exceptions.EventosAppException;

import java.util.List;
import java.util.Optional;

public interface Repository
{
    List<Reserva> getReservas() throws EventosAppException;
    Optional<Reserva> getReservaById(String id) throws EventosAppException;
    Reserva insertReserva(Reserva reserva) throws EventosAppException;
    boolean actualizarEstadoReserva(String idReserva, String nuevoEstado) throws EventosAppException;

    Optional<EventoWithRecinto>getEventoById(String id) throws EventosAppException;
    void updateEvento(String id, DescontarEntradasDto dto) throws EventosAppException;
}
