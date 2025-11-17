package co.uniquindio.edu.dto.orden;

import java.time.LocalDateTime;

public record ActualizarOrdenDTO(String descripcion,
                                 LocalDateTime fechaIngreso, LocalDateTime fechaSalida,
                                 String idVehiculo) {
}
