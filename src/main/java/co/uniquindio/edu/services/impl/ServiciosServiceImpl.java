package co.uniquindio.edu.services.impl;

import co.uniquindio.edu.dto.servicio.CrearServicioDTO;
import co.uniquindio.edu.dto.servicio.ObtenerServicioDTO;
import co.uniquindio.edu.repository.ServicioRepository;
import co.uniquindio.edu.services.ServiciosService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ServiciosServiceImpl implements ServiciosService {

    private final ServicioRepository servicioRepository;

    @Override
    public void crearServicio(CrearServicioDTO crearServicioDTO) {
        servicioRepository.crearServicio(crearServicioDTO);
    }

    @Override
    public void actualizarServicio(String id, CrearServicioDTO crearServicioDTO) {
        servicioRepository.actualizarServicio(id, crearServicioDTO);
    }

    @Override
    public List<ObtenerServicioDTO> listaServicios() {
        return servicioRepository.listaServicios();
    }

    @Override
    public ObtenerServicioDTO obtenerServicio(String id) {
        return servicioRepository.obtenerServicio(id);
    }

    @Override
    public void eliminarServicio(String id) {
        servicioRepository.eliminarServicio(id);
    }

    @Override
    public List<ObtenerServicioDTO> listaServiciosPorMecanico(String id) {
        return servicioRepository.listaServiciosPorMecanico(id);
    }

    @Override
    public List<ObtenerServicioDTO> listaServiciosPorVehiculo(String idCliente, String placaVehiculo) {
        return servicioRepository.listaServiciosPorVehiculo(idCliente, placaVehiculo);
    }
}
