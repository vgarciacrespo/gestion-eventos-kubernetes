package es.upsa.tfg.gestioneventos.wscatalogo.adapters.input.rest;

import es.upsa.tfg.gestioneventos.domain.entities.EventoWithRecinto;
import es.upsa.tfg.gestioneventos.domain.exceptions.EventosAppException;
import es.upsa.tfg.gestioneventos.wscatalogo.application.usecases.GetEventoWithRecintoByIdUseCase;
import es.upsa.tfg.gestioneventos.wscatalogo.application.usecases.GetEventosUseCase;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/eventos")
@RequestScoped
public class EventosResource
{
    @Inject
    GetEventosUseCase getEquiposUseCase;
    @Inject
    GetEventoWithRecintoByIdUseCase getEventoWithRecintoByIdUseCase;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getEventos() throws EventosAppException {
        return Response.ok()
                .entity(getEquiposUseCase.execute())
                .build();
    }
    @Path("/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getEventosById(@PathParam("id")String id) throws EventosAppException
    {
        EventoWithRecinto eventoWithRecinto = getEventoWithRecintoByIdUseCase.execute(id);
        return Response.ok()
                .entity(eventoWithRecinto)
                .build();
    }



}
