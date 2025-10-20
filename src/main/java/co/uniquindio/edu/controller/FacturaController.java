package co.uniquindio.edu.controller;

import co.uniquindio.edu.dto.factura.ObtenerFacturaDTO;
import co.uniquindio.edu.dto.response.ResponseDTO;
import co.uniquindio.edu.services.FacturasService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/facturas")
@RequiredArgsConstructor
public class FacturaController {

    //inyeccion de dependencias
    private final FacturasService facturaService;

    // obtener una factura por el id de la orden
    @GetMapping("/{idOrden}/ordenes")
    public ResponseEntity<ResponseDTO<ObtenerFacturaDTO>> obtenerFactura(@PathVariable String idOrden) {
        ObtenerFacturaDTO factura = facturaService.obtenerFactura(idOrden);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO<>(false, factura));
    }

    // obtener lista de facturas que tiene un vehiculo (buscamos por placa)
    @GetMapping("/{placa}/vehiculos")
    public ResponseEntity<ResponseDTO<List<ObtenerFacturaDTO>>> obtenerFacturasPorPlaca(@PathVariable String placa) {
        List<ObtenerFacturaDTO> facturas = facturaService.listaFacturasVehiculo(placa);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO<>(false, facturas));
    }

    // obtener todas las facturas del taller
    @GetMapping()
    public ResponseEntity<ResponseDTO<List<ObtenerFacturaDTO>>> obtenerFacturas() {
        List<ObtenerFacturaDTO> facturas = facturaService.listaFacturas();
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO<>(false, facturas));
    }





}
