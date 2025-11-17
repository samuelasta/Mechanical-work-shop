package co.uniquindio.edu.services.impl;

import co.uniquindio.edu.dto.repuesto.CrearRepuestoDTO;
import co.uniquindio.edu.dto.repuesto.ObtenerRepuestoDTO;
import co.uniquindio.edu.repository.RepuestosRepository;
import co.uniquindio.edu.services.RepuestosService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class RepuestosServiceImpl implements RepuestosService {

    private final RepuestosRepository repuestosRepository;

    @Override
    public void crearRepuesto(CrearRepuestoDTO crearRepuestoDTO, String idProveedor) {
        repuestosRepository.crearRepuesto(crearRepuestoDTO, idProveedor);
    }

    @Override
    public void actualizarRepuesto(String id, CrearRepuestoDTO crearRepuestoDTO, String idProveedor) {
        repuestosRepository.actualizarRepuesto(id, crearRepuestoDTO, idProveedor);
    }

    @Override
    public List<ObtenerRepuestoDTO> listaRepuestos() {
        return repuestosRepository.listaRepuestos();
    }

    @Override
    public ObtenerRepuestoDTO obtenerRepuesto(String id) {
        return repuestosRepository.obtenerRepuesto(id);
    }

    @Override
    public void eliminarRepuesto(String id) {
        repuestosRepository.eliminarRepuesto(id);
    }
}
