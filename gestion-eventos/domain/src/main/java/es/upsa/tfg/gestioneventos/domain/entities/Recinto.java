package es.upsa.tfg.gestioneventos.domain.entities;

import lombok.*;

@Data
@Builder(setterPrefix = "with")
@NoArgsConstructor
@AllArgsConstructor
@With
public class Recinto
{
    private String id_recinto;
    private String nombre;
    private String ubicacion;
    private int capacidad_total;
}
