package co.uniquindio.edu.dto.mecanico;

public record CrearMecanicoDTO(String nombre1,
                               String nombre2,
                               String apellido1,
                               String apellido2,
                               String email,
                               int experiencia,
                               String especializacion
                               // estado se crea automatico (activo, inactivo)
) {
    // misma para editar/actualizar mecanico y obtener info del mecanico, y una lista de mecanicos de este tipo
    //no
}
