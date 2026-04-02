package es.upsa.tfg.gestioneventos.wscatalogo.adapters.output.persistence;

import es.upsa.tfg.gestioneventos.domain.entities.Evento;
import es.upsa.tfg.gestioneventos.domain.entities.Recinto;
import es.upsa.tfg.gestioneventos.domain.exceptions.EventosAppException;

import java.util.List;
import java.util.Optional;

public interface Dao
{
    List<Evento> findEventos() throws EventosAppException;
    Optional<Evento> findEventoById(String id) throws EventosAppException;
    Optional<Recinto> findRecintoById(String id) throws EventosAppException;
}
