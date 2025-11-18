package co.uniquindio.edu.dto;

import java.time.LocalDateTime;

public record RangoFechasDTO(
    LocalDateTime fechaInicio,
    LocalDateTime fechaFin
) {
}