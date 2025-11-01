package co.uniquindio.edu.services.impl;

import co.uniquindio.edu.dto.vehiculo.CrearVehiculoDTO;
import co.uniquindio.edu.dto.vehiculo.ObtenerVehiculoDTO;
import co.uniquindio.edu.repository.VehiculoRepository;
import co.uniquindio.edu.services.VehiculosService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class VehiculosServiceImpl implements VehiculosService {

    private final VehiculoRepository vehiculoRepository;

    @Override
    public void crearVehiculo(String idCliente, CrearVehiculoDTO crearVehiculoDTO) {
        vehiculoRepository.crearVehiculo(idCliente, crearVehiculoDTO);
    }

    @Override
    public void actualizarVehiculo(String id, String idCliente, CrearVehiculoDTO crearVehiculoDTO) {
        vehiculoRepository.actualizarVehiculo(id, idCliente, crearVehiculoDTO);
    }

    @Override
    public List<ObtenerVehiculoDTO> listaVehiculos() {
        return vehiculoRepository.listaVehiculos();
    }

    @Override
    public ObtenerVehiculoDTO obtenerVehiculo(String id) {
        return vehiculoRepository.obtenerVehiculo(id);
    }

    @Override
    public void eliminarVehiculo(String id) {
        vehiculoRepository.eliminarVehiculo(id);
    }
}
