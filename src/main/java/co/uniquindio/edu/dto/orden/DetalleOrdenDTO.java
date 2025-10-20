package co.uniquindio.edu.dto.orden;

import java.time.LocalDate;

public record DetalleOrdenDTO(String mecanicoId,
                              String nombreMecanico,
                              String idServicio,
                              String nombreServicio,
                              String rol,
                              int horasTrabajadas,
                              LocalDate fechaAsignacion

                              ) {
}
