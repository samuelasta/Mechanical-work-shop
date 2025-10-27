package co.uniquindio.edu.dto.cliente;

import java.time.LocalDateTime;
import java.util.List;

public record ObtenerClienteDTO(String id,
                                String nombre1,
                                String nombre2,
                                String apellido1,
                                String apellido2,
                                String email,
                                List<CrearTelefonoDTO> telefonos,
                                String direccion,
                                String barrio,
                                String ciudad,
                                String departamento) {
}
