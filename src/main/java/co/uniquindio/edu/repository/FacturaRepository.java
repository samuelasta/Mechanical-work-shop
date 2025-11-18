package co.uniquindio.edu.repository;

import co.uniquindio.edu.dto.factura.ObtenerFacturaDTO;
import co.uniquindio.edu.dto.factura.FacturaConOrdenDTO;
import co.uniquindio.edu.exception.ResourceNotFoundException;
import co.uniquindio.edu.model.enums.EstadoFactura;
import co.uniquindio.edu.model.enums.EstadoOrden;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Repository
public class FacturaRepository {

    private final JdbcTemplate jdbcTemplate;

    public ObtenerFacturaDTO obtenerFactura(String ordenId) {
        String sql = "SELECT ID, CONSECUTIVO, ESTADO, FECHAEMISION, IMPUESTOS, VALORTOTAL, ORDEN_ID FROM FACTURA WHERE ORDEN_ID=?";
        return jdbcTemplate.queryForObject(sql,
                (rs, rowNum) -> new ObtenerFacturaDTO(
                        rs.getString("ID"),
                        rs.getString("CONSECUTIVO"),
                        rs.getString("ESTADO"),
                        rs.getTimestamp("FECHAEMISION").toLocalDateTime(),
                        rs.getDouble("IMPUESTOS"),
                        rs.getDouble("VALORTOTAL"),
                        rs.getString("ORDEN_ID")
                ), ordenId);
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
                        rs.getString("ESTADO"),
                        rs.getTimestamp("FECHAEMISION").toLocalDateTime(),
                        rs.getDouble("IMPUESTOS"),
                        rs.getDouble("VALORTOTAL"),
                        rs.getString("ORDEN_ID")
                ),
                placa
        );
    }

    public List<ObtenerFacturaDTO>  listaFacturas() {
        String sql = """
                SELECT ID,
                CONSECUTIVO,
                ESTADO,
                FECHAEMISION,
                VALORTOTAL,
                IMPUESTOS,
                ORDEN_ID
                        FROM FACTURA 
                            """;

        return jdbcTemplate.query(sql,
                (rs, rowNum) -> new ObtenerFacturaDTO(
                        rs.getString("ID"),
                        rs.getString("CONSECUTIVO"),
                        rs.getString("ESTADO"),
                        rs.getTimestamp("FECHAEMISION").toLocalDateTime(),
                        rs.getDouble("IMPUESTOS"),
                        rs.getDouble("VALORTOTAL"),
                        rs.getString("ORDEN_ID")

                ));
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
                            JOIN VEHICULO v ON v.ID = o.VEHICULO_ID
                            JOIN CLIENTES c ON v.CLIENTES_ID = c.ID
                            WHERE c.ID = ?
                            AND o.ESTADO = 'FINALIZADA'
                            """;

        return jdbcTemplate.query(sql,
                (rs, rowNum) -> new ObtenerFacturaDTO(
                        rs.getString("ID"),
                        rs.getString("CONSECUTIVO"),
                        rs.getString("ESTADO"),
                        rs.getTimestamp("FECHAEMISION").toLocalDateTime(),
                        rs.getDouble("IMPUESTOS"),
                        rs.getDouble("VALORTOTAL"),
                        rs.getString("ORDEN_ID")
                        
                ), id
        );
    }

    public void generarFactura(String idOrden) {
        String sql= """
                SELECT
                    NVL(rep.valorRepuestos, 0) AS valorRepuestos,
                    NVL(serv.valorServicios, 0) AS valorServicios
                FROM ORDEN o
                LEFT JOIN (
                    SELECT\s
                        dtl.ORDEN_ID,
                        SUM(r.COSTOUNITARIO * dtl.CANTIDAD) AS valorRepuestos
                    FROM DTL_SER_REP dtl
                    INNER JOIN REPUESTO r ON r.ID = dtl.REPUESTO_ID
                    GROUP BY dtl.ORDEN_ID
                ) rep ON o.ID = rep.ORDEN_ID
                LEFT JOIN (
                    SELECT\s
                        mos.ORDEN_ID,
                        SUM(s.COSTOUNITARIO) AS valorServicios
                    FROM MOS mos
                    INNER JOIN SERVICIO s ON s.ID = mos.SERVICIO_ID
                    GROUP BY mos.ORDEN_ID
                ) serv ON o.ID = serv.ORDEN_ID
                WHERE o.ID = ?
         """;

        Map<String, Object> resultado = jdbcTemplate.queryForMap(sql, idOrden);

        double valorRepuestos = ((Number) resultado.get("valorRepuestos")).doubleValue();
        double valorServicios = ((Number) resultado.get("valorServicios")).doubleValue();
        double impuestos = (valorRepuestos + valorServicios) * 0.19;
        double totalFactura = valorRepuestos + valorServicios + impuestos;

        // Obtener el nuevo consecutivo
        String sqlConsecutivo = "SELECT COALESCE(MAX(CONSECUTIVO), 0) FROM FACTURA";
        Integer ultimoConsecutivo = jdbcTemplate.queryForObject(sqlConsecutivo, Integer.class);
        int nuevoConsecutivo = ultimoConsecutivo + 1;

        // Insertar la factura
        String idFactura = java.util.UUID.randomUUID().toString();
        String insertFactura = """
        INSERT INTO FACTURA (ID, CONSECUTIVO, ESTADO, FECHAEMISION, VALORTOTAL, IMPUESTOS, ORDEN_ID) 
        VALUES (?, ?, ?, ?, ?, ?, ?)
    """;
        jdbcTemplate.update(insertFactura, idFactura, nuevoConsecutivo+"" , "PENDIENTE", LocalDateTime.now(), totalFactura, impuestos, idOrden);

        System.out.println("Factura generada con total: " + totalFactura);
    }

    public void eliminarFactura(String idFactura, String idOrden){

        try {
            String sql = """
                DELETE FROM FACTURA WHERE ID = ? AND ORDEN_ID = ?
        """;
            jdbcTemplate.update(sql, idFactura, idOrden);

            String sqlEstadoOrden = """
                UPDATE ORDEN SET ESTADO = ? 
                WHERE ID = ?
            """;
            jdbcTemplate.update(sqlEstadoOrden, "PENDIENTE", idOrden);

        }catch (DataAccessException e){
            throw new ResourceNotFoundException("Factura no encontrada o no se puedo eliminar por x motivo");
        }
    }
    public List<FacturaConOrdenDTO> listaFacturasConOrdenesEnRangoFechas(LocalDateTime fechaInicio, LocalDateTime fechaFin) {
        String sql = """
        SELECT f.ID AS idFactura,
               f.CONSECUTIVO,
               f.ESTADO AS estadoFactura,
               f.FECHAEMISION,
               f.IMPUESTOS,
               f.VALORTOTAL,
               o.ID AS idOrden,
               o.DESCRIPCION AS descripcionOrden,
               o.FECHAINGRESO,
               o.FECHASALIDA,
               o.ESTADO AS estadoOrden,
               d.DIAGNOSTICOINICIAL,
               d.DIAGNOSTICOFINAL,
               v.PLACA AS placaVehiculo
        FROM FACTURA f
        JOIN ORDEN o ON f.ORDEN_ID = o.ID
        LEFT JOIN DIAGNOSTICO d ON d.ORDEN_ID = o.ID
        JOIN VEHICULO v ON v.ID = o.VEHICULO_ID
        WHERE f.FECHAEMISION BETWEEN ? AND ?
        ORDER BY f.FECHAEMISION DESC
    """;

        return jdbcTemplate.query(sql,
                (rs, rowNum) -> new FacturaConOrdenDTO(
                        rs.getString("idFactura"),
                        rs.getString("CONSECUTIVO"),
                        rs.getString("estadoFactura"),
                        rs.getTimestamp("FECHAEMISION").toLocalDateTime(),
                        rs.getDouble("IMPUESTOS"),
                        rs.getDouble("VALORTOTAL"),
                        rs.getString("idOrden"),
                        rs.getString("descripcionOrden"),
                        rs.getTimestamp("FECHAINGRESO").toLocalDateTime(),
                        rs.getTimestamp("FECHASALIDA") != null ? rs.getTimestamp("FECHASALIDA").toLocalDateTime() : null,
                        EstadoOrden.valueOf(rs.getString("estadoOrden")),
                        rs.getString("DIAGNOSTICOINICIAL"),
                        rs.getString("DIAGNOSTICOFINAL"),
                        rs.getString("placaVehiculo")
                ),
                fechaInicio, fechaFin
        );
    }
}
