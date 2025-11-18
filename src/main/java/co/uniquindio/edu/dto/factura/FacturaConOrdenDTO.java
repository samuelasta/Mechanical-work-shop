package co.uniquindio.edu.dto.factura;

import co.uniquindio.edu.model.enums.EstadoOrden;

import java.time.LocalDateTime;

public record FacturaConOrdenDTO(
        // Datos de la factura
        String idFactura,
        String consecutivo,
        String estadoFactura,
        LocalDateTime fechaEmision,
        double impuestos,
        double valorTotal,
        // Datos de la orden
        String idOrden,
        String descripcionOrden,
        LocalDateTime fechaIngreso,
        LocalDateTime fechaSalida,
        EstadoOrden estadoOrden,
        String diagnosticoInicial,
        String diagnosticoFinal,
        String placaVehiculo
) {
}