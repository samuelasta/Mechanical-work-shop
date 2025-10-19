package co.uniquindio.edu.dto.proveedor;

import co.uniquindio.edu.dto.cliente.CrearTelefonoDTO;

import java.util.List;

public record ObtenerProveedorDTO(String id,
                                  String nombre,
                                  String email,
                                  List<CrearTelefonoDTO> telefonos) {
}
