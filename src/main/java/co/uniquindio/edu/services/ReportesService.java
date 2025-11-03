package co.uniquindio.edu.services;

import co.uniquindio.edu.dto.mecanico.ObtenerMecanicoDTO;
import co.uniquindio.edu.dto.mecanico.ObtenerMecanicoOrdenDTO;
import co.uniquindio.edu.dto.orden.ObtenerOrdenDTO;

import java.util.List;

public interface ReportesService {

    // Reporte 1 (lista de todas las ordenes)
    List<ObtenerOrdenDTO> obtenerOrdenes();
    byte[] generarPDFOrdenes();

    // Reporte 2 (lista de mecanicos de la orden)
    List<ObtenerMecanicoOrdenDTO>  obtenerMecanicos(String id);
    byte[] generarPDFMecanicos(String id);
}
