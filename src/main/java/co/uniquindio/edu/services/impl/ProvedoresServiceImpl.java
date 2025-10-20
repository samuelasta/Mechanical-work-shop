package co.uniquindio.edu.services.impl;

import co.uniquindio.edu.dto.proveedor.CrearProveedorDTO;
import co.uniquindio.edu.dto.proveedor.ObtenerProveedorDTO;
import co.uniquindio.edu.services.ProvedoresService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProvedoresServiceImpl implements ProvedoresService {

    @Override
    public void crearProvedor(CrearProveedorDTO crearProveedorDTO) {

    }

    @Override
    public void actualizarProveedor(String id, CrearProveedorDTO crearProveedorDTO) {

    }

    @Override
    public List<ObtenerProveedorDTO> listaProvedores() {
        return List.of();
    }

    @Override
    public ObtenerProveedorDTO obtenerProveedor(String id) {
        return null;
    }

    @Override
    public void eliminarProveedor(String id) {

    }
}
