package es.upsa.tfg.gestioneventos.wscatalogo.adapters.input.rest;

import es.upsa.tfg.gestioneventos.domain.dtos.RecintoDto;
import es.upsa.tfg.gestioneventos.domain.entities.Recinto;
import es.upsa.tfg.gestioneventos.domain.exceptions.EventosAppException;
import es.upsa.tfg.gestioneventos.domain.mappers.Mappers;
import es.upsa.tfg.gestioneventos.wscatalogo.application.usecases.recintos.AddRecintoUseCase;
import es.upsa.tfg.gestioneventos.wscatalogo.application.usecases.recintos.GetRecintosUseCase;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;

import java.net.URI;

@Path("/recintos")
@RequestScoped
public class RecintosResource
{
    @Inject
    GetRecintosUseCase getRecintosUseCase;
    @Inject
    AddRecintoUseCase addRecintoUseCase;
    @Inject
    UriInfo uriInfo;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getRecintos() throws EventosAppException {
        return Response.ok()
                .entity(getRecintosUseCase.execute())
                .build();
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
    private URI createRecintoURI(Recinto recinto)
    {
        return uriInfo.getBaseUriBuilder()
                .path("/recintos")
                .path(recinto.getId_recinto())
                .build();
    }






}
