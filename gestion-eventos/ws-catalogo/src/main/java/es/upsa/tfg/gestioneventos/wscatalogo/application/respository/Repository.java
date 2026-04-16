package es.upsa.tfg.gestioneventos.wscatalogo.application.respository;

import es.upsa.tfg.gestioneventos.domain.entities.Evento;
import es.upsa.tfg.gestioneventos.domain.entities.Recinto;
import es.upsa.tfg.gestioneventos.domain.exceptions.EventosAppException;

import java.util.List;
import java.util.Optional;

public interface Repository
{
    List<Evento> getEventos() throws EventosAppException;
    Optional<Evento> getEventoById(String id) throws EventosAppException;
    Evento save(Evento evento) throws EventosAppException;
    void removeEventoById(String id) throws EventosAppException;


    List<Recinto> getRecintos() throws EventosAppException;
    Optional<Recinto> getRecintoById(String id) throws EventosAppException;
    Recinto save(Recinto recinto) throws EventosAppException;
    void removeRecintoById(String id) throws EventosAppException;
}
