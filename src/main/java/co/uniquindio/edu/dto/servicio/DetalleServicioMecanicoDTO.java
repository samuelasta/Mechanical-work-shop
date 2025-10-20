package co.uniquindio.edu.dto.servicio;

import java.time.LocalDate;

public record DetalleServicioMecanicoDTO(String rol,
                                         int horasTrabajadas,
                                         LocalDate fechaAsignacion
                                         ) {
}
