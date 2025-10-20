package co.uniquindio.edu.services.impl;

import co.uniquindio.edu.dto.repuesto.CrearRepuestoDTO;
import co.uniquindio.edu.dto.repuesto.ObtenerRepuestoDTO;
import co.uniquindio.edu.services.RepuestosService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RepuestosServiceImpl implements RepuestosService {

    @Override
    public void crearRepuesto(CrearRepuestoDTO crearRepuestoDTO) {

    }

    @Override
    public void actualizarRepuesto(String id, CrearRepuestoDTO crearRepuestoDTO) {

    }

    @Override
    public List<ObtenerRepuestoDTO> listaRepuestos() {
        return List.of();
    }

    @Override
    public ObtenerRepuestoDTO obtenerRepuesto(String id) {
        return null;
    }

    @Override
    public void eliminarRepuesto(String id) {

    }
}
