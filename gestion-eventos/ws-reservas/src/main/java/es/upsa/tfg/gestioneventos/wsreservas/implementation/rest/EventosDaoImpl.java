package es.upsa.tfg.gestioneventos.wsreservas.implementation.rest;

import es.upsa.tfg.gestioneventos.domain.dtos.DescontarEntradasDto;
import es.upsa.tfg.gestioneventos.domain.entities.Evento;
import es.upsa.tfg.gestioneventos.domain.entities.EventoWithRecinto;
import es.upsa.tfg.gestioneventos.domain.exceptions.EventoNotFoundException;
import es.upsa.tfg.gestioneventos.domain.exceptions.EventosAppException;
import es.upsa.tfg.gestioneventos.wsreservas.adapters.output.rest.EventosDao;
import es.upsa.tfg.gestioneventos.wsreservas.implementation.rest.restapi.EventosRestClient;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.util.Optional;

@ApplicationScoped
public class EventosDaoImpl implements EventosDao
{
    @Inject
    @RestClient
    EventosRestClient restClient;
    @Override
    public Optional<EventoWithRecinto> findById(String id) throws EventosAppException {
        try {
            return Optional.of( restClient.requestEventoById(id) );
        }catch (EventoNotFoundException exception)
        {
            return Optional.empty();
        }
    }

    @Override
    public void updateEvento(String id, DescontarEntradasDto dto) throws EventosAppException {
        restClient.descontarEntradas(id, dto);
    }
}
