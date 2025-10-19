package co.uniquindio.edu.dto.proveedor;

import co.uniquindio.edu.dto.cliente.CrearTelefonoDTO;

import java.util.List;

public record CrearProveedorDTO(String nombre,
                                String email,
                                List<CrearTelefonoDTO> telefonos

                                ) {
    //misma para editar/actualizar datos
}
