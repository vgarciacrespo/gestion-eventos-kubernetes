package es.upsa.tfg.gestioneventos.wscatalogo.domain.exceptions;

import es.upsa.tfg.gestioneventos.domain.exceptions.EventosAppException;

public class FieldRequiredSQLException extends EventosAppException {
    public FieldRequiredSQLException(String nombre)
    {
        super("El campo " + nombre + " es obligatorio");
    }
}
