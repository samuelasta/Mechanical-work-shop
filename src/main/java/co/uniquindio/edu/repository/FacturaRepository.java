package co.uniquindio.edu.repository;

import co.uniquindio.edu.dto.factura.ObtenerFacturaDTO;
import co.uniquindio.edu.model.enums.EstadoFactura;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Repository
public class FacturaRepository {

    private final JdbcTemplate jdbcTemplate;

    public ObtenerFacturaDTO obtenerFactura(String ordenId) {
        String sql = "SELECT ID, CONSECUTIVO, ESTADO, FECHAEMISION, IMPUESTOS, VALORTOTAL, ORDENID FROM FACTURA WHERE ORDEN_ID=?";
        return jdbcTemplate.queryForObject(sql,
                (rs, rowNum) -> new ObtenerFacturaDTO(
                        rs.getString("ID"),
                        rs.getString("CONSECUTIVO"),
                        EstadoFactura.valueOf(rs.getString("ESTADO").toUpperCase()),
                        rs.getTimestamp("FECHAEMISION").toLocalDateTime(),
                        rs.getDouble("IMPUESTOS"),
                        rs.getDouble("VALORTOTAL"),
                        rs.getString("ORDENID")
                ));
    }

    public List<ObtenerFacturaDTO> listaFacturasVehiculo(String placa) {

        String sql = """
        SELECT f.ID,
               f.CONSECUTIVO,
               f.ESTADO,
               f.FECHAEMISION,
               f.IMPUESTOS,
               f.VALORTOTAL,
               f.ORDEN_ID
        FROM FACTURA f
        JOIN ORDEN o ON o.ID = f.ORDEN_ID
        JOIN VEHICULO v ON v.ID = o.VEHICULO_ID
        WHERE v.PLACA = ?
          AND o.ESTADO = 'FINALIZADA'
    """;

        return jdbcTemplate.query(
                sql,
                (rs, rowNum) -> new ObtenerFacturaDTO(
                        rs.getString("ID"),
                        rs.getString("CONSECUTIVO"),
                        EstadoFactura.valueOf(rs.getString("ESTADO").toUpperCase()),
                        rs.getTimestamp("FECHAEMISION").toLocalDateTime(),
                        rs.getDouble("IMPUESTOS"),
                        rs.getDouble("VALORTOTAL"),
                        rs.getString("ORDEN_ID")
                ),
                placa
        );
    }

    public List<ObtenerFacturaDTO> listaFacturasCliente(String id){
        String sql = """
                SELECT f.ID,
                f.CONSECUTIVO,
                f.ESTADO,
                f.FECHAEMISION,
                f.VALORTOTAL,
                f.IMPUESTOS,
                f.ORDEN_ID
                        FROM FACTURA f 
                            JOIN ORDEN o ON o.ID = f.ORDEN_ID
                            JOIN CLIENTES c ON c.ID = o.CLIENTES_ID
                            WHERE c.ID = ?
                            AND o.ESTADO = 'FINALIZADA'
                            """;

        return jdbcTemplate.query(sql,
                (rs, rowNum) -> new ObtenerFacturaDTO(
                        rs.getString("ID"),
                        rs.getString("CONSECUTIVO"),
                        EstadoFactura.valueOf(rs.getString("ESTADO").toUpperCase()),
                        rs.getTimestamp("FECHAEMISION").toLocalDateTime(),
                        rs.getDouble("IMPUESTOS"),
                        rs.getDouble("VALORTOTAL"),
                        rs.getString("ORDEN_ID")
                        
                ), id
        );
    }


}
