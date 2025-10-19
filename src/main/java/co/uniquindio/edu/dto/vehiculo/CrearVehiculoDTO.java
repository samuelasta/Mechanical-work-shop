package co.uniquindio.edu.dto.vehiculo;

import java.util.Date;

public record CrearVehiculoDTO(String placa,
                               Date anio,
                               String color,
                               String modelo,
                               String marca
                               ) {
}
