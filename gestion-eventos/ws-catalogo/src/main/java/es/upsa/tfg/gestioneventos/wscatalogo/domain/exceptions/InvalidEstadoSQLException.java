package es.upsa.tfg.gestioneventos.wscatalogo.domain.exceptions;

import es.upsa.tfg.gestioneventos.domain.exceptions.EventosAppException;

public class InvalidEstadoSQLException extends EventosAppException
{
    public InvalidEstadoSQLException() {
        super("El estado no es valido");
    }
}
