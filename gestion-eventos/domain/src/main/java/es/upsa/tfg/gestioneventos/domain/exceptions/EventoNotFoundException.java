package es.upsa.tfg.gestioneventos.domain.exceptions;

public class EventoNotFoundException extends EventosAppException {
    public EventoNotFoundException() {
        super("El evento no existe");
    }
}
