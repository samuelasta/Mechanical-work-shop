package co.uniquindio.edu.dto.repuesto;

public record ObtenerRepuestoDTO(String id,
                                 String nombre,
                                 double costoUnitario,
                                 int stock,
                                 String idProveedor) {
}
