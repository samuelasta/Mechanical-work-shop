package co.uniquindio.edu.dto.vehiculo;

import java.util.Date;

public record ObtenerVehiculoDTO(String id,
                                 String placa,
                                 int anio,
                                 String color,
                                 String modelo,
                                 String marca,
                                 String idPropietario
) {
}
