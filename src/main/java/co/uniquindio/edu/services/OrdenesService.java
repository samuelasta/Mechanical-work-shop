package co.uniquindio.edu.services;

import co.uniquindio.edu.dto.orden.ActualizarOrdenDTO;
import co.uniquindio.edu.dto.orden.CrearOrdenDTO;
import co.uniquindio.edu.dto.orden.ObtenerOrdenDTO;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface OrdenesService {

    void crearOrden(CrearOrdenDTO crearOrdenDTO);
    void eliminarOrden(String idOrden);
    void actualizarOrden(String idOrden, ActualizarOrdenDTO actualizarOrdenDTO);
    ObtenerOrdenDTO obtenerOrden(String idOrden);
    List<ObtenerOrdenDTO> listaOrdenes();
}
