package es.upsa.tfg.gestioneventos.wscatalogo.adapters.output.persistence;

import es.upsa.tfg.gestioneventos.domain.entities.Evento;
import es.upsa.tfg.gestioneventos.domain.exceptions.EventosAppException;
import es.upsa.tfg.gestioneventos.domain.exceptions.SQLEventosAppException;

import java.util.List;

public interface Dao
{
    List<Evento> findEventos() throws EventosAppException, SQLEventosAppException;
}
