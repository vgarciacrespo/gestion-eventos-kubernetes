package es.upsa.tfg.gestioneventos.wsreservas.adapters.output.persistence.providers;

import es.upsa.tfg.gestioneventos.domain.dtos.ErrorDescriptor;
import es.upsa.tfg.gestioneventos.domain.exceptions.EventoNotFoundException;
import es.upsa.tfg.gestioneventos.domain.exceptions.EventosAppException;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.ext.ResponseExceptionMapper;

public class ReservasResponseExceptionMapper implements ResponseExceptionMapper<EventosAppException> {
    @Override
    public EventosAppException toThrowable(Response response)
    {
        return  switch ( response.getStatusInfo().toEnum() )
        {
            case NOT_FOUND ->  new EventoNotFoundException(  response.readEntity(ErrorDescriptor.class).getMessage()   );


            default -> {
                ErrorDescriptor errorDescriptor2 = response.readEntity(ErrorDescriptor.class);
                String message = errorDescriptor2.getMessage();
                yield new EventosAppException(message);
            }
        };
    }
}
