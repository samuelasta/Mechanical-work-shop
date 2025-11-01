package co.uniquindio.edu.services;

import co.uniquindio.edu.dto.repuesto.CrearRepuestoDTO;
import co.uniquindio.edu.dto.repuesto.ObtenerRepuestoDTO;
import co.uniquindio.edu.dto.vehiculo.CrearVehiculoDTO;
import co.uniquindio.edu.dto.vehiculo.ObtenerVehiculoDTO;

import java.util.List;

public interface VehiculosService {

    void crearVehiculo(String idCliente, CrearVehiculoDTO crearVehiculoDTO);
    void actualizarVehiculo(String id, String idCliente, CrearVehiculoDTO crearVehiculoDTO);
    List<ObtenerVehiculoDTO> listaVehiculos();
    ObtenerVehiculoDTO obtenerVehiculo(String id);
    void eliminarVehiculo(String id);
}
