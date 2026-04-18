package es.upsa.tfg.gestioneventos.wscatalogo.domain.exceptions;

import es.upsa.tfg.gestioneventos.domain.exceptions.EventosAppException;

public class StockInsuficienteException extends EventosAppException
{
    public StockInsuficienteException() {
        super("Error stock insuficiente: " );
    }
}
