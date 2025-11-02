package co.uniquindio.edu.repository;

import co.uniquindio.edu.dto.cliente.*;
import co.uniquindio.edu.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class ClienteRepository {

    private final JdbcTemplate jdbcTemplate;

    @Transactional
    public void crearCliente(CrearClienteDTO clienteDTO) {

        // insertar el departamento del cliente
        String departamentoId = java.util.UUID.randomUUID().toString();
        String sqlDepartamento = "INSERT INTO DEPARTAMENTO(ID, DEPARTAMENTO) VALUES(?,?)";
        jdbcTemplate.update(sqlDepartamento,
                departamentoId,
                clienteDTO.departamento()
        );

        // incertar la ciudad del cliente
        String ciudadId = java.util.UUID.randomUUID().toString();
        String sqlCiudad = "INSERT INTO CIUDAD(ID, CIUDAD, DEPARTAMENTO_ID) VALUES(?,?,?)";
        jdbcTemplate.update(sqlCiudad,
                ciudadId,
                clienteDTO.ciudad(),
                departamentoId
        );

        // Insertar el barrio del cliente
        String barrioId = java.util.UUID.randomUUID().toString();
        String sqlBarrio = "INSERT INTO BARRIO(ID, BARRIO, CIUDAD_ID) VALUES(?,?,?)";
        jdbcTemplate.update(sqlBarrio,
                barrioId,
                clienteDTO.barrio(),
                ciudadId
        );

        //Inserta direccion del cliente
        String direccionId=java.util.UUID.randomUUID().toString();
        String sqlDireccion = "INSERT INTO DIRECCION(ID,DIRECCION,BARRIO_ID) VALUES(?,?,?)";
        jdbcTemplate.update(sqlDireccion,
                direccionId,
                clienteDTO.direccion(),
                barrioId
        );

        //crea idcliente
        String clienteId=java.util.UUID.randomUUID().toString();
        //Inserta cliente
        String sqlCliente = "INSERT INTO CLIENTES(ID,NOMBRE1,NOMBRE2,APELLIDO1,APELLIDO2,EMAIL,FECHAREGISTRO, DIRECCION_ID, ESTADO) VALUES(?,?,?,?,?,?,?,?,?)";
        jdbcTemplate.update(sqlCliente,
                clienteId,
                clienteDTO.nombre1(),
                clienteDTO.nombre2(),
                clienteDTO.apellido1(),
                clienteDTO.apellido2(),
                clienteDTO.email(),
                Timestamp.valueOf(java.time.LocalDateTime.now()),
                direccionId,
                "ACTIVO"
        );


        //insertar telefonos del cliente
        String sqlTelefono = "INSERT INTO TELEFONO(ID,TIPO,NUMERO,CLIENTES_ID)VALUES(?,?,?,?)";
        for (CrearTelefonoDTO t : clienteDTO.telefonos()) {
            //Id para cada telefono PK
            String telefonoId = java.util.UUID.randomUUID().toString();
            jdbcTemplate.update(sqlTelefono,
                    telefonoId,
                    t.tipo(),
                    t.numero(),
                    clienteId);
        }
    }

    @Transactional
    public void eliminarCliente(String idCliente) {
        Integer count = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM CLIENTES WHERE ID = ?",
                Integer.class,
                idCliente
        );

        if (count == null || count == 0) {
            throw new ResourceNotFoundException("No existe un cliente con ese ID.");
        }

        jdbcTemplate.update(
                "UPDATE CLIENTES SET ESTADO = ? WHERE ID = ?",
                "INACTIVO",
                idCliente
        );
    }


    @Transactional
    public void actualizarCliente(String clienteId, CrearClienteDTO dto) {

        try {
            //  Obtener IDs en cadena jerárquica
            String direccionId = jdbcTemplate.queryForObject(
                    "SELECT DIRECCION_ID FROM CLIENTES WHERE ID = ?", String.class, clienteId);

            String barrioId = jdbcTemplate.queryForObject(
                    "SELECT BARRIO_ID FROM DIRECCION WHERE ID = ?", String.class, direccionId);

            String ciudadId = jdbcTemplate.queryForObject(
                    "SELECT CIUDAD_ID FROM BARRIO WHERE ID = ?", String.class, barrioId);

            String departamentoId = jdbcTemplate.queryForObject(
                    "SELECT DEPARTAMENTO_ID FROM CIUDAD WHERE ID = ?", String.class, ciudadId);

            //  Actualizar cliente
            String sqlCliente = """
            UPDATE CLIENTES
            SET NOMBRE1=?, NOMBRE2=?, APELLIDO1=?, APELLIDO2=?, EMAIL=?
            WHERE ID=?
        """;
            jdbcTemplate.update(sqlCliente,
                    dto.nombre1(),
                    dto.nombre2(),
                    dto.apellido1(),
                    dto.apellido2(),
                    dto.email(),
                    clienteId
            );

            // Actualizar dirección y jerarquía de ubicación
            jdbcTemplate.update("UPDATE DIRECCION SET DIRECCION=? WHERE ID=?", dto.direccion(), direccionId);
            jdbcTemplate.update("UPDATE BARRIO SET BARRIO=? WHERE ID=?", dto.barrio(), barrioId);
            jdbcTemplate.update("UPDATE CIUDAD SET CIUDAD=? WHERE ID=?", dto.ciudad(), ciudadId);
            jdbcTemplate.update("UPDATE DEPARTAMENTO SET DEPARTAMENTO=? WHERE ID=?", dto.departamento(), departamentoId);

            if (dto.telefonos() != null && !dto.telefonos().isEmpty()) {
                jdbcTemplate.update("DELETE FROM TELEFONO WHERE CLIENTES_ID = ?", clienteId);
                for (CrearTelefonoDTO t : dto.telefonos()) {
                    String telefonoId = UUID.randomUUID().toString();
                    jdbcTemplate.update(
                            "INSERT INTO TELEFONO (ID, TIPO, NUMERO, CLIENTES_ID) VALUES (?, ?, ?, ?)",
                            telefonoId, t.tipo(), t.numero(), clienteId
                    );
                }
            }

        } catch (Exception e) {
            throw new ResourceNotFoundException(" No se encontró el cliente o su ubicación asociada (ID: " + clienteId + ")");
        }
    }

    @Transactional(readOnly = true)
    public ObtenerClienteDTO obtenerCliente(String clienteId) {
        try {
            // 1) Traer cliente + ubicación completa
            String sqlClienteUbic = """
            SELECT
              c.ID,
              c.NOMBRE1,
              c.NOMBRE2,
              c.APELLIDO1,
              c.APELLIDO2,
              c.EMAIL,
              c.FECHAREGISTRO,
              d.DIRECCION,
              b.BARRIO,
              ci.CIUDAD,
              dep.DEPARTAMENTO
            FROM CLIENTES c
            JOIN DIRECCION d     ON d.ID = c.DIRECCION_ID
            JOIN BARRIO b        ON b.ID = d.BARRIO_ID
            JOIN CIUDAD ci       ON ci.ID = b.CIUDAD_ID
            JOIN DEPARTAMENTO dep ON dep.ID = ci.DEPARTAMENTO_ID
            WHERE c.ID = ?
              AND c.ESTADO <> 'INACTIVO'
        """;

            var cliente = jdbcTemplate.queryForObject(
                    sqlClienteUbic,
                    (rs, rowNum) -> new Object[]{
                            rs.getString("ID"),
                            rs.getString("NOMBRE1"),
                            rs.getString("NOMBRE2"),
                            rs.getString("APELLIDO1"),
                            rs.getString("APELLIDO2"),
                            rs.getString("EMAIL"),
                            rs.getTimestamp("FECHAREGISTRO"),
                            rs.getString("DIRECCION"),
                            rs.getString("BARRIO"),
                            rs.getString("CIUDAD"),
                            rs.getString("DEPARTAMENTO")
                    },
                    clienteId
            );

            // Traer teléfonos del cliente
            String sqlTelefonos = """
            SELECT TIPO, NUMERO
            FROM TELEFONO
            WHERE CLIENTES_ID = ?
            ORDER BY ID
        """;
            List<CrearTelefonoDTO> telefonos = jdbcTemplate.query(
                    sqlTelefonos,
                    (rs, rowNum) -> new CrearTelefonoDTO(
                            rs.getString("TIPO"),
                            rs.getString("NUMERO")
                    ),
                    clienteId
            );

            // 3) Construir DTO final (ajusta el constructor a tu clase real)
            return new ObtenerClienteDTO(
                    (String) cliente[0], // id
                    (String) cliente[1], // nombre1
                    (String) cliente[2], // nombre2
                    (String) cliente[3], // apellido1
                    (String) cliente[4], // apellido2
                    (String) cliente[5], // email
                    telefonos,
                    (String) cliente[7], // direccion
                    (String) cliente[8], // barrio
                    (String) cliente[9], // ciudad
                    (String) cliente[10] // departamento

            );

        } catch (org.springframework.dao.EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException("No se encontró el cliente activo con ID: " + clienteId);
        }
    }


    public List<ObtenerClienteDTO> listarClientes() {
        //lista cliente
        String sql = "SELECT ID, NOMRE1,NOMRE2,APELLIDO1,APELLIDO2,EMAIL FROM CLIENTES";
        return jdbcTemplate.query(sql, (rs, rowNum) -> new ObtenerClienteDTO(
                rs.getString("ID"),
                rs.getString("NOMBRE1"),
                rs.getString("NOMBRE2"),
                rs.getString("APELLIDO1"),
                rs.getString("APELLIDO2"),
                rs.getString("EMAIL"),
                null, null, null, null, null
        ));
    }

}
