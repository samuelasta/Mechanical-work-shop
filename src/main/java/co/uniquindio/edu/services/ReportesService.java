package co.uniquindio.edu.services;

import co.uniquindio.edu.dto.dashboard.DashBoardDTO;
import co.uniquindio.edu.dto.factura.FacturaConOrdenDTO;
import co.uniquindio.edu.dto.mecanico.ObtenerMecanicoDTO;
import co.uniquindio.edu.dto.mecanico.ObtenerMecanicoOrdenDTO;
import co.uniquindio.edu.dto.mecanico.PromedioHorasDTO;
import co.uniquindio.edu.dto.mecanico.MecanicoPendienteDTO;
import co.uniquindio.edu.dto.orden.ObtenerOrdenDTO;
import co.uniquindio.edu.dto.repuesto.ObtenerRepuestoDTO;
import co.uniquindio.edu.dto.servicio.ObtenerServicioDTO;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface ReportesService {

    // Reporte 1 (lista de todas las ordenes)
    List<ObtenerOrdenDTO> obtenerOrdenes();

    // Reporte 2 (lista de repuestos disponibles)
    List<ObtenerRepuestoDTO> listaRepuestos();

    // Reporte 3 (lista de todos los servicios disponibles)
    List<ObtenerServicioDTO> listaServicios();

    // Reporte 4 (lista de mecanicos de la orden)
    List<ObtenerMecanicoOrdenDTO>  obtenerMecanicos(String id);

    // Reporte 5 (lista de ordenes del cliente)
    List<ObtenerOrdenDTO> listaOrdenesCliente(String id);

    // Reporte 6(Ingresos de las ordenes finalizadas
    Map<String, Double> obtenerIngresosPorOrdenFinalizada();

    // Reporte 7
    List<PromedioHorasDTO> obtenerPromedioHorasPorMecanico();

    // Reporte 8
    List<ObtenerOrdenDTO> listaOrdenesRepuesto(String idRepuesto);

    // Reporte 9: Facturas en rango de fechas con detalles de órdenes
    List<FacturaConOrdenDTO> listaFacturasConOrdenesEnRangoFechas(LocalDateTime fechaInicio, LocalDateTime fechaFin);

    // Reporte 10: Mecánicos con órdenes pendientes y repuestos asignados
    List<MecanicoPendienteDTO> listaMecanicosConOrdenesPendientesYRepuestos();

    DashBoardDTO obtenerDatos();
}
