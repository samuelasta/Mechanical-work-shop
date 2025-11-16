package co.uniquindio.edu.dto.mecanico;

import co.uniquindio.edu.model.enums.TipoEspecializacion;

import java.util.List;

public record ObtenerMecanicoDTO(String id,
                                 String nombre1,
                                 String nombre2,
                                 String apellido1,
                                 String apellido2,
                                 String email,
                                 int experiencia,
                                 int salario,
                                 List<TipoEspecializacion> especializacion) {
}
