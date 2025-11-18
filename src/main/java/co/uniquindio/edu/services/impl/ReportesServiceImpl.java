package co.uniquindio.edu.services.impl;

import co.uniquindio.edu.dto.factura.FacturaConOrdenDTO;
import co.uniquindio.edu.dto.mecanico.ObtenerMecanicoDTO;
import co.uniquindio.edu.dto.mecanico.ObtenerMecanicoOrdenDTO;
import co.uniquindio.edu.dto.mecanico.PromedioHorasDTO;
import co.uniquindio.edu.dto.mecanico.MecanicoPendienteDTO;
import co.uniquindio.edu.dto.orden.ObtenerOrdenDTO;
import co.uniquindio.edu.dto.repuesto.ObtenerRepuestoDTO;
import co.uniquindio.edu.dto.servicio.ObtenerServicioDTO;
import co.uniquindio.edu.repository.FacturaRepository;
import co.uniquindio.edu.repository.OrdenesRepository;
import co.uniquindio.edu.services.OrdenesService;
import co.uniquindio.edu.services.ReportesService;
import co.uniquindio.edu.services.RepuestosService;
import co.uniquindio.edu.services.ServiciosService;
import co.uniquindio.edu.util.PdfGeneratorUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class ReportesServiceImpl implements ReportesService {

    private final OrdenesService ordenesService;
    private final ServiciosService serviciosService;
    private final RepuestosService repuestosService;
    private final FacturaRepository facturaRepository;
    private final OrdenesRepository ordenesRepository;


    // REPORTE 1
    @Override
    public List<ObtenerOrdenDTO> obtenerOrdenes() {
        return ordenesService.listaOrdenes();
    }

    // REPORTE 2
    @Override
    public List<ObtenerRepuestoDTO> listaRepuestos() {
        return repuestosService.listaRepuestos();
    }

    // REPORTE 3
    @Override
    public List<ObtenerServicioDTO> listaServicios() {
        return serviciosService.listaServicios();
    }

    // REPORTE 4
    @Override
    public List<ObtenerMecanicoOrdenDTO> obtenerMecanicos(String id) {
        return ordenesService.obtenerMecanicosPorOrden(id);
    }


    // REPORTE 5
    @Override
    public List<ObtenerOrdenDTO> listaOrdenesCliente(String id) {
        return ordenesService.listaOrdenesPorCliente(id);
    }

    @Override
    public Map<String, Double> obtenerIngresosPorOrdenFinalizada() {
        return ordenesService.obtenerIngresosPorOrdenFinalizadas();
    }

    @Override
    public List<PromedioHorasDTO> obtenerPromedioHorasPorMecanico() {
        return ordenesService.obtenerPromedioHorasPorMecanico();
    }

    @Override
    public List<ObtenerOrdenDTO> listaOrdenesRepuesto(String idRepuesto) {
        return ordenesService.listaOrdenesRepuesto(idRepuesto);
    }

    @Override
    public List<FacturaConOrdenDTO> listaFacturasConOrdenesEnRangoFechas(LocalDateTime fechaInicio, LocalDateTime fechaFin) {
        return facturaRepository.listaFacturasConOrdenesEnRangoFechas(fechaInicio, fechaFin);
    }

    @Override
    public List<MecanicoPendienteDTO> listaMecanicosConOrdenesPendientesYRepuestos() {
        return ordenesRepository.listaMecanicosConOrdenesPendientesYRepuestos();
    }

}
