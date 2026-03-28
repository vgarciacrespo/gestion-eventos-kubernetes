package es.upsa.tfg.gestioneventos.domain.exceptions;

import java.sql.SQLException;

public class SQLEventosAppException extends EventosAppException
{
    public SQLEventosAppException(Throwable cause) {
        super(cause);
    }
}
