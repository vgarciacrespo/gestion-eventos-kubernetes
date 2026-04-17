package es.upsa.tfg.gestioneventos.domain.dtos;

import es.upsa.tfg.gestioneventos.domain.entities.EstadoReserva;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder(setterPrefix = "with")
@NoArgsConstructor
@AllArgsConstructor
@With
public class ReservaDto
{
    private String id_evento;
    private String nombre_cliente;
    private LocalDateTime fecha_reserva;
    private int cantidad_entradas;
    private double precio_total;
}
