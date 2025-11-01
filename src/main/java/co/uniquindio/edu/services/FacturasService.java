package co.uniquindio.edu.services;

import co.uniquindio.edu.dto.factura.ObtenerFacturaDTO;

import java.util.List;

public interface FacturasService {

    ObtenerFacturaDTO obtenerFactura(String idOrden);
    List<ObtenerFacturaDTO> listaFacturasVehiculo(String placa);
    List<ObtenerFacturaDTO> listaFacturas();
    List<ObtenerFacturaDTO> obtenerFacturasPorCliente(String id);
}
