package co.uniquindio.edu.services.impl;

import co.uniquindio.edu.dto.mecanico.ObtenerMecanicoDTO;
import co.uniquindio.edu.dto.mecanico.ObtenerMecanicoOrdenDTO;
import co.uniquindio.edu.dto.orden.ObtenerOrdenDTO;
import co.uniquindio.edu.dto.servicio.ObtenerServicioDTO;
import co.uniquindio.edu.services.OrdenesService;
import co.uniquindio.edu.services.ReportesService;
import co.uniquindio.edu.services.ServiciosService;
import co.uniquindio.edu.util.PdfGeneratorUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ReportesServiceImpl implements ReportesService {

    private final OrdenesService ordenesService;
    private final ServiciosService serviciosService;


    // REPORTE 1
    @Override
    public List<ObtenerOrdenDTO> obtenerOrdenes() {
        return ordenesService.listaOrdenes();
    }



    // REPORTE 2
    @Override
    public List<ObtenerMecanicoOrdenDTO> obtenerMecanicos(String id) {
        return ordenesService.obtenerMecanicosPorOrden(id);
    }

    // REPORTE 3
    @Override
    public List<ObtenerServicioDTO> listaServicios() {
        return serviciosService.listaServicios();
    }



}
