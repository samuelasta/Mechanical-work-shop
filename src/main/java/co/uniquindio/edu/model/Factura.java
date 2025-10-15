package co.uniquindio.edu.model;

import co.uniquindio.edu.model.enums.EstadoFactura;

import java.time.LocalDateTime;

public class Factura {

    private String id;
    private int consecutivo;
    private EstadoFactura EstadoFactura;
    private LocalDateTime fechaEmision;
    private double valorTotal;
    private double impuestos;
}
