package co.uniquindio.edu.dto.factura;

import co.uniquindio.edu.model.enums.EstadoFactura;

import java.time.LocalDateTime;

public record ObtenerFacturaDTO(String id,
                                String consecutivo,
                                EstadoFactura estado,
                                LocalDateTime fechaEmision,
                                double impuestos,
                                double valorTotal,
                                String ordenId) {
}
