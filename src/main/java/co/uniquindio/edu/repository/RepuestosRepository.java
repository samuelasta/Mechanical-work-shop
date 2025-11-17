package co.uniquindio.edu.repository;

import co.uniquindio.edu.dto.repuesto.CrearRepuestoDTO;
import co.uniquindio.edu.dto.repuesto.ObtenerRepuestoDTO;
import co.uniquindio.edu.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Repository
public class RepuestosRepository {

    private final JdbcTemplate jdbcTemplate;


    @Transactional
    public void crearRepuesto(CrearRepuestoDTO crearRepuestoDTO, String idProveedor) {

        // Crear el ID del repuesto
        String repuestoId = UUID.randomUUID().toString();

        // Insertar el repuesto
        String sql = """
            INSERT INTO REPUESTO (ID, NOMBRE, COSTOUNITARIO, STOCK, ESTADO)
            VALUES (?, ?, ?, ?, ?)
        """;

        jdbcTemplate.update(sql,
                repuestoId,
                crearRepuestoDTO.nombre(),
                crearRepuestoDTO.costoUnitario(),
                crearRepuestoDTO.stock(),
                "ACTIVO"

        );
        // asignar repuesto y proveedor A la tabla intermedia
        String sqlAsignarRepuestoProveedor = """
            INSERT INTO PROVEEDORES_REPUESTO_FK (REPUESTO_ID, PROVEEDORES_ID) 
            VALUES (?, ?) 
        """;
        jdbcTemplate.update(sqlAsignarRepuestoProveedor,
                repuestoId,
                idProveedor);

    }


    @Transactional
    public void actualizarRepuesto(String id, CrearRepuestoDTO crearRepuestoDTO, String idProveedor) {

        // Validar existencia del repuesto
        Integer count = jdbcTemplate.queryForObject(
                "SELECT COUNT(1) FROM REPUESTO WHERE ID = ?",
                Integer.class,
                id
        );

        if (count == null || count == 0) {
            throw new ResourceNotFoundException("No existe repuesto con ID: " + id);
        }

        // Actualizar los datos
        String sql = """
            UPDATE REPUESTO
               SET NOMBRE = ?,
                   COSTOUNITARIO = ?,
                   STOCK = ?
             WHERE ID = ?
        """;

        jdbcTemplate.update(sql,
                crearRepuestoDTO.nombre(),
                crearRepuestoDTO.costoUnitario(),
                crearRepuestoDTO.stock(),
                id
        );
        // Actualizar el proveedor asignado al repuesto
        String sqlActualizarProveedor = """
              UPDATE PROVEEDORES_REPUESTO_FK
                SET PROVEEDORES_ID = ?
                WHERE REPUESTO_ID = ?
               """;

        jdbcTemplate.update(sqlActualizarProveedor, idProveedor, id);
    }


    @Transactional(readOnly = true)
    public List<ObtenerRepuestoDTO> listaRepuestos() {

        String sql = """
            SELECT r.ID, r.NOMBRE, r.COSTOUNITARIO, r.STOCK, pr.PROVEEDORES_ID
              FROM REPUESTO r
              JOIN PROVEEDORES_REPUESTO_FK pr ON pr.REPUESTO_ID = r.ID
              WHERE ESTADO <> 'INACTIVO'
             ORDER BY NOMBRE
        """;

        return jdbcTemplate.query(sql,
                (rs, rowNum) -> new ObtenerRepuestoDTO(
                        rs.getString("ID"),
                        rs.getString("NOMBRE"),
                        rs.getDouble("COSTOUNITARIO"),
                        rs.getInt("STOCK"),
                        rs.getString("PROVEEDORES_ID")
                )
        );
    }


    @Transactional(readOnly = true)
    public ObtenerRepuestoDTO obtenerRepuesto(String id) {

        String sql = """
            SELECT r.ID, r.NOMBRE, r.COSTOUNITARIO, r.STOCK,  pr.PROVEEDORES_ID 
              FROM REPUESTO
              JOIN PROVEEDORES_REPUESTO_FK pr ON pr.REPUESTO_ID = r.ID
             WHERE ID = ? AND ESTADO <> 'INACTIVO'
                              
        """;

        try {
            return jdbcTemplate.queryForObject(sql,
                    (rs, rowNum) -> new ObtenerRepuestoDTO(
                            rs.getString("ID"),
                            rs.getString("NOMBRE"),
                            rs.getDouble("COSTOUNITARIO"),
                            rs.getInt("STOCK"),
                            rs.getString("PROVEEDORES_ID")

                    ),
                    id
            );
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException("No se encontró repuesto con ID: " + id);
        }
    }


    @Transactional
    public void eliminarRepuesto(String id) {

        // Verificar existencia
        Integer count = jdbcTemplate.queryForObject(
                "SELECT COUNT(1) FROM REPUESTO WHERE ID = ?",
                Integer.class,
                id
        );

        if (count == null || count == 0) {
            throw new ResourceNotFoundException("No se encontró repuesto con ID: " + id);
        }

        jdbcTemplate.update("UPDATE REPUESTO SET ESTADO = 'INACTIVO' WHERE ID = ?", id);

    }
}
