package es.upsa.tfg.gestioneventos.domain.dtos;

import lombok.*;

@Data
@Builder(setterPrefix = "with")
@NoArgsConstructor
@AllArgsConstructor
@With
public class RecintoDto {
    private String nombre;
    private String ubicacion;
    private int capacidad_total;
}
