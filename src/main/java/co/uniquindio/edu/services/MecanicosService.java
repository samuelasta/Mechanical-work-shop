package co.uniquindio.edu.services;

import co.uniquindio.edu.dto.mecanico.CrearMecanicoDTO;
import co.uniquindio.edu.dto.mecanico.ObtenerMecanicoDTO;

import java.util.List;

public interface MecanicosService {

    void crearMecanico(CrearMecanicoDTO crearMecanicoDTO);
    void actualizarMecanico(CrearMecanicoDTO crearMecanicoDTO);
    List<ObtenerMecanicoDTO> listaMecanicos();
    ObtenerMecanicoDTO obtenerMecanico(String id);
    void eliminarMecanico(String id);
}
