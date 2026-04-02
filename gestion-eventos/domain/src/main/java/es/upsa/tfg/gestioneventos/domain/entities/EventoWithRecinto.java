package es.upsa.tfg.gestioneventos.domain.entities;


import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(setterPrefix = "with")
@With
public class EventoWithRecinto
{
    private Evento evento;
    private Recinto recinto;
}
