package co.uniquindio.edu.services.impl;

import co.uniquindio.edu.dto.orden.ActualizarOrdenDTO;
import co.uniquindio.edu.dto.orden.CrearOrdenDTO;
import co.uniquindio.edu.dto.orden.ObtenerOrdenDTO;
import co.uniquindio.edu.services.OrdenesService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrdenesServiceImpl implements OrdenesService {

    @Override
    public void crearOrden(CrearOrdenDTO crearOrdenDTO) {

    }

    @Override
    public void eliminarOrden(String id) {

    }

    @Override
    public void actualizarOrden(String id, ActualizarOrdenDTO actualizarOrdenDTO) {

    }

    @Override
    public ObtenerOrdenDTO obtenerOrden(String idOrden) {
        return null;
    }

    @Override
    public List<ObtenerOrdenDTO> listaOrdenes() {
        return List.of();
    }
}
