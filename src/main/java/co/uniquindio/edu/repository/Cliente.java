package co.uniquindio.edu.repository;

import co.uniquindio.edu.dto.cliente.*;
import co.uniquindio.edu.model.Direccion;
import co.uniquindio.edu.model.Telefono;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public class Cliente {
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
        String sqlTelefono="INSERT INTO TELEFONO(ID,CLIENTE_ID,NUMERO)VALUES(?,?,?)";
        for(CrearTelefonoDTO t : clienteDTO.telefonos()){
            //Id para cada telefono PK
            String telefonoId=java.util.UUID.randomUUID().toString();
            jdbcTemplate.update(sqlTelefono,telefonoId,id,t.numero());
        }
    }
    public void eliminarCliente(String id){
        //elimina cliente
        jdbcTemplate.update("DELETE FROM TELEFONO WHERE CLIENTE_ID=?",id);
        jdbcTemplate.update("DELETE FROM DIRECCION WHERE ID=?",id);
        jdbcTemplate.update("DELETE FROM CLIENTE WHERE ID=?",id);
    }
    public void actualizarCliente(String id,CrearClienteDTO clienteDTO){
        //muy largo que pereza
    }
    public ObtenerClienteDTO obtenerCliente(String id){
        //obtener cliente
        String sqlCliente="SELECT ID, NOMBRE1,NOMBRE2,APELLIDO1,APELLIDO2,EMAIL,FECHAREGISTRO FROM CLIENTE WHERE ID =?";
        var clienteRow=jdbcTemplate.queryForObject(sqlCliente,
                (rs, rowNum) -> new Object[]{
                        rs.getString("ID"),
                        rs.getString("NOMBRE1"),
                        rs.getString("NOMBRE2"),
                        rs.getString("APELLIDO1"),
                        rs.getString("APELLIDO2"),
                        rs.getString("EMAIL"),
                },id);
        //obtener direccion
        String sqlDireccion="SELECT DIRECCION,BARRIO,CIUDAD,DEPARTAMENTO FROM DIRECCION WHERE ID=?";
        var direccionRow =jdbcTemplate.queryForObject(sqlDireccion,
                (rs, rowNum) -> new Object[] {
                        rs.getString("DIRECCION"),
                        rs.getString("BARRIO"),
                        rs.getString("CIUDAD"),
                        rs.getString("DEPARTAMENTO")
                },id);

        //obetner telefonos
        String sqlTelefonos="SELECT ID,NUMERO FROM TELEFONO WHERE CLIENTE_ID=?";
        List<CrearTelefonoDTO> telefonos = jdbcTemplate.query(sqlTelefonos,
                (rs, rowNum) -> new CrearTelefonoDTO(
                        rs.getString("TIPOTELEFONO"),
                        rs.getString("NUMERO")
                ),id);
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
}
