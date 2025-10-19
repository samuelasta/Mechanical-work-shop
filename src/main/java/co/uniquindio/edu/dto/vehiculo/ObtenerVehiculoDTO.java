package co.uniquindio.edu.dto.vehiculo;

import java.util.Date;

public record ObtenerVehiculoDTO(String id,
                                 String placa,
                                 Date anio,
                                 String color,
                                 String modelo,
                                 String marca
) {
}
