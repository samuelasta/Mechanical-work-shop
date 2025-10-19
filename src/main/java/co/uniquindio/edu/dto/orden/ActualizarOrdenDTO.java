package co.uniquindio.edu.dto.orden;

import java.time.LocalDateTime;

public record ActualizarOrdenDTO(String descripcion, String placa,
                                 LocalDateTime fechaIngreso, LocalDateTime fechaSalida ) {
}
