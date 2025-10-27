package co.uniquindio.edu.repository;

import co.uniquindio.edu.dto.factura.ObtenerFacturaDTO;
import co.uniquindio.edu.model.enums.EstadoFactura;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class FacturaRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    public ObtenerFacturaDTO obtenerFactura(String ordenId){
        String sql="SELECT ID, CONSECUTIVO, ESTADO, FECHAEMISION, IMPUESTOS, VALORTOTAL, ORDENID FROM FACTURA WHERE ORDENID=?";
        return jdbcTemplate.queryForObject(sql,
                (rs, rowNum) -> new ObtenerFacturaDTO(
                        rs.getString("ID"),
                        rs.getString("CONSECUTIVO"),
                        EstadoFactura.valueOf(rs.getString("ESTADO").toUpperCase()),
                        rs.getTimestamp("FECHAEMISION").toLocalDateTime(),
                        rs.getDouble("IMPUESTOS"),
                        rs.getDouble("VALORTOTAL"),
                        rs.getString("ORDENID")
                ) );
    }
    public List<ObtenerFacturaDTO> listaFacturasVehiculo(){
        //listar facturas
        String sql="SELECT id, CONSECUTIVO, ESTADO, FECHAEMISION, IMPUESTOS, VALORTOTAL, ORDENID FROM FACTURA";
        return jdbcTemplate.query(sql,
                (rs, rowNum) -> new ObtenerFacturaDTO(
                        rs.getString("ID"),
                        rs.getString("CONSECUTIVE"),
                        EstadoFactura.valueOf(rs.getString("ESTADO").toUpperCase()),
                        rs.getTimestamp("FECHAEMISION").toLocalDateTime(),
                        rs.getDouble("IMPUESTOS"),
                        rs.getDouble("VALORTOTAL"),
                        rs.getString("ORDENID")
        ));
    }
    //listar por placa nose porque no encuentro en donde lo tiene como atributo
}
