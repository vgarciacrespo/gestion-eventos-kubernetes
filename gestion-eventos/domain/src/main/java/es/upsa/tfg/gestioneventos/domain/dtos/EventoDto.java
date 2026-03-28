package es.upsa.tfg.gestioneventos.domain.dtos;

import es.upsa.tfg.gestioneventos.domain.entities.Estado;
import es.upsa.tfg.gestioneventos.domain.entities.Recinto;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder(setterPrefix = "with")
@NoArgsConstructor
@AllArgsConstructor
@With
public class EventoDto
{
    private String nombre;
    private String descripcion;
    private String disciplina;
    private String recinto;
    private LocalDateTime fecha_inicio;
    private LocalDateTime fecha_final;
    private double precio;
    private int capacidad_max;
    private Estado estado;

}
