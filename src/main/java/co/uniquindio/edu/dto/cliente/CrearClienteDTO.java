package co.uniquindio.edu.dto.cliente;

import java.util.List;

//tambien será el mismo para ActualizarCliente
public record CrearClienteDTO( String nombre1,
                              String nombre2,
                              String apellido1,
                              String apellido2,
                              String email,
                              List<CrearTelefonoDTO> telefonos,
                              String direccion,
                              String barrio,
                              String ciudad,
                              String departamento

                              ) {
}
