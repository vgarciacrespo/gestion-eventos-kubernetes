package es.upsa.tfg.gestioneventos.wscatalogo.adapters.input.rest;

import es.upsa.tfg.gestioneventos.domain.dtos.RecintoDto;
import es.upsa.tfg.gestioneventos.domain.entities.Recinto;
import es.upsa.tfg.gestioneventos.domain.exceptions.EventosAppException;
import es.upsa.tfg.gestioneventos.domain.exceptions.RecintoNotFoundException;
import es.upsa.tfg.gestioneventos.domain.mappers.Mappers;
import es.upsa.tfg.gestioneventos.wscatalogo.application.usecases.recintos.*;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;

import java.net.URI;
import java.util.Optional;

@Path("/recintos")
@RequestScoped
public class RecintosResource
{
    @Inject
    GetRecintosUseCase getRecintosUseCase;
    @Inject
    AddRecintoUseCase addRecintoUseCase;
    @Inject
    UpdateRecintoUseCase updateRecintoUseCase;
    @Inject
    RemoveRecintoUseCase removeRecintoUseCase;
    @Inject
    GetRecintoByIdUseCase getRecintoByIdUseCase;
    @Inject
    UriInfo uriInfo;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getRecintos() throws EventosAppException {
        return Response.ok()
                .entity(getRecintosUseCase.execute())
                .build();
    }
    @Path("/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getRecintosById(@PathParam("id")String id) throws EventosAppException
    {
        Optional<Recinto> optRecinto = getRecintoByIdUseCase.execute(id);
        return optRecinto.map(recinto -> Response.ok()
                        .entity(recinto)
                        .build()
                )
                .orElseThrow(RecintoNotFoundException::new);
    }
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addRecinto(RecintoDto recintoDto) throws EventosAppException
    {
        Recinto recinto = Mappers.toRecinto(recintoDto);
        Recinto insertedRecinto = addRecintoUseCase.execute(recinto);
        return Response.created(createRecintoURI(insertedRecinto))
                .entity(insertedRecinto)
                .build();
    }

    @Path("/{id}")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateRecinto(@PathParam("id") String id, RecintoDto recintoDto) throws EventosAppException
    {
        Recinto recinto = Mappers.toRecinto(recintoDto).withId_recinto(id);
        Recinto updatedRecinto = updateRecintoUseCase.execute(recinto);
        return Response.ok()
                       .entity( updatedRecinto )
                       .build();
    }
    @Path("/{id}")
    @DELETE
    public Response deleteRecintoById(@PathParam("id") String id) throws EventosAppException
    {
        removeRecintoUseCase.execute(id);
        return Response.noContent()
                       .build();
    }
    private URI createRecintoURI(Recinto recinto)
    {
        return uriInfo.getBaseUriBuilder()
                      .path("/recintos")
                      .path(recinto.getId_recinto())
                      .build();
    }






}
