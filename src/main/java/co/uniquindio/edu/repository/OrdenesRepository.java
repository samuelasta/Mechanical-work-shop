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
import co.uniquindio.edu.model.enums.Rol;

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
import java.util.UUID;

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

        if(actualizarOrdenDTO.fechaSalida() != null){
            String sqlOrden = "UPDATE ORDEN SET FECHAINGRESO=?, FECHASALIDA=?, DESCRIPCION=?, VEHICULO_ID=? WHERE ID =?";
            jdbcTemplate.update(sqlOrden,
                        Timestamp.valueOf(actualizarOrdenDTO.fechaIngreso()),
                        Timestamp.valueOf(actualizarOrdenDTO.fechaSalida()),
                        actualizarOrdenDTO.descripcion(), actualizarOrdenDTO.idVehiculo(), id);
        }
        else{
            String sqlOrden = "UPDATE ORDEN SET FECHAINGRESO=?, FECHASALIDA=?, DESCRIPCION=?, VEHICULO_ID=? WHERE ID =?";
            jdbcTemplate.update(sqlOrden,
                    Timestamp.valueOf(actualizarOrdenDTO.fechaIngreso()),
                    null,
                    actualizarOrdenDTO.descripcion(), actualizarOrdenDTO.idVehiculo(), id);
        }
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
            d.DIAGNOSTICOFINAL,
            v.PLACA
        FROM ORDEN o
        LEFT JOIN DIAGNOSTICO d ON d.ORDEN_ID = o.ID
        JOIN VEHICULO v ON v.ID = o.VEHICULO_ID
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
                rs.getString("DIAGNOSTICOFINAL"),
                rs.getString("PLACA")
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
            d.DIAGNOSTICOFINAL,
            v.PLACA
        FROM ORDEN o 
        LEFT JOIN DIAGNOSTICO d ON d.ORDEN_ID = o.ID
        JOIN VEHICULO v ON v.ID = o.VEHICULO_ID
        WHERE o.ESTADO <> 'INACTIVA'
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
                rs.getString("DIAGNOSTICOFINAL"),
                rs.getString("PLACA")

        ));
    } catch (DataAccessException e) {
        throw new BadRequestException("Error al obtener la lista de ordenes: " + e.getMessage());
    }
}



   @Transactional
public void asignarMecanico(String idOrden, String idMecanico, RolDTO rolDTO) {
    try {
        String sql = """
            INSERT INTO DTL_ORD_MEC (ORDEN_ID, MECANICO_ID, ROL, HORASTRABAJADAS)
            VALUES (?, ?, ?, ?)
        """;

        jdbcTemplate.update(sql,
                idOrden,
                idMecanico,
                rolDTO.rol().name(),  // SUPERVISOR o MECANICO
                0 // inicia con 0 horas
        );

    } catch (DataAccessException e) {
        throw new BadRequestException("Error al asignar el mecánico: " + e.getMessage());
    }
}


    @Transactional
    public void eliminarMecanico(String idOrden, String idMecanico) {
    try {
        String sql = "DELETE FROM DTL_ORD_MEC WHERE ORDEN_ID = ? AND MECANICO_ID = ?";
        int filas = jdbcTemplate.update(sql, idOrden, idMecanico);

        if (filas == 0) {
            throw new ResourceNotFoundException("No se encontró la relación entre la orden y el mecánico");
        }

        
    } catch (DataAccessException e) {
        throw new BadRequestException("Error al eliminar el mecqnico: " + e.getMessage());
    }
}


    @Transactional(readOnly = true)
    public List<ObtenerMecanicoOrdenDTO> obtenerMecanicosPorOrden(String idOrden) {

    String sql = """
        SELECT 
            m.ID,
            m.NOMBRE1,
            m.NOMBRE2,
            m.APELLIDO1,
            m.APELLIDO2,
            m.EMAIL,
            m.EXPERIENCIA,
            dom.ROL
        FROM DTL_ORD_MEC dom
        JOIN MECANICO m ON dom.MECANICO_ID = m.ID
        WHERE dom.ORDEN_ID = ?
    """;

    try {
        return jdbcTemplate.query(sql, (rs, rowNum) -> new ObtenerMecanicoOrdenDTO(
                rs.getString("ID"),
                rs.getString("NOMBRE1"),
                rs.getString("NOMBRE2"),
                rs.getString("APELLIDO1"),
                rs.getString("APELLIDO2"),
                rs.getString("EMAIL"),
                new RolDTO(Rol.valueOf(rs.getString("ROL"))),
                rs.getInt("EXPERIENCIA")
                ), idOrden);

    } catch (DataAccessException e) {
        throw new BadRequestException("Error al obtener los mecánicos de la orden: " + e.getMessage());
    }
}




    @Transactional
    public void registrarDiagnosticos(String idOrden, CrearDiagnosticoDTO dto) {
    try {
        String sql = """
            INSERT INTO DIAGNOSTICO (ORDEN_ID, DIAGNOSTICOINICIAL, DIAGNOSTICOFINAL)
            VALUES ( ?, ?, ?)
        """;

        jdbcTemplate.update(sql,
                idOrden,
                dto.diagnosticoInicial(),
                dto.diagnosticoFinal()
        );

       
    } catch (DataAccessException e) {
        throw new BadRequestException("Error al registrar diagnóstico: " + e.getMessage());
    }
}

   @Transactional(readOnly = true)
    public CrearDiagnosticoDTO obtenerDiagnostico(String idOrden) {

    String sql = """
        SELECT DIAGNOSTICOINICIAL, DIAGNOSTICOFINAL
        FROM DIAGNOSTICO
        WHERE ORDEN_ID = ?
    """;

    try {
        return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> new CrearDiagnosticoDTO(
                rs.getString("DIAGNOSTICOINICIAL"),
                rs.getString("DIAGNOSTICOFINAL")
        ), idOrden);
    }
     catch (EmptyResultDataAccessException e) {
        throw new ResourceNotFoundException("No se encontró diagnóstico para la orden " + idOrden);
    }
}


    // (agregar en la relación ternaria)
    @Transactional
    public void registrarServicio(String idOrden, String idMecanico, String idServicio, DetalleServicioMecanicoDTO dto) {
    try {
        String sql = """
            INSERT INTO MOS (ORDEN_ID, MECANICO_ID, SERVICIO_ID, ROL, HORAS_TRABAJADAS, FECHA_ASIGNACION)
            VALUES (?, ?, ?, ?, ?, ?)
        """;

        jdbcTemplate.update(sql,
                idOrden,
                idMecanico,
                idServicio,
                dto.rol(),
                dto.horasTrabajadas(),
                Timestamp.valueOf(dto.fechaAsignacion())
        );

       
    } catch (DataAccessException e) {
        throw new BadRequestException("Error al registrar servicio: " + e.getMessage());
    }
}


    // editar los datos de esa relación ternaria

    @Transactional
    public void actualizarDetalleServicio(String idOrden, String idMecanico, String idServicio, DetalleServicioMecanicoDTO dto) {
        try {
            String sql = """
            UPDATE MOS
            SET ROL = ?, HORAS_TRABAJADAS = ?, FECHA_ASIGNACION = ?, MECANICO_ID = ?, SERVICIO_ID = ?
            WHERE ORDEN_ID = ? AND MECANICO_ID = ? AND SERVICIO_ID = ?
        """;

            int filas = jdbcTemplate.update(sql,
                    dto.rol(),
                    dto.horasTrabajadas(),
                    Timestamp.valueOf(dto.fechaAsignacion()),
                    dto.idNuevoMecanico(),
                    dto.idNuevoServicio(),
                    idOrden,
                    idMecanico,
                    idServicio
            );

            if (filas == 0) {
                throw new ResourceNotFoundException("No se encontró la relación ternaria para actualizar");
            }

        } catch (DataAccessException e) {
            throw new BadRequestException("Error al actualizar detalle del servicio: " + e.getMessage());
        }
    }


    // devueve la lista de todos los servicios(el mecanico que los hizo, rol, horas trabajadas y fechaAsignacion)

    @Transactional(readOnly = true)
    public List<DetalleOrdenDTO> obtenerDetalleOrden(String idOrden) {

    String sql = """
        SELECT 
            m.NOMBRE1,
            m.NOMBRE2,
            m.APELLIDO1,
            m.APELLIDO2,
            s.TIPO,
            s.COSTOUNITARIO,
            s.DESCRIPCION,
            mos.ROL,
            mos.HORAS_TRABAJADAS,
            mos.FECHA_ASIGNACION
        FROM MOS mos
        JOIN MECANICO m ON mos.MECANICO_ID = m.ID
        JOIN SERVICIO s ON mos.SERVICIO_ID = s.ID
        WHERE mos.ORDEN_ID = ?
    """;

    try {
        return jdbcTemplate.query(sql, (rs, rowNum) -> new DetalleOrdenDTO(
                rs.getString("NOMBRE1"),
                rs.getString("NOMBRE2"),
                rs.getString("APELLIDO1"),
                rs.getString("APELLIDO2"),
                rs.getString("TIPO"),
                rs.getDouble("COSTOUNITARIO"),
                rs.getString("DESCRIPCION"),
                rs.getString("ROL"),
                rs.getInt("HORAS_TRABAJADAS"),
                rs.getTimestamp("FECHA_ASIGNACION") != null
                    ? rs.getTimestamp("FECHA_ASIGNACION").toLocalDateTime()
                    : null
        ), idOrden);

    } catch (DataAccessException e) {
        throw new BadRequestException("Error al obtener el detalle de la orden: " + e.getMessage());
    }
}




    @Transactional(readOnly = true)
    public List<ObtenerOrdenDTO> listaOrdenesPorCliente(String idCliente) {
    String sql = """
        SELECT 
            o.ID,
            o.DESCRIPCION,
            o.FECHAINGRESO,
            o.FECHASALIDA,
            o.ESTADO,
            d.DIAGNOSTICOINICIAL,
            d.DIAGNOSTICOFINAL,
            v.PLACA
        FROM ORDEN o
        LEFT JOIN DIAGNOSTICO d ON o.ID = d.ORDEN_ID
        JOIN VEHICULO v ON v.ID = o.VEHICULO_ID
        JOIN CLIENTES c ON c.ID = v.CLIENTES_ID
        WHERE c.ID = ?
   
    """;

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
            rs.getString("DIAGNOSTICOFINAL"),
            rs.getString("PLACA")
    ), idCliente);
}

}
