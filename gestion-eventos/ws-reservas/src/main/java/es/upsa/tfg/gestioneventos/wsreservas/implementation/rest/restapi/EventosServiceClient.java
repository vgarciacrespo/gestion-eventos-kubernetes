package es.upsa.tfg.gestioneventos.wsreservas.implementation.rest.restapi;

import es.upsa.tfg.gestioneventos.domain.dtos.DescontarEntradasDto;
import es.upsa.tfg.gestioneventos.domain.entities.Evento;
import es.upsa.tfg.gestioneventos.domain.exceptions.EventosAppException;
import es.upsa.tfg.gestioneventos.wsreservas.adapters.output.persistence.providers.ReservasResponseExceptionMapper;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.annotation.RegisterProvider;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@RegisterRestClient(configKey = "rest.client.eventos")
@RegisterProvider(ReservasResponseExceptionMapper.class)
@Path("/eventos")
public interface EventosServiceClient
{

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    Evento requestEventoById(@PathParam("id")String id)throws EventosAppException;

    @Path("/{id}/descontar")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    void descontarEntradas(@PathParam("id") String id, DescontarEntradasDto dto) throws EventosAppException;

}
