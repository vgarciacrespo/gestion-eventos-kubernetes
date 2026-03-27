package es.upsa.tfg.gestioneventos.wscatalogo.application.respository;

import es.upsa.tfg.gestioneventos.domain.entities.Evento;
import es.upsa.tfg.gestioneventos.domain.exceptions.EventosAppException;

import java.util.List;

public interface Repository
{
    List<Evento> getEventos() throws EventosAppException;
}
