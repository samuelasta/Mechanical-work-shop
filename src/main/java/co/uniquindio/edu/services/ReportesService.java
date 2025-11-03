package co.uniquindio.edu.services;

import co.uniquindio.edu.dto.mecanico.ObtenerMecanicoDTO;
import co.uniquindio.edu.dto.mecanico.ObtenerMecanicoOrdenDTO;
import co.uniquindio.edu.dto.orden.ObtenerOrdenDTO;
import co.uniquindio.edu.dto.servicio.ObtenerServicioDTO;

import java.util.List;

public interface ReportesService {

    // Reporte 1 (lista de todas las ordenes)
    List<ObtenerOrdenDTO> obtenerOrdenes();


    // Reporte 2 (lista de mecanicos de la orden)
    List<ObtenerMecanicoOrdenDTO>  obtenerMecanicos(String id);


    // Reporte 3 (lista de todos los servicios disponibles)
    List<ObtenerServicioDTO> listaServicios();


}
