package es.upsa.tfg.gestioneventos.wsreservas.application.usecases.impl;

import es.upsa.tfg.gestioneventos.domain.dtos.DescontarEntradasDto;
import es.upsa.tfg.gestioneventos.domain.entities.EstadoReserva;
import es.upsa.tfg.gestioneventos.domain.entities.Evento;
import es.upsa.tfg.gestioneventos.domain.entities.EventoWithRecinto;
import es.upsa.tfg.gestioneventos.domain.entities.Reserva;
import es.upsa.tfg.gestioneventos.domain.exceptions.EventoNotFoundException;
import es.upsa.tfg.gestioneventos.domain.exceptions.EventosAppException;
import es.upsa.tfg.gestioneventos.wsreservas.application.repository.Repository;
import es.upsa.tfg.gestioneventos.wsreservas.application.usecases.AddReservaUseCase;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class AddReservaUseCaseImpl implements AddReservaUseCase
{
    @Inject
    Repository repository;

    @Override
    public Reserva execute(Reserva reserva) throws EventosAppException
    {
        EventoWithRecinto evento = repository.getEventoById(reserva.getId_evento())
                                             .orElseThrow(EventoNotFoundException::new);
        double precioTotal = evento.getEvento().getPrecio()*reserva.getCantidad_entradas();
        reserva.setPrecio_total(precioTotal);

        reserva.setEstadoReserva(EstadoReserva.PENDIENTE);

        Reserva newReserva = repository.insertReserva(reserva);

        try{
            DescontarEntradasDto dto = DescontarEntradasDto.builder()
                                                           .withCantidad(newReserva.getCantidad_entradas())
                                                           .build();

            repository.updateEvento(newReserva.getId_evento(), dto);

            repository.actualizarEstadoReserva(newReserva.getId_reserva(), "CONFIRMADA");
            newReserva.setEstadoReserva(EstadoReserva.CONFIRMADA);
            return newReserva;

        }catch(EventosAppException e){
            repository.actualizarEstadoReserva(newReserva.getId_reserva(), "CANCELADA" );
            throw new EventosAppException("Reserva no completada con exito");
        }
    }
}
