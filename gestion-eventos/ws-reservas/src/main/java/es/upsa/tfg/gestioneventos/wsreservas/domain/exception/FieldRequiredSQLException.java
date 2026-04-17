package es.upsa.tfg.gestioneventos.wsreservas.domain.exception;

import es.upsa.tfg.gestioneventos.domain.exceptions.EventosAppException;

public class FieldRequiredSQLException extends EventosAppException
{
    public FieldRequiredSQLException(String nombre)
    {
        super("El campo " + nombre + " es obligatorio");
    }
}
