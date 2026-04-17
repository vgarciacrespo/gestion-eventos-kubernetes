package es.upsa.tfg.gestioneventos.domain.entities;

import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder(setterPrefix = "with")
@NoArgsConstructor
@AllArgsConstructor
@With
public class Reserva
{
    private String id_reserva;
    private String id_evento;
    private String nombre_cliente;
    private LocalDateTime fecha_reserva;
    private int cantidad_entradas;
    private double precio_total;
    private EstadoReserva estadoReserva;

}
