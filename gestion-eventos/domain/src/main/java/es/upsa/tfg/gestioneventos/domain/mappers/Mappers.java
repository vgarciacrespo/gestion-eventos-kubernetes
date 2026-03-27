package es.upsa.tfg.gestioneventos.domain.mappers;

import es.upsa.tfg.gestioneventos.domain.dtos.EventoDto;
import es.upsa.tfg.gestioneventos.domain.dtos.RecintoDto;
import es.upsa.tfg.gestioneventos.domain.entities.Evento;
import es.upsa.tfg.gestioneventos.domain.entities.Recinto;

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
                .withCapacidad_max(eventoDto.getCapacidad_max())
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
                .withCapacidad_max(evento.getCapacidad_max())
                .withEstado(evento.getEstado())
                .build();
    }

    public static Recinto toRecinto(RecintoDto recintoDto){
        return Recinto.builder()
                .withId_recinto(null)
                .withNombre(recintoDto.getNombre())
                .withUbicacion(recintoDto.getUbicacion())
                .withUbicacion(recintoDto.getUbicacion())
                .build();
    }
    public static RecintoDto toRecintoDto(Recinto recinto){
        return RecintoDto.builder()
                .withNombre(recinto.getNombre())
                .withUbicacion(recinto.getUbicacion())
                .withUbicacion(recinto.getUbicacion())
                .build();
    }

}
