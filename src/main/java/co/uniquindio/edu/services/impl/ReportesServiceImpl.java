package co.uniquindio.edu.services.impl;

import co.uniquindio.edu.dto.mecanico.ObtenerMecanicoDTO;
import co.uniquindio.edu.dto.mecanico.ObtenerMecanicoOrdenDTO;
import co.uniquindio.edu.dto.orden.ObtenerOrdenDTO;
import co.uniquindio.edu.services.OrdenesService;
import co.uniquindio.edu.services.ReportesService;
import co.uniquindio.edu.util.PdfGeneratorUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ReportesServiceImpl implements ReportesService {

    private final OrdenesService ordenesService;


    // REPORTE 1
    @Override
    public List<ObtenerOrdenDTO> obtenerOrdenes() {
        return ordenesService.listaOrdenes();
    }

    @Override
    public byte[] generarPDFOrdenes() {
        List<ObtenerOrdenDTO> lista = ordenesService.listaOrdenes();
        return PdfGeneratorUtil.generarPDFOrdenes(lista);
    }

    // REPORTE 2
    @Override
    public List<ObtenerMecanicoOrdenDTO> obtenerMecanicos(String id) {
        return ordenesService.obtenerMecanicosPorOrden(id);
    }

    @Override
    public byte[] generarPDFMecanicos(String id) {
        List<ObtenerMecanicoOrdenDTO> lista = ordenesService.obtenerMecanicosPorOrden(id);
        return PdfGeneratorUtil.generarPDFMecanicos(lista);
    }

}
