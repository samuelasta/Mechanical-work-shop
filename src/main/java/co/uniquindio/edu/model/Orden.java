package co.uniquindio.edu.model;

import co.uniquindio.edu.model.enums.EstadoOrden;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Orden {

    private String id;
    private LocalDateTime fechaIngreso;
    private LocalDateTime fechaSalida;
    private EstadoOrden EstadoOrden;
    private Diagnostico diagnostico;

}
