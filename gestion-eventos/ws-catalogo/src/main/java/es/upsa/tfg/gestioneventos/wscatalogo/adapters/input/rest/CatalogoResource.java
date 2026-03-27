package es.upsa.tfg.gestioneventos.wscatalogo.adapters.input.rest;

import jakarta.enterprise.context.RequestScoped;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/eventos")
@RequestScoped
public class CatalogoResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getEventos() {
        return "Hello RESTEasy";
    }
}
