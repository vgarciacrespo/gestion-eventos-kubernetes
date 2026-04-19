package es.upsa.tfg.gestioneventos.wsreservas.adapters.input.rest;

import es.upsa.tfg.gestioneventos.domain.dtos.EventoDto;
import es.upsa.tfg.gestioneventos.domain.dtos.ReservaDto;
import es.upsa.tfg.gestioneventos.domain.entities.Evento;
import es.upsa.tfg.gestioneventos.domain.entities.Reserva;
import es.upsa.tfg.gestioneventos.domain.entities.ReservaWithEvento;
import es.upsa.tfg.gestioneventos.domain.exceptions.EventosAppException;
import es.upsa.tfg.gestioneventos.domain.mappers.Mappers;
import es.upsa.tfg.gestioneventos.wsreservas.application.usecases.AddReservaUseCase;
import es.upsa.tfg.gestioneventos.wsreservas.application.usecases.GetReservaByIdUseCase;
import es.upsa.tfg.gestioneventos.wsreservas.application.usecases.GetReservasUseCase;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;

import java.net.URI;
import java.util.List;


@Path("/reservas")
@RequestScoped
public class ReservasReseource
{
    @Inject
    GetReservasUseCase getReservasUseCase;
    @Inject
    GetReservaByIdUseCase getReservaByIdUseCase;
    @Inject
    AddReservaUseCase addReservaUseCase;
    @Inject
    UriInfo uriInfo;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getReservas() throws EventosAppException
    {
        List<Reserva> reservas = getReservasUseCase.execute();

        return Response.ok()
                .entity(reservas)
                .build();
    }
    @Path("/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getReservasById(@PathParam("id")String id) throws EventosAppException
    {
        ReservaWithEvento reservaWithEvento = getReservaByIdUseCase.execute(id);

        return Response.ok()
                .entity(reservaWithEvento)
                .build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addReserva(ReservaDto reservaDto) throws EventosAppException
    {
        Reserva reserva = Mappers.toReserva(reservaDto );
        Reserva insertedReserva = addReservaUseCase.execute(reserva);
        return Response.created(createEventoURI(insertedReserva))
                .entity(insertedReserva)
                .build();
    }

    private URI createEventoURI(Reserva reserva)
    {
        return uriInfo.getBaseUriBuilder()
                .path("/reservas")
                .path(reserva.getId_reserva())
                .build();

    }





}
