package es.upsa.tfg.gestioneventos.wscatalogo.domain.exceptions;

import es.upsa.tfg.gestioneventos.domain.exceptions.EventosAppException;

public class RecintoRelacionadoSQLException extends EventosAppException {
    public RecintoRelacionadoSQLException() {
        super("El recinto tiene evento asociado");
    }
}
