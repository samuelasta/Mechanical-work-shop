package co.uniquindio.edu.services;

import co.uniquindio.edu.dto.repuesto.CrearRepuestoDTO;
import co.uniquindio.edu.dto.repuesto.ObtenerRepuestoDTO;

import java.util.List;

public interface RepuestosService {

    void crearRepuesto(CrearRepuestoDTO crearRepuestoDTO, String idProveedor);
    void actualizarRepuesto(String id, CrearRepuestoDTO crearRepuestoDTO, String idProveedor);
    List<ObtenerRepuestoDTO> listaRepuestos();
    ObtenerRepuestoDTO obtenerRepuesto(String id);
    void eliminarRepuesto(String id);
}
