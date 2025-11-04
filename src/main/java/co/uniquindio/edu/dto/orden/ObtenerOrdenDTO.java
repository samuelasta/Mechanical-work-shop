package co.uniquindio.edu.dto.orden;

import co.uniquindio.edu.model.enums.EstadoOrden;

import java.time.LocalDateTime;

public record ObtenerOrdenDTO(String id,
                              String descripcion,
                              LocalDateTime fechaIngreso,
                              LocalDateTime fechaSalida, // si es null, sacar un mensaje como que no ha salido
                              EstadoOrden estado,
                              String diagnosticoInicial,
                              String diagnosticoFinal,
                              String placa
                              ) {


    
    //para la ver la lista de todas las ordenes, se devuelven una lista de este DTO
}
