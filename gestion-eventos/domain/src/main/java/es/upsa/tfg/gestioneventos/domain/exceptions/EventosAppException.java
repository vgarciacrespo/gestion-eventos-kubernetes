package es.upsa.tfg.gestioneventos.domain.exceptions;

public class EventosAppException extends Exception
{
    public EventosAppException() {
    }

    public EventosAppException(String message) {
        super(message);
    }

    public EventosAppException(String message, Throwable cause) {
        super(message, cause);
    }

    public EventosAppException(Throwable cause) {
        super(cause);
    }

    public EventosAppException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
