package es.upsa.tfg.gestioneventos.wsreservas.adapters.input.rest;

import es.upsa.tfg.gestioneventos.domain.entities.Reserva;
import es.upsa.tfg.gestioneventos.domain.entities.ReservaWithEvento;
import es.upsa.tfg.gestioneventos.domain.exceptions.EventosAppException;
import es.upsa.tfg.gestioneventos.wsreservas.application.usecases.GetReservaByIdUseCase;
import es.upsa.tfg.gestioneventos.wsreservas.application.usecases.GetReservasUseCase;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;


@Path("/reservas")
@RequestScoped
public class ReservasReseource
{
    @Inject
    GetReservasUseCase getReservasUseCase;
    @Inject
    GetReservaByIdUseCase getReservaByIdUseCase;

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



}
