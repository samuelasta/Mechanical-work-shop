package co.uniquindio.edu.dto.servicio;

import co.uniquindio.edu.model.enums.TipoServicio;

import java.time.LocalDate;

public record ObtenerServicioDTO(String id,
                                 TipoServicio tipoServicio,
                                 String descripcion,
                                 LocalDate fechaCreacion,
                                 int costoUnitario
) {
}
