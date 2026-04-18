package es.upsa.tfg.gestioneventos.wsreservas.adapters.output.rest;

import es.upsa.tfg.gestioneventos.domain.dtos.DescontarEntradasDto;
import es.upsa.tfg.gestioneventos.domain.entities.Evento;
import es.upsa.tfg.gestioneventos.domain.entities.EventoWithRecinto;
import es.upsa.tfg.gestioneventos.domain.exceptions.EventosAppException;

import java.util.Optional;

public interface EventosDao
{
    Optional<EventoWithRecinto> findById(String id) throws EventosAppException;
    void updateEvento(String id, DescontarEntradasDto dto ) throws EventosAppException;
}
