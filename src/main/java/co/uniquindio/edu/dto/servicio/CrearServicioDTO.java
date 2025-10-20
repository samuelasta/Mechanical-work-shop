package co.uniquindio.edu.dto.servicio;

import co.uniquindio.edu.model.enums.TipoServicio;

public record CrearServicioDTO(TipoServicio tipoServicio,
                               String descripcion,
                               double costoUnitario) {
}
