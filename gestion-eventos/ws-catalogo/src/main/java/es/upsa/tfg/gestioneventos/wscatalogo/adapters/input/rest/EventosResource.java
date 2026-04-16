package es.upsa.tfg.gestioneventos.wscatalogo.adapters.input.rest;

import es.upsa.tfg.gestioneventos.domain.dtos.EventoDto;
import es.upsa.tfg.gestioneventos.domain.entities.Evento;
import es.upsa.tfg.gestioneventos.domain.entities.EventoWithRecinto;
import es.upsa.tfg.gestioneventos.domain.exceptions.EventosAppException;
import es.upsa.tfg.gestioneventos.domain.mappers.Mappers;
import es.upsa.tfg.gestioneventos.wscatalogo.application.usecases.eventos.*;
import es.upsa.tfg.gestioneventos.wscatalogo.application.usecases.recintos.GetRecintosUseCase;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;

import java.net.URI;

@Path("/eventos")
@RequestScoped
public class EventosResource
{
    @Inject
    GetEventosUseCase getEquiposUseCase;
    @Inject
    GetRecintosUseCase getRecintosUseCase;
    @Inject
    GetEventoWithRecintoByIdUseCase getEventoWithRecintoByIdUseCase;
    @Inject
    AddEventoUseCase addEventoUseCase;
    @Inject
    UpdateEventoUseCase updateEventoUseCase;
    @Inject
    RemoveEventoUseCase removeEventoUseCase;

    @Inject
    UriInfo uriInfo;

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

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addEvento(EventoDto eventoDto) throws EventosAppException
    {
        Evento evento = Mappers.toEvento(eventoDto);
        Evento insertedEvento = addEventoUseCase.execute(evento);
        return Response.created(createEventoURI(insertedEvento))
                       .entity(insertedEvento)
                       .build();
    }


    @Path("/{id}")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateEvento(@PathParam("id") String id, EventoDto eventoDto) throws EventosAppException
    {
        Evento evento = Mappers.toEvento(eventoDto).withId_evento(id);
        Evento updatedEvento = updateEventoUseCase.execute(evento);
        return Response.ok()
                       .entity( updatedEvento )
                       .build();
    }
    @Path("/{id}")
    @DELETE
    public Response deleteEventoById(@PathParam("id") String id) throws EventosAppException
    {
        removeEventoUseCase.execute(id);
        return Response.noContent()
                       .build();
    }

    private URI createEventoURI(Evento evento)
    {
        return uriInfo.getBaseUriBuilder()
                .path("/eventos")
                .path(evento.getId_evento())
                .build();

    }


}
