package co.uniquindio.edu.controller;

import co.uniquindio.edu.dto.mecanico.ObtenerMecanicoDTO;
import co.uniquindio.edu.dto.mecanico.ObtenerMecanicoOrdenDTO;
import co.uniquindio.edu.dto.orden.ObtenerOrdenDTO;
import co.uniquindio.edu.dto.response.ResponseDTO;
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

@RestController
@RequestMapping("/api/reportes")
@RequiredArgsConstructor
public class ReportesController {

    private final OrdenesService ordenesService;
    private final ReportesService reportesService;

    @GetMapping("/ordenes")
    public ResponseEntity<ResponseDTO<List<ObtenerOrdenDTO>>> obtenerOrdenes() {
        List<ObtenerOrdenDTO> lista = reportesService.obtenerOrdenes();
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO<>(false, lista));
    }

    @GetMapping("/ordenes/pdf")
    public ResponseEntity<byte[]> generarReporteOrdenesPDF() {
        List<ObtenerOrdenDTO> lista = reportesService.obtenerOrdenes();
        byte[] pdf = PdfGeneratorUtil.generarPDFOrdenes(lista);

        return ResponseEntity.ok()
                // encabezado de info
                .header(HttpHeaders.CONTENT_DISPOSITION,"attachment; filename=reporte_listaOrdenes.pdf")
                // para visualizarlo desde aca
                .contentType(MediaType.APPLICATION_PDF)
                // el pdf du
                .body( pdf );
    }

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
                .body( pdf );

    }
}
