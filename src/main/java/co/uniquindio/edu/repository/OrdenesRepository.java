package co.uniquindio.edu.repository;

import co.uniquindio.edu.dto.CrearDiagnosticoDTO;
import co.uniquindio.edu.dto.mecanico.ObtenerMecanicoOrdenDTO;
import co.uniquindio.edu.dto.mecanico.RolDTO;
import co.uniquindio.edu.dto.orden.ActualizarOrdenDTO;
import co.uniquindio.edu.dto.orden.CrearOrdenDTO;
import co.uniquindio.edu.dto.orden.DetalleOrdenDTO;
import co.uniquindio.edu.dto.orden.ObtenerOrdenDTO;
import co.uniquindio.edu.dto.servicio.DetalleServicioMecanicoDTO;
import lombok.RequiredArgsConstructor;
import co.uniquindio.edu.exception.BadRequestException;
import co.uniquindio.edu.exception.ResourceNotFoundException;
import co.uniquindio.edu.model.enums.EstadoOrden;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import co.uniquindio.edu.dto.orden.ObtenerOrdenDTO;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Repository
public class OrdenesRepository {

    private final JdbcTemplate jdbcTemplate;


    @Transactional
    public void crearOrden(String idVehiculo, CrearOrdenDTO crearOrdenDTO) {


        try{
        String idOrden = java.util.UUID.randomUUID().toString();
        String sqlOrden = "INSERT INTO ORDEN (ID, FECHAINGRESO, ESTADO, DESCRIPCION, VEHICULO_ID) VALUES (?,?,?,?,?) ";
        jdbcTemplate.update(sqlOrden, 
            idOrden,
            Timestamp.valueOf(crearOrdenDTO.fechaIngreso()),
            "PENDIENTE",
            crearOrdenDTO.descripcion(),
            idVehiculo 
        );
        }catch(Exception e){

            throw new BadRequestException("no se pudo crear la orden");

        }

    }


    @Transactional
    public void eliminarOrden(String id) {

        String sqlVerificar = "SELECT COUNT(*) FROM ORDEN WHERE ID =? ";
        Integer count = jdbcTemplate.queryForObject(sqlVerificar, Integer.class, id);
    
        if(count == null || count == 0 ){
            throw new ResourceNotFoundException("no se encontró la orden");
        }

        jdbcTemplate.update("UPDATE ORDEN SET ESTADO=? WHERE ID =?", "INACTIVA", id);
    }


    @Transactional
    public void actualizarOrden(String id, ActualizarOrdenDTO actualizarOrdenDTO) {

        String sqlOrden = "UPDATE ORDEN SET FECHAINGRESO=?, FECHASALIDA=?, DESCRIPCION=? WHERE ID =?";
        jdbcTemplate.update(sqlOrden, 
                        Timestamp.valueOf(actualizarOrdenDTO.fechaIngreso()),
                        Timestamp.valueOf(actualizarOrdenDTO.fechaSalida()),
                        actualizarOrdenDTO.descripcion(), id);   
    }


    @Transactional(readOnly = true)
    public ObtenerOrdenDTO obtenerOrden(String idOrden) {

    String sql = """
        SELECT 
            o.ID,
            o.DESCRIPCION,
            o.FECHAINGRESO,
            o.FECHASALIDA,
            o.ESTADO,
            d.DIAGNOSTICOINICIAL,
            d.DIAGNOSTICOFINAL
        FROM ORDEN o
        JOIN DIAGNOSTICO d ON d.ORDEN_ID = o.ID
        WHERE o.ID = ?
    """;

    try {
        return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> new ObtenerOrdenDTO(
                rs.getString("ID"),
                rs.getString("DESCRIPCION"),
                rs.getTimestamp("FECHAINGRESO") != null 
                    ? rs.getTimestamp("FECHAINGRESO").toLocalDateTime() 
                    : null,
                rs.getTimestamp("FECHASALIDA") != null 
                    ? rs.getTimestamp("FECHASALIDA").toLocalDateTime() 
                    : null,
                EstadoOrden.valueOf(rs.getString("ESTADO")),
                rs.getString("DIAGNOSTICOINICIAL"),
                rs.getString("DIAGNOSTICOFINAL")
        ), idOrden);

    } catch (Exception e) {
        throw new ResourceNotFoundException("No se encontró la orden con ID: " + idOrden);
    }
}


    @Transactional(readOnly = true)
    public List<ObtenerOrdenDTO> obtenerTodasLasOrdenes() {

    String sql = """
        SELECT 
            o.ID,
            o.DESCRIPCION,
            o.FECHAINGRESO,
            o.FECHASALIDA,
            o.ESTADO,
            d.DIAGNOSTICOINICIAL,
            d.DIAGNOSTICOFINAL
        FROM ORDEN o
        JOIN DIAGNOSTICO d ON d.ORDEN_ID = o.ID
        ORDER BY o.FECHAINGRESO DESC
    """;

    try {
        return jdbcTemplate.query(sql, (rs, rowNum) -> new ObtenerOrdenDTO(
                rs.getString("ID"),
                rs.getString("DESCRIPCION"),
                rs.getTimestamp("FECHAINGRESO") != null
                    ? rs.getTimestamp("FECHAINGRESO").toLocalDateTime()
                    : null,
                rs.getTimestamp("FECHASALIDA") != null
                    ? rs.getTimestamp("FECHASALIDA").toLocalDateTime()
                    : null,
                EstadoOrden.valueOf(rs.getString("ESTADO")),
                rs.getString("DIAGNOSTICOINICIAL"),
                rs.getString("DIAGNOSTICOFINAL")
        ));
    } catch (DataAccessException e) {
        throw new BadRequestException("Error al obtener la lista de ordenes: " + e.getMessage());
    }
}



   @Transactional
    public void asignarMecanico(String idOrden, String idMecanico, RolDTO rolDTO) {
    try {
        String sql = """
            INSERT INTO MOS (ORDEN_ID, MECANICO_ID, ROL, FECHA_ASIGNACION)
            VALUES (?, ?, ?, ?)
        """;

        jdbcTemplate.update(sql,
                idOrden,
                idMecanico,
                rolDTO.rol().name(), // guarda el enum como texto (PINTOR, SUPERVISOR, etc.)
                Timestamp.valueOf(LocalDateTime.now())
        );

        System.out.println("Mecánico asignado correctamente a la orden " + idOrden);
    } catch (DataAccessException e) {
        throw new BadRequestException("Error al asignar el mecanico: " + e.getMessage());
    }
}


    @Transactional
    public void eliminarMecanico(String idOrden, String idMecanico) {
    try {
        String sql = "DELETE FROM MOS WHERE ORDEN_ID = ? AND MECANICO_ID = ?";
        int filas = jdbcTemplate.update(sql, idOrden, idMecanico);

        if (filas == 0) {
            throw new ResourceNotFoundException("No se encontró la relación entre la orden y el mecánico");
        }

        
    } catch (DataAccessException e) {
        throw new BadRequestException("Error al eliminar el mecqnico: " + e.getMessage());
    }
}



    public List<ObtenerMecanicoOrdenDTO> obtenerMecanicosPorOrden(String idOrden) {
        return List.of();
    }


    public void registrarDiagnosticos(String idOrden, CrearDiagnosticoDTO crearDiagnosticoDTO) {

    }


    public CrearDiagnosticoDTO obtenerDiagnostico(String idOrden) {
        return null;
    }

    // (agregar en la relación ternaria)

    public void registrarServicio(String idOrden, String idMecanico, String idServicio, DetalleServicioMecanicoDTO detalleServicioMecanicoDTO) {

    }

    // editar los datos de esa relación ternaria

    public void actualizarDetalleServicio(String idOrden, String idMecanico, String idServicio, DetalleServicioMecanicoDTO detalleServicioMecanicoDTO) {

    }

    // devueve la lista de todos los servicios(el mecanico que los hizo, rol, horas trabajadas y fechaAsignacion)

    public List<DetalleOrdenDTO> obtenerDetalleOrden(String idOrden) {
        return List.of();
    }


    public List<ObtenerOrdenDTO> listaOrdenesPorCliente(String idCliente) {
        return List.of();
    }
}
