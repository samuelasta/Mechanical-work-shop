package co.uniquindio.edu.services.impl;

import co.uniquindio.edu.dto.factura.ObtenerFacturaDTO;
import co.uniquindio.edu.services.FacturasService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FacturasServiceImpl implements FacturasService {


    @Override
    public ObtenerFacturaDTO obtenerFactura(String idOrden) {
        return null;
    }

    @Override
    public List<ObtenerFacturaDTO> listaFacturasVehiculo(String placa) {
        return List.of();
    }

    @Override
    public List<ObtenerFacturaDTO> listaFacturas() {
        return List.of();
    }

    @Override
    public List<ObtenerFacturaDTO> obtenerFacturasPorCliente(String id) {
        return List.of();
    }
}
