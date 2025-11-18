package co.uniquindio.edu.controller;

import co.uniquindio.edu.dto.mecanico.ObtenerMecanicoDTO;
import co.uniquindio.edu.dto.mecanico.ObtenerMecanicoOrdenDTO;
import co.uniquindio.edu.dto.mecanico.PromedioHorasDTO;
import co.uniquindio.edu.dto.orden.ObtenerOrdenDTO;
import co.uniquindio.edu.dto.repuesto.ObtenerRepuestoDTO;
import co.uniquindio.edu.dto.response.ResponseDTO;
import co.uniquindio.edu.dto.servicio.ObtenerServicioDTO;
import co.uniquindio.edu.services.OrdenesService;
import co.uniquindio.edu.services.ReportesService;
import co.uniquindio.edu.util.PdfGeneratorUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/reportes")
@RequiredArgsConstructor
public class ReportesController {


    private final ReportesService reportesService;

    // REPORTE 1
    @GetMapping("/ordenes")
    public ResponseEntity<ResponseDTO<List<ObtenerOrdenDTO>>> obtenerOrdenes() {
        List<ObtenerOrdenDTO> lista = reportesService.obtenerOrdenes();
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO<>(false, lista));
    }

    @GetMapping("/ordenes/pdf")
    public ResponseEntity<byte[]> ReporteOrdenesPDF() {
        List<ObtenerOrdenDTO> lista = reportesService.obtenerOrdenes();
        byte[] pdf = PdfGeneratorUtil.generarPDFOrdenes(lista);

        return ResponseEntity.ok()
                // encabezado de info
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=reporte_listaOrdenes.pdf")
                // para visualizarlo desde aca
                .contentType(MediaType.APPLICATION_PDF)
                // el pdf du
                .body(pdf);
    }


    // REPORTE 2 (lista de todos los repuestos disponibles)
    @GetMapping("/repuestos")
    public ResponseEntity<ResponseDTO<List<ObtenerRepuestoDTO>>> listaRepuestos() {
        List<ObtenerRepuestoDTO> lista = reportesService.listaRepuestos();
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO<>(false, lista));
    }

    @GetMapping("/repuestos/pdf")
    public ResponseEntity<byte[]> ReporteRepuestosPDF() {
        List<ObtenerRepuestoDTO> lista = reportesService.listaRepuestos();
        byte[] pdf = PdfGeneratorUtil.generarPDFRepuestos(lista);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename= reporte_listaRepuestos.pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdf);
    }


    // REPORTE 3
    @GetMapping("/servicios")
    public ResponseEntity<ResponseDTO<List<ObtenerServicioDTO>>> listaServicios() {
        List<ObtenerServicioDTO> lista = reportesService.listaServicios();
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO<>(false, lista));
    }

    @GetMapping("/servicios/pdf")
    public ResponseEntity<byte[]> reporteListaServicios() {
        List<ObtenerServicioDTO> lista = reportesService.listaServicios();
        byte[] pdf = PdfGeneratorUtil.generarPDFListaServicios(lista);
        return ResponseEntity.status(HttpStatus.OK)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=reporte_listaServicios.pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdf);
    }

    // REPORTE 4
    @GetMapping("/mecanicos/{idOrden}/orden")
    public ResponseEntity<ResponseDTO<List<ObtenerMecanicoOrdenDTO>>> obtenerMecanicos(@PathVariable String idOrden) {

        List<ObtenerMecanicoOrdenDTO> lista = reportesService.obtenerMecanicos(idOrden);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO<>(false, lista));
    }

    @GetMapping("mecanicos/{idOrden}/ordenes/pdf")
    public ResponseEntity<byte[]> reporteMecanicosPDF(@PathVariable String idOrden) {
        List<ObtenerMecanicoOrdenDTO> lista = reportesService.obtenerMecanicos(idOrden);
        byte[] pdf = PdfGeneratorUtil.generarPDFMecanicos(lista);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename = reporte_mecanicosOrden.pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdf);

    }

    // REPORTE 5
    @GetMapping("/ordenes/{idCliente}/clientes")
    public ResponseEntity<ResponseDTO<List<ObtenerOrdenDTO>>> listaOrdenesCliente(@PathVariable String idCliente) {
        List<ObtenerOrdenDTO> lista = reportesService.listaOrdenesCliente(idCliente);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO<>(false, lista));
    }

    @GetMapping("/ordenes/{idCliente}/clientes/pdf")
    public ResponseEntity<byte[]> reporteOrdenesClientePDF(@PathVariable String idCliente) {
        List<ObtenerOrdenDTO> lista = reportesService.listaOrdenesCliente(idCliente);
        byte[] pdf = PdfGeneratorUtil.generarPDFListaOrdenesCliente(lista);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename= reporte_listaOrdenesCliente.pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdf);
    }

    // Reporte 6
    // Ingresos totales por órdenes finalizadas (con sumas)
    @GetMapping("/ingresos/pdf")
    public ResponseEntity<byte[]> reporteIngresosTotales() {
        Map<String, Double> ingresosPorOrden = reportesService.obtenerIngresosPorOrdenFinalizada();
        byte[] pdf = PdfGeneratorUtil.generarPDFIngresosTotales(ingresosPorOrden);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=reporte_ingresos.pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdf);
    }

    //Reporte 7, reporte promedio de horas
    @GetMapping("/promedio-horas/pdf")
    public ResponseEntity<byte[]> reportePromedioHorasPDF() {
        List<PromedioHorasDTO> listaPromedios = reportesService.obtenerPromedioHorasPorMecanico();
        byte[] pdf = PdfGeneratorUtil.generarPDFPromedioHoras(listaPromedios);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=reporte_promedio_horas.pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdf);
    }

    // Reporte 8, /api/reportes/ordenes-repuestos/{idRepuesto}/pdf
    @GetMapping("/ordenes-repuestos/{idRepuesto}/pdf")
    public ResponseEntity<byte[]> reporteRepuestosPDF(@PathVariable String idRepuesto) {
        List<ObtenerOrdenDTO> listaOrdenes = reportesService.listaOrdenesRepuesto(idRepuesto);
        byte[] pdf = PdfGeneratorUtil.generarPDFOrdenesRepuesto(listaOrdenes);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=reporte_ordenes_repuesto")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdf);
    }
}

