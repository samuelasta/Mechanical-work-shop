package co.uniquindio.edu.repository;

import co.uniquindio.edu.dto.cliente.CrearTelefonoDTO;
import co.uniquindio.edu.dto.proveedor.CrearProveedorDTO;
import co.uniquindio.edu.dto.proveedor.ObtenerProveedorDTO;
import co.uniquindio.edu.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
@RequiredArgsConstructor
@Repository
public class ProveedorRepository {

    private final JdbcTemplate jdbcTemplate;

    @Transactional
    public void crearProvedor(CrearProveedorDTO crearProveedorDTO) {
        // 1) Proveedor (UUID completo)
        String proveedorId = java.util.UUID.randomUUID().toString();
        jdbcTemplate.update(
                "INSERT INTO PROVEEDORES (ID, NOMBRE, EMAIL, ESTADO) VALUES (?,?,?,?)",
                proveedorId,
                crearProveedorDTO.nombre(),
                crearProveedorDTO.email(),
                "ACTIVO"
        );

        // 2) Teléfonos del proveedor (usa PROVEEDORES_ID)
        if (crearProveedorDTO.telefonos() != null) {
            String sqlTel = "INSERT INTO TELEFONO (ID, TIPO, NUMERO, PROVEEDORES_ID) VALUES (?,?,?,?)";
            for (CrearTelefonoDTO t : crearProveedorDTO.telefonos()) {
                jdbcTemplate.update(
                        sqlTel,
                        java.util.UUID.randomUUID().toString(),
                        t.tipo(),
                        t.numero(),
                        proveedorId
                );
            }
        }
    }
    @Transactional
    public void actualizarProveedor(String id, CrearProveedorDTO dto) {
        // 1) Actualiza datos básicos del proveedor si está activo
        int rows = jdbcTemplate.update(
                "UPDATE PROVEEDORES SET NOMBRE = ?, EMAIL = ? WHERE ID = ? AND ESTADO <> 'INACTIVO'",
                dto.nombre(), dto.email(), id
        );
        if (rows == 0) {
            throw new ResourceNotFoundException("Proveedor no existe o está INACTIVO: " + id);
        }

        // 2) Reemplazar teléfonos (soft delete + reinsertar)
        if (dto.telefonos() != null && !dto.telefonos().isEmpty()) {

            jdbcTemplate.update(
                    "DELETE FROM TELEFONO  WHERE PROVEEDORES_ID = ?",
                    id
            );


            String sqlInsertTel = "INSERT INTO TELEFONO (ID, TIPO, NUMERO, PROVEEDORES_ID) VALUES (?,?,?,?)";
            for (CrearTelefonoDTO t : dto.telefonos()) {
                jdbcTemplate.update(
                        sqlInsertTel,
                        java.util.UUID.randomUUID().toString(), // UUID completo
                        t.tipo(),
                        t.numero(),
                        id
                );
            }
        }
    }


    @Transactional(readOnly = true)
    public List<ObtenerProveedorDTO> listaProvedores() {
        String sql = """
            SELECT ID, NOMBRE, EMAIL
            FROM PROVEEDORES
            WHERE ESTADO <> 'INACTIVO'
            ORDER BY NOMBRE
        """;
        return jdbcTemplate.query(sql, (rs, rowNum) ->
                new ObtenerProveedorDTO(
                        rs.getString("ID"),
                        rs.getString("NOMBRE"),
                        rs.getString("EMAIL"),
                        java.util.Collections.emptyList()
                )
        );
    }

    @Transactional(readOnly = true)
    public ObtenerProveedorDTO obtenerProveedor(String id) {
        // 1) Proveedor activo
        String sqlProv = """
            SELECT ID, NOMBRE, EMAIL
            FROM PROVEEDORES
            WHERE ID = ? AND ESTADO <> 'INACTIVO'
        """;
        ObtenerProveedorDTO base = jdbcTemplate.queryForObject(
                sqlProv,
                (rs, rowNum) -> new ObtenerProveedorDTO(
                        rs.getString("ID"),
                        rs.getString("NOMBRE"),
                        rs.getString("EMAIL"),
                        java.util.Collections.emptyList()
                ),
                id
        );

        // 2) Teléfonos activos del proveedor
        String sqlTel = """
            SELECT TIPO, NUMERO
            FROM TELEFONO
            WHERE PROVEEDORES_ID = ? AND ESTADO <> 'INACTIVO'
            ORDER BY ID
        """;
        List<CrearTelefonoDTO> telefonos = jdbcTemplate.query(
                sqlTel,
                (rs, rowNum) -> new CrearTelefonoDTO(
                        rs.getString("TIPO"),
                        rs.getString("NUMERO")
                ),
                id
        );

        return new ObtenerProveedorDTO(base.id(), base.nombre(), base.email(), telefonos);
    }

    @Transactional
    public void eliminarProveedor(String id) {
        // Soft delete: marcar proveedor y sus teléfonos como INACTIVO
        jdbcTemplate.update(
                "UPDATE TELEFONO SET ESTADO = 'INACTIVO' WHERE PROVEEDORES_ID = ?",
                id
        );
        int rows = jdbcTemplate.update(
                "UPDATE PROVEEDORES SET ESTADO = 'INACTIVO' WHERE ID = ?",
                id
        );
        if (rows == 0) {
            throw new ResourceNotFoundException("Proveedor no existe: " + id);
        }
    }
}




