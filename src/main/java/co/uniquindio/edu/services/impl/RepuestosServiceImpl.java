package co.uniquindio.edu.services.impl;

import co.uniquindio.edu.dto.repuesto.CrearRepuestoDTO;
import co.uniquindio.edu.dto.repuesto.ObtenerRepuestoDTO;
import co.uniquindio.edu.services.RepuestosService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class RepuestosServiceImpl implements RepuestosService {

    private final RepuestosService repuestosService;

    @Override
    public void crearRepuesto(CrearRepuestoDTO crearRepuestoDTO) {
        repuestosService.crearRepuesto(crearRepuestoDTO);
    }

    @Override
    public void actualizarRepuesto(String id, CrearRepuestoDTO crearRepuestoDTO) {
        repuestosService.actualizarRepuesto(id, crearRepuestoDTO);
    }

    @Override
    public List<ObtenerRepuestoDTO> listaRepuestos() {
        return repuestosService.listaRepuestos();
    }

    @Override
    public ObtenerRepuestoDTO obtenerRepuesto(String id) {
        return repuestosService.obtenerRepuesto(id);
    }

    @Override
    public void eliminarRepuesto(String id) {
        repuestosService.eliminarRepuesto(id);
    }
}
