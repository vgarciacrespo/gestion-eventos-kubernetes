package es.upsa.tfg.gestioneventos.domain.entities;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(setterPrefix = "with")
@With
public class ReservaWithEvento
{
    private Reserva reserva;
    private EventoWithRecinto eventoWithRecinto;
}
