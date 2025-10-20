package co.uniquindio.edu.services;

import co.uniquindio.edu.dto.servicio.CrearServicioDTO;
import co.uniquindio.edu.dto.servicio.ObtenerServicioDTO;

import java.util.List;

public interface ServiciosService {

    void crearServicio(CrearServicioDTO crearServicioDTO);
    void actualizarServicio(String id, CrearServicioDTO crearServicioDTO);
    List<ObtenerServicioDTO> listaServicios();
    ObtenerServicioDTO obtenerServicio(String id);
    void eliminarServicio(String id);
}
