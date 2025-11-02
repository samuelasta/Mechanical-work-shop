package co.uniquindio.edu.dto.orden;

import java.time.LocalDateTime;

public record DetalleOrdenDTO(String nombre1,
                              String nombre2,
                              String apellido1,
                              String apellido2,
                              String tipo,
                              double costoUnitario,
                              String descripcion,                              
                              String rol,
                              int horasTrabajadas,
                              LocalDateTime fechaAsignacion

                              ) {
}
