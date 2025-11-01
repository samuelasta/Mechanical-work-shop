package co.uniquindio.edu.services.impl;

import co.uniquindio.edu.dto.servicio.CrearServicioDTO;
import co.uniquindio.edu.dto.servicio.ObtenerServicioDTO;
import co.uniquindio.edu.services.ServiciosService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ServiciosServiceImpl implements ServiciosService {

    private final ServiciosService serviciosService;

    @Override
    public void crearServicio(CrearServicioDTO crearServicioDTO) {
        serviciosService.crearServicio(crearServicioDTO);
    }

    @Override
    public void actualizarServicio(String id, CrearServicioDTO crearServicioDTO) {
        serviciosService.actualizarServicio(id, crearServicioDTO);
    }

    @Override
    public List<ObtenerServicioDTO> listaServicios() {
        return serviciosService.listaServicios();
    }

    @Override
    public ObtenerServicioDTO obtenerServicio(String id) {
        return serviciosService.obtenerServicio(id);
    }

    @Override
    public void eliminarServicio(String id) {
        serviciosService.eliminarServicio(id);
    }

    @Override
    public List<ObtenerServicioDTO> listaServiciosPorMecanico(String id) {
        return serviciosService.listaServiciosPorMecanico(id);
    }

    @Override
    public List<ObtenerServicioDTO> listaServiciosPorVehiculo(String idCliente, String placaVehiculo) {
        return serviciosService.listaServiciosPorVehiculo(idCliente, placaVehiculo);
    }
}
