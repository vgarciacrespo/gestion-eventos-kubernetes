package es.upsa.tfg.gestioneventos.domain.exceptions;

public class RecintoNotFoundException extends EventosAppException
{
    public  RecintoNotFoundException()
    {
        super("El recinto no existe");
    }
}
