package co.uniquindio.edu.services.impl;

import co.uniquindio.edu.dto.vehiculo.CrearVehiculoDTO;
import co.uniquindio.edu.dto.vehiculo.ObtenerVehiculoDTO;
import co.uniquindio.edu.services.VehiculosService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VehiculosServiceImpl implements VehiculosService {

    @Override
    public void crearVehiculo(CrearVehiculoDTO crearVehiculoDTO) {

    }

    @Override
    public void actualizarVehiculo(String id, CrearVehiculoDTO crearVehiculoDTO) {

    }

    @Override
    public List<ObtenerVehiculoDTO> listaVehiculos() {
        return List.of();
    }

    @Override
    public ObtenerVehiculoDTO obtenerVehiculo(String id) {
        return null;
    }

    @Override
    public void eliminarVehiculo(String id) {

    }
}
