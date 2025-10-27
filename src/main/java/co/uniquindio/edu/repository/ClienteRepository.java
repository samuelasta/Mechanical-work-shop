package co.uniquindio.edu.repository;

import co.uniquindio.edu.dto.cliente.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Repository
public class ClienteRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void crearCliente(String id, CrearClienteDTO clienteDTO) {
        //Inserta cliente
        String sqlCliente = "INSERT INTO CLIENTE (ID,NOMBRE1,NOMBRE2,APELLIDO1,APELLIDO2,EMAIL,FECHAREGISTRO) VALUES(?,?,?,?,?,?,?)";
        jdbcTemplate.update(sqlCliente,
                id,
                clienteDTO.nombre1(),
                clienteDTO.nombre2(),
                clienteDTO.apellido1(),
                clienteDTO.apellido2(),
                clienteDTO.email(),
                Timestamp.valueOf(java.time.LocalDateTime.now())
        );

        //Inserta direccion del cliente
        String sqlDireccion = "INSERT INTO DIRECCION(ID,DIRECCION,BARRIO,CIUDAD,DEPARTAMENTO) VALUES(?,?,?,?,?)";
        jdbcTemplate.update(sqlDireccion,
                id,
                clienteDTO.direccion(),
                clienteDTO.barrio(),
                clienteDTO.ciudad(),
                clienteDTO.departamento()
        );
        //insertar telefono del cliente
        String sqlTelefono = "INSERT INTO TELEFONO(ID,CLIENTE_ID,NUMERO)VALUES(?,?,?)";
        for (CrearTelefonoDTO t : clienteDTO.telefonos()) {
            //Id para cada telefono PK
            String telefonoId = java.util.UUID.randomUUID().toString();
            jdbcTemplate.update(sqlTelefono, telefonoId, id, t.numero());
        }
    }

    public void eliminarCliente(String id) {
        //elimina cliente
        jdbcTemplate.update("DELETE FROM TELEFONO WHERE CLIENTE_ID=?", id);
        jdbcTemplate.update("DELETE FROM DIRECCION WHERE ID=?", id);
        jdbcTemplate.update("DELETE FROM CLIENTE WHERE ID=?", id);
    }

    public void actualizarCliente(String clienteId, CrearClienteDTO clienteDTO) {
        //actualizar cliente
        String sql = "UPDATE CLIENTE SET NOMBRE1=?,NOMBRE2=?,APELLIDO1=?,APELLIDO2=?,EMAIL=? WHERE ID=?";
        jdbcTemplate.update(sql,
                clienteDTO.nombre1(),
                clienteDTO.nombre2(),
                clienteDTO.apellido1(),
                clienteDTO.apellido2(),
                clienteDTO.email(), clienteId);
    }

    //rebice el dto porque no tiene la fecharevision entonces que reglas se manejan ahi
    public ObtenerClienteDTO obtenerCliente(String id) {
        //obtener cliente
        String sqlCliente = "SELECT ID, NOMBRE1,NOMBRE2,APELLIDO1,APELLIDO2,EMAIL,FECHAREGISTRO FROM CLIENTE WHERE ID =?";
        var clienteRow = jdbcTemplate.queryForObject(sqlCliente,
                (rs, rowNum) -> new Object[]{
                        rs.getString("ID"),
                        rs.getString("NOMBRE1"),
                        rs.getString("NOMBRE2"),
                        rs.getString("APELLIDO1"),
                        rs.getString("APELLIDO2"),
                        rs.getString("EMAIL"),
                }, id);
        //obtener direccion
        String sqlDireccion = "SELECT DIRECCION,BARRIO,CIUDAD,DEPARTAMENTO FROM DIRECCION WHERE ID=?";
        var direccionRow = jdbcTemplate.queryForObject(sqlDireccion,
                (rs, rowNum) -> new Object[]{
                        rs.getString("DIRECCION"),
                        rs.getString("BARRIO"),
                        rs.getString("CIUDAD"),
                        rs.getString("DEPARTAMENTO")
                }, id);

        //obetner telefonos
        String sqlTelefonos = "SELECT ID,NUMERO FROM TELEFONO WHERE CLIENTE_ID=?";
        List<CrearTelefonoDTO> telefonos = jdbcTemplate.query(sqlTelefonos,
                (rs, rowNum) -> new CrearTelefonoDTO(
                        rs.getString("TIPOTELEFONO"),
                        rs.getString("NUMERO")
                ), id);
        //contruye el final DTO
        return new ObtenerClienteDTO(
                (String) clienteRow[0],
                (String) clienteRow[1],
                (String) clienteRow[2],
                (String) clienteRow[3],
                (String) clienteRow[4],
                (String) clienteRow[5],
                telefonos,
                (String) direccionRow[0],
                (String) direccionRow[1],
                (String) direccionRow[2],
                (String) direccionRow[3]
        );
    }

    public List<ObtenerClienteDTO> listarClientes() {
        //lista cliente
        String sql = "SELECT ID, NOMRE1,NOMRE2,APELLIDO1,APELLIDO2,EMAIL FROM CLIENTE";
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

    public void anhadirTelefono(String clienteId, String numero, String tipo) {
        //añadir telefono
        String telefonoId = java.util.UUID.randomUUID().toString();
        String sql = "INSERT INTO TELEFONO(ID,CLIENTE_ID,NUMERO) VALUES(?,?,?)";
        jdbcTemplate.update(sql, telefonoId, tipo, numero, clienteId);
    }

    public void cambiarTelefono(String clienteId, String numero) {
        //cambia telefono
        String sql = "UPDATE TELEFONO SET NUMERO=? WHERE CLIENTE_ID=?";
        jdbcTemplate.update(sql, numero, clienteId);
    }

    public void eliminarTelefono(String numero) {
        //elimina telefono
        String sql = "DELETE FROM TELEFONO WHEN CLIENTE_ID=?";
        jdbcTemplate.update(sql, numero);
    }

    public void cambiarDireccion(String clienteId, String direccion, String barrio, String ciudad, String departamento) {
        //cambia direccion
        String sql = "UPDATE DIRECCION SET DIRECCION=?,BARRIO=?,CIUDAD=?,DEPARTAMENTO=? WHERE ID=?";
        jdbcTemplate.update(sql, direccion, barrio, ciudad, departamento);
    }
}
