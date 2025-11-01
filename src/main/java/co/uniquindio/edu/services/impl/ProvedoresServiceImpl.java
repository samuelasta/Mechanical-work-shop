package co.uniquindio.edu.services.impl;

import co.uniquindio.edu.dto.proveedor.CrearProveedorDTO;
import co.uniquindio.edu.dto.proveedor.ObtenerProveedorDTO;
import co.uniquindio.edu.repository.ProveedorRepository;
import co.uniquindio.edu.services.ProvedoresService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ProvedoresServiceImpl implements ProvedoresService {

    private final ProveedorRepository proveedorRepository;

    @Override
    public void crearProvedor(CrearProveedorDTO crearProveedorDTO) {
        proveedorRepository.crearProvedor(crearProveedorDTO);
    }

    @Override
    public void actualizarProveedor(String id, CrearProveedorDTO crearProveedorDTO) {
        proveedorRepository.actualizarProveedor(id, crearProveedorDTO);
    }

    @Override
    public List<ObtenerProveedorDTO> listaProvedores() {
        return proveedorRepository.listaProvedores();
    }

    @Override
    public ObtenerProveedorDTO obtenerProveedor(String id) {
        return proveedorRepository.obtenerProveedor(id);
    }

    @Override
    public void eliminarProveedor(String id) {
        proveedorRepository.eliminarProveedor(id);
    }
}
