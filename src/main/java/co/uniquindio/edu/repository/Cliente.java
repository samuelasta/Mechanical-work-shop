package co.uniquindio.edu.repository;

import co.uniquindio.edu.dto.cliente.CrearClienteDTO;
import co.uniquindio.edu.dto.cliente.CrearTelefonoDTO;
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
}
