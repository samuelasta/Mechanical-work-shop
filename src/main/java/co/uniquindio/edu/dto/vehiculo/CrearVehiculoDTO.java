package co.uniquindio.edu.dto.vehiculo;

import java.util.Date;

public record CrearVehiculoDTO(String placa,
                               int anio,
                               String color,
                               String modelo,
                               String marca
                               ) {
}
