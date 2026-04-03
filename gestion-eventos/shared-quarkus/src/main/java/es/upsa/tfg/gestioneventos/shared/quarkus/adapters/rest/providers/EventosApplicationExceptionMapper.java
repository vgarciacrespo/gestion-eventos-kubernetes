package es.upsa.tfg.gestioneventos.shared.quarkus.adapters.rest.providers;

import es.upsa.tfg.gestioneventos.domain.dtos.ErrorDto;
import es.upsa.tfg.gestioneventos.domain.exceptions.EventosAppException;
import es.upsa.tfg.gestioneventos.domain.exceptions.NotFoundAppException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class EventosApplicationExceptionMapper implements ExceptionMapper<EventosAppException>
{

    @Override
    public Response toResponse(EventosAppException exception) {
        String message = exception.getMessage();
        Response.Status status = (exception instanceof NotFoundAppException) ? Response.Status.NOT_FOUND : Response.Status.INTERNAL_SERVER_ERROR;
        return Response.status(status)
                .entity(ErrorDto.builder()
                        .withMensaje(message)
                        .build()
                )
                .type(MediaType.APPLICATION_JSON)
                .build();
    }
}
