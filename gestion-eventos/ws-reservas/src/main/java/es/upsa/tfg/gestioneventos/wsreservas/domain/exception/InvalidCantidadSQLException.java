package es.upsa.tfg.gestioneventos.wsreservas.domain.exception;

import es.upsa.tfg.gestioneventos.domain.exceptions.EventosAppException;

public class InvalidCantidadSQLException extends EventosAppException
{
    public InvalidCantidadSQLException(String message) {
        super(message);
    }
}
