package es.upsa.tfg.gestioneventos.wscatalogo.adapters.output.persistence;

import es.upsa.tfg.gestioneventos.domain.entities.Evento;
import es.upsa.tfg.gestioneventos.domain.entities.Recinto;
import es.upsa.tfg.gestioneventos.domain.exceptions.EventosAppException;

import java.util.List;
import java.util.Optional;

public interface Dao
{
    List<Evento> findEventos() throws EventosAppException;
    List<Recinto> findRecintos() throws EventosAppException;
    Optional<Evento> findEventoById(String id) throws EventosAppException;
    Optional<Recinto> findRecintoById(String id) throws EventosAppException;
    Evento insertEvento(Evento evento) throws EventosAppException;
    Recinto insertRecinto(Recinto recinto) throws EventosAppException;
    Optional<Evento> updateEvento(Evento evento) throws EventosAppException;
    void deleteEvento(String id) throws EventosAppException;

}
