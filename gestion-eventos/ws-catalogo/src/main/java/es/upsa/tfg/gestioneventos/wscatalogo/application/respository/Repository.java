package es.upsa.tfg.gestioneventos.wscatalogo.application.respository;

import es.upsa.tfg.gestioneventos.domain.entities.Evento;
import es.upsa.tfg.gestioneventos.domain.entities.Recinto;
import es.upsa.tfg.gestioneventos.domain.exceptions.EventosAppException;

import java.util.List;
import java.util.Optional;

public interface Repository
{
    List<Evento> getEventos() throws EventosAppException;
    Optional<Evento> getEvento(String id) throws EventosAppException;
    Optional<Recinto> getRecinto(String id) throws EventosAppException;
}
