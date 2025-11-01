package co.uniquindio.edu.services.impl;

import co.uniquindio.edu.dto.mecanico.CrearMecanicoDTO;
import co.uniquindio.edu.dto.mecanico.EstadisticasMecanicoDTO;
import co.uniquindio.edu.dto.mecanico.ObtenerMecanicoDTO;
import co.uniquindio.edu.services.MecanicosService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MecanicoServiceImpl implements MecanicosService {

    @Override
    public void crearMecanico(CrearMecanicoDTO crearMecanicoDTO) {

    }

    @Override
    public void actualizarMecanico(CrearMecanicoDTO crearMecanicoDTO) {

    }

    @Override
    public List<ObtenerMecanicoDTO> listaMecanicos() {
        return List.of();
    }

    @Override
    public ObtenerMecanicoDTO obtenerMecanico(String id) {
        return null;
    }

    @Override
    public void eliminarMecanico(String id) {

    }

    @Override
    public EstadisticasMecanicoDTO obtenerEstadisticas(String id) {
        return null;
    }
}
