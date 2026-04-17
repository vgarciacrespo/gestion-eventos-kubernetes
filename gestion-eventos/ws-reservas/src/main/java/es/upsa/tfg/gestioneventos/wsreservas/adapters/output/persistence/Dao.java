package es.upsa.tfg.gestioneventos.wsreservas.adapters.output.persistence;

import es.upsa.tfg.gestioneventos.domain.entities.Evento;
import es.upsa.tfg.gestioneventos.domain.entities.Reserva;
import es.upsa.tfg.gestioneventos.domain.exceptions.EventosAppException;

import java.util.List;
import java.util.Optional;

public interface Dao
{
    List<Reserva> findReservas() throws EventosAppException;
    Optional<Reserva> findReservaById(String id) throws EventosAppException;
    Reserva insertReserva(Reserva reserva) throws EventosAppException;
    boolean actualizarEstadoReserva(String idReserva, String nuevoEstado) throws EventosAppException;
    void deleteReserva(String id) throws EventosAppException;



}
