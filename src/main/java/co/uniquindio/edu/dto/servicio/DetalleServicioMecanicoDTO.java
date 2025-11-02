package co.uniquindio.edu.dto.servicio;

import java.time.LocalDateTime;

public record DetalleServicioMecanicoDTO(String rol,
                                         int horasTrabajadas,
                                         LocalDateTime fechaAsignacion
                                         ) {
}
