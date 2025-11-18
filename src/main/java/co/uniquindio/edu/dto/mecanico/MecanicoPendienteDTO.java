package co.uniquindio.edu.dto.mecanico;

import java.util.List;

public record MecanicoPendienteDTO(
        String idMecanico,
        String nombre1,
        String nombre2,
        String apellido1,
        String apellido2,
        String email,
        int experiencia,
        List<String> ordenesPendientes, // IDs de órdenes pendientes
        List<String> repuestosAsignados // Nombres de repuestos asignados
) {
}