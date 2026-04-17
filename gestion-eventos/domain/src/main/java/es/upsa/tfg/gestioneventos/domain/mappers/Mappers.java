package es.upsa.tfg.gestioneventos.domain.mappers;

import es.upsa.tfg.gestioneventos.domain.dtos.EventoDto;
import es.upsa.tfg.gestioneventos.domain.dtos.RecintoDto;
import es.upsa.tfg.gestioneventos.domain.dtos.ReservaDto;
import es.upsa.tfg.gestioneventos.domain.entities.EstadoReserva;
import es.upsa.tfg.gestioneventos.domain.entities.Evento;
import es.upsa.tfg.gestioneventos.domain.entities.Recinto;
import es.upsa.tfg.gestioneventos.domain.entities.Reserva;

import java.time.LocalDateTime;

public class Mappers
{

    public static Evento toEvento(EventoDto eventoDto)
    {
        return Evento.builder()
                .withId_evento(null)
                .withNombre(eventoDto.getNombre())
                .withDescripcion(eventoDto.getDescripcion())
                .withDisciplina(eventoDto.getDisciplina())
                .withRecinto(eventoDto.getRecinto())
                .withFecha_inicio(eventoDto.getFecha_inicio())
                .withFecha_final(eventoDto.getFecha_final())
                .withPrecio(eventoDto.getPrecio())
                .withEntradas_disponibles(eventoDto.getEntradas_disponibles())
                .withEstado(eventoDto.getEstado())
                .build();
    }
    public static EventoDto toEventoDto(Evento evento)
    {
        return EventoDto.builder()
                .withNombre(evento.getNombre())
                .withDescripcion(evento.getDescripcion())
                .withDisciplina(evento.getDisciplina())
                .withRecinto(evento.getRecinto())
                .withFecha_inicio(evento.getFecha_inicio())
                .withFecha_final(evento.getFecha_final())
                .withPrecio(evento.getPrecio())
                .withEntradas_disponibles(evento.getEntradas_disponibles())
                .withEstado(evento.getEstado())
                .build();
    }

    public static Recinto toRecinto(RecintoDto recintoDto){
        return Recinto.builder()
                .withId_recinto(null)
                .withNombre(recintoDto.getNombre())
                .withUbicacion(recintoDto.getUbicacion())
                .withCapacidad_total(recintoDto.getCapacidad_total())
                .build();
    }
    public static RecintoDto toRecintoDto(Recinto recinto){
        return RecintoDto.builder()
                .withNombre(recinto.getNombre())
                .withUbicacion(recinto.getUbicacion())
                .withCapacidad_total(recinto.getCapacidad_total())
                .build();
    }
    public static Reserva toReserva(ReservaDto reservaDto, double precioEvento)
    {
        return Reserva.builder()
                .withId_reserva(null)
                .withId_evento(reservaDto.getId_evento())
                .withNombre_cliente(reservaDto.getNombre_cliente())
                .withFecha_reserva(LocalDateTime.now())
                .withCantidad_entradas(reservaDto.getCantidad_entradas())
                .withPrecio_total(reservaDto.getCantidad_entradas()*precioEvento)
                .withEstadoReserva(EstadoReserva.PENDIENTE)
                .build();
    }
    public static ReservaDto toReservaDto(Reserva reserva)
    {
        return ReservaDto.builder()
                .withId_evento(reserva.getId_evento())
                .withNombre_cliente(reserva.getNombre_cliente())
                .withFecha_reserva(reserva.getFecha_reserva())
                .withCantidad_entradas(reserva.getCantidad_entradas())
                .withPrecio_total(reserva.getPrecio_total())
                .build();
    }

}
