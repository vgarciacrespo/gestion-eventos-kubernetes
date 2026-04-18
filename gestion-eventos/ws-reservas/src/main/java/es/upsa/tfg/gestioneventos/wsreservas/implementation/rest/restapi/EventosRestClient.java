package es.upsa.tfg.gestioneventos.wsreservas.implementation.rest.restapi;

import es.upsa.tfg.gestioneventos.domain.dtos.DescontarEntradasDto;
import es.upsa.tfg.gestioneventos.domain.entities.Evento;
import es.upsa.tfg.gestioneventos.domain.entities.EventoWithRecinto;
import es.upsa.tfg.gestioneventos.domain.exceptions.EventosAppException;
import es.upsa.tfg.gestioneventos.wsreservas.adapters.output.persistence.providers.ReservasResponseExceptionMapper;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.annotation.RegisterProvider;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@RegisterRestClient(configKey = "rest.client.eventos")
@RegisterProvider(ReservasResponseExceptionMapper.class)
@Path("/eventos")
public interface EventosRestClient
{

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    EventoWithRecinto requestEventoById(@PathParam("id")String id)throws EventosAppException;

    @POST
    @Path("/{id}/descontar")
    @Consumes(MediaType.APPLICATION_JSON)
    void descontarEntradas(@PathParam("id") String id, DescontarEntradasDto dto) throws EventosAppException;
}
