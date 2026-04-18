package es.upsa.tfg.gestioneventos.domain.dtos;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(setterPrefix = "with")
@With
public class DescontarEntradasDto {
    private int cantidad;
}
