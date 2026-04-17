package es.upsa.tfg.gestioneventos.domain.exceptions;

public class ReservaNotFoundException extends EventosAppException
{
    public ReservaNotFoundException() {
        super("La reserva no existe");
    }
}
