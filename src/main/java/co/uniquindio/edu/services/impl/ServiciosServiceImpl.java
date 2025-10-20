package co.uniquindio.edu.services.impl;

import co.uniquindio.edu.dto.servicio.CrearServicioDTO;
import co.uniquindio.edu.dto.servicio.ObtenerServicioDTO;
import co.uniquindio.edu.services.ServiciosService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServiciosServiceImpl implements ServiciosService {

    @Override
    public void crearServicio(CrearServicioDTO crearServicioDTO) {

    }

    @Override
    public void actualizarServicio(String id, CrearServicioDTO crearServicioDTO) {

    }

    @Override
    public List<ObtenerServicioDTO> listaServicios() {
        return List.of();
    }

    @Override
    public ObtenerServicioDTO obtenerServicio(String id) {
        return null;
    }

    @Override
    public void eliminarServicio(String id) {

    }
}
