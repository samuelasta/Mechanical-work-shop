package co.uniquindio.edu.dto.orden;

import java.time.LocalDateTime;

public record CrearOrdenDTO(LocalDateTime fechaIngreso, // tal vez la ponga yo desde el back
                            String descripcion,
                            String placa,
                            String vehiculoId
                            // el estado se crea automatico (PENDIENTE)
                            ) {
}
