package co.uniquindio.edu.services;

import co.uniquindio.edu.dto.proveedor.CrearProveedorDTO;
import co.uniquindio.edu.dto.proveedor.ObtenerProveedorDTO;

import java.util.List;

public interface ProvedoresService {

    void crearProvedor(CrearProveedorDTO crearProveedorDTO);
    void actualizarProveedor(String id, CrearProveedorDTO crearProveedorDTO);
    List<ObtenerProveedorDTO> listaProvedores();
    ObtenerProveedorDTO obtenerProveedor(String id);
}
