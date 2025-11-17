package co.uniquindio.edu.services.impl;

import co.uniquindio.edu.dto.factura.ObtenerFacturaDTO;
import co.uniquindio.edu.repository.FacturaRepository;
import co.uniquindio.edu.services.FacturasService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@RequiredArgsConstructor
@Service
public class FacturasServiceImpl implements FacturasService {

    private final FacturaRepository facturaRepository;

    @Override
    public ObtenerFacturaDTO obtenerFactura(String idOrden) {
        return facturaRepository.obtenerFactura(idOrden);
    }

    @Override
    public List<ObtenerFacturaDTO> listaFacturasVehiculo(String placa) {
        return facturaRepository.listaFacturasVehiculo(placa);
    }

    @Override
    public List<ObtenerFacturaDTO> obtenerFacturasPorCliente(String id) {
        return facturaRepository.listaFacturasCliente(id);
    }

    @Override
    public void eliminarFactura(String idFactura, String idOrden) {
        facturaRepository.eliminarFactura(idFactura, idOrden);
    }

    // luego, es para ver todas las facturas
    @Override
    public List<ObtenerFacturaDTO> listaFacturas() {
        return facturaRepository.listaFacturas();
    }

}
