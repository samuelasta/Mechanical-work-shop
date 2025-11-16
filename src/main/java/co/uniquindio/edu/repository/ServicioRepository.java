package co.uniquindio.edu.repository;

import co.uniquindio.edu.dto.servicio.CrearServicioDTO;
import co.uniquindio.edu.dto.servicio.ObtenerServicioDTO;
import co.uniquindio.edu.exception.ResourceNotFoundException;
import co.uniquindio.edu.model.enums.TipoServicio;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Repository
public class ServicioRepository {

    private final JdbcTemplate jdbcTemplate;


    @Transactional
    public void crearServicio(CrearServicioDTO crearServicioDTO) {

        // Crear ID del servicio
        String servicioId = UUID.randomUUID().toString();

        // Insertar servicio (fecha actual)
        String sql = "INSERT INTO SERVICIO (ID, DESCRIPCION, TIPO, FECHA, COSTOUNITARIO, ESTADO) VALUES (?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql,
                servicioId,
                crearServicioDTO.descripcion(),
                crearServicioDTO.tipoServicio().toString(),
                Date.valueOf(LocalDate.now()),
                crearServicioDTO.costoUnitario(),
                "ACTIVO"
        );
    }


    @Transactional
    public void actualizarServicio(String id, CrearServicioDTO crearServicioDTO) {

        // Verificar que el servicio exista
        Integer count = jdbcTemplate.queryForObject(
                "SELECT COUNT(1) FROM SERVICIO WHERE ID = ?",
                Integer.class,
                id
        );

        if (count == null || count == 0) {
            throw new ResourceNotFoundException("No existe servicio con ID: " + id);
        }

        // Actualizar información del servicio
        String sql = """
            UPDATE SERVICIO
               SET DESCRIPCION = ?,
                   TIPO = ?,
                   COSTOUNITARIO = ?,
                   FECHA = ?
             WHERE ID = ?
        """;

        jdbcTemplate.update(sql,
                crearServicioDTO.descripcion(),
                crearServicioDTO.tipoServicio().toString(),
                crearServicioDTO.costoUnitario(),
                Date.valueOf(LocalDate.now()),
                id
        );
    }


    @Transactional(readOnly = true)
    public List<ObtenerServicioDTO> listaServicios() {
        String sql = """
            SELECT ID, DESCRIPCION, TIPO, COSTOUNITARIO, FECHA
              FROM SERVICIO
              WHERE ESTADO <> 'INACTIVO'
              ORDER BY FECHA DESC
        """;

        return jdbcTemplate.query(sql, (rs, rowNum) ->
                new ObtenerServicioDTO(
                        rs.getString("ID"),
                        TipoServicio.valueOf(rs.getString("TIPO")),
                        rs.getString("DESCRIPCION"),
                        rs.getObject("FECHA", LocalDate.class),
                        rs.getInt("COSTOUNITARIO")
                )
        );
    }


    @Transactional(readOnly = true)
    public ObtenerServicioDTO obtenerServicio(String id) {
        String sql = """
            SELECT ID, DESCRIPCION, TIPO, COSTOUNITARIO, FECHA
              FROM SERVICIO
             WHERE ID = ? AND ESTADO <> 'INACTIVO'
        """;

        try {
            return jdbcTemplate.queryForObject(sql, (rs, rowNum) ->
                            new ObtenerServicioDTO(
                                    rs.getString("ID"),
                                    TipoServicio.valueOf(rs.getString("TIPO")),
                                    rs.getString("DESCRIPCION"),
                                    rs.getObject("FECHA", LocalDate.class),
                                    rs.getInt("COSTOUNITARIO")
                            ),
                    id
            );
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException("No se encontró el servicio con ID: " + id);
        }
    }


    @Transactional
    public void eliminarServicio(String id) {

        Integer count = jdbcTemplate.queryForObject(
                "SELECT COUNT(1) FROM SERVICIO WHERE ID = ?",
                Integer.class,
                id
        );

        if (count == null || count == 0) {
            throw new ResourceNotFoundException("No se encontró servicio con ID: " + id);
        }

        jdbcTemplate.update("UPDATE SERVICIO SET ESTADO =?  WHERE ID = ?", "INACTIVO", id);
    }


    @Transactional(readOnly = true)
    public List<ObtenerServicioDTO> listaServiciosPorMecanico(String idMecanico) {

        String sql = """
            SELECT s.ID, s.DESCRIPCION, s.TIPO, s.COSTOUNITARIO, s.FECHA
              FROM SERVICIO s
              JOIN ORDEN_SERVICIO_MECANICO osm ON osm.SERVICIO_ID = s.ID
             WHERE osm.MECANICO_ID = ?
             ORDER BY s.FECHA DESC
        """;

        return jdbcTemplate.query(sql, (rs, rowNum) ->
                        new ObtenerServicioDTO(
                                rs.getString("ID"),
                                TipoServicio.valueOf(rs.getString("TIPO")),
                                rs.getString("DESCRIPCION"),
                                rs.getObject("FECHA", LocalDate.class),
                                rs.getInt("COSTOUNITARIO")
                        ),
                idMecanico
        );
    }


    @Transactional(readOnly = true)
    public List<ObtenerServicioDTO> listaServiciosPorVehiculo(String idCliente, String placaVehiculo) {

        String sql = """
            SELECT s.ID, s.DESCRIPCION, s.TIPO, s.COSTOUNITARIO
              FROM SERVICIO s
              JOIN ORDEN_SERVICIO_MECANICO osm ON osm.SERVICIO_ID = s.ID
              JOIN ORDEN o ON o.ID = osm.ORDEN_ID
              JOIN VEHICULO v ON v.ID = o.VEHICULO_ID
             WHERE v.CLIENTES_ID = ?
               AND v.PLACA = ?
               AND o.ESTADO = 'FINALIZADA'
             ORDER BY s.FECHA DESC
        """;

        return jdbcTemplate.query(sql, (rs, rowNum) ->
                        new ObtenerServicioDTO(
                                rs.getString("ID"),
                                TipoServicio.valueOf(rs.getString("TIPO")),
                                rs.getString("DESCRIPCION"),
                                rs.getObject("FECHA", LocalDate.class),
                                rs.getInt("COSTOUNITARIO")
                        ),
                idCliente, placaVehiculo
        );
    }
}
