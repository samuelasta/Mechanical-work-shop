package co.uniquindio.edu.repository;

import co.uniquindio.edu.dto.vehiculo.CrearVehiculoDTO;
import co.uniquindio.edu.dto.vehiculo.ObtenerVehiculoDTO;
import co.uniquindio.edu.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;


import co.uniquindio.edu.dto.vehiculo.CrearVehiculoDTO;
import co.uniquindio.edu.dto.vehiculo.ObtenerVehiculoDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Repository
public class VehiculoRepository {

    private final JdbcTemplate jdbcTemplate;

    @Transactional
    public void crearVehiculo(String idCliente, CrearVehiculoDTO vehiculoDTO) {

        // Validar que el cliente esté activo antes de crear el vehículo
        Integer clienteActivo = jdbcTemplate.queryForObject(
                "SELECT COUNT(1) FROM CLIENTES WHERE ID = ? AND ESTADO <> 'INACTIVO'",
                Integer.class,
                idCliente
        );

        if (clienteActivo == null || clienteActivo == 0) {
            throw new ResourceNotFoundException("Cliente no existe o está INACTIVO: " + idCliente);
        }

        //  Insertar la MARCA del vehículo
        String marcaId = UUID.randomUUID().toString();
        String sqlMarca = "INSERT INTO MARCA (ID, NOMBRE) VALUES (?, ?)";
        jdbcTemplate.update(sqlMarca,
                marcaId,
                vehiculoDTO.marca()
        );

        //  Insertar el MODELO (relacionado con la marca)
        String modeloId = UUID.randomUUID().toString();
        String sqlModelo = "INSERT INTO MODELO (ID, NOMBRE, MARCA_ID) VALUES (?,?,?)";
        jdbcTemplate.update(sqlModelo,
                modeloId,
                vehiculoDTO.modelo(),
                marcaId
        );

        //  Insertar el VEHICULO (relacionado con el modelo y el cliente)
        String vehiculoId = UUID.randomUUID().toString();
        String sqlVehiculo = "INSERT INTO VEHICULO (ID, PLACA, ANIO, COLOR, MODELO_ID, CLIENTES_ID) VALUES (?,?,?,?,?,?)";
        jdbcTemplate.update(sqlVehiculo,
                vehiculoId,
                vehiculoDTO.placa(),
                vehiculoDTO.anio(),
                vehiculoDTO.color(),
                modeloId,
                idCliente
        );
    }


    @Transactional
    public void actualizarVehiculo(String idVehiculo, String idCliente, CrearVehiculoDTO vehiculoDTO) {

        //  Verificar existencia del vehículo
        Integer existeVehiculo = jdbcTemplate.queryForObject(
                "SELECT COUNT(1) FROM VEHICULO WHERE ID = ?",
                Integer.class,
                idVehiculo
        );
        if (existeVehiculo == null || existeVehiculo == 0) {
            throw new ResourceNotFoundException("No existe vehículo con ID: " + idVehiculo);
        }

        //  Validar cliente activo
        Integer clienteActivo = jdbcTemplate.queryForObject(
                "SELECT COUNT(1) FROM CLIENTES WHERE ID = ? AND ESTADO <> 'INACTIVO'",
                Integer.class,
                idCliente
        );
        if (clienteActivo == null || clienteActivo == 0) {
            throw new ResourceNotFoundException("Cliente no existe o está INACTIVO: " + idCliente);
        }

        //  Obtener la cadena de relaciones: MODELO → MARCA
        String modeloId = jdbcTemplate.queryForObject(
                "SELECT MODELO_ID FROM VEHICULO WHERE ID = ?",
                String.class,
                idVehiculo
        );

        String marcaId = jdbcTemplate.queryForObject(
                "SELECT MARCA_ID FROM MODELO WHERE ID = ?",
                String.class,
                modeloId
        );

        //  Actualizar datos en cascada (igual que cliente: dirección → barrio → ciudad → departamento)
        // Actualizar la MARCA
        String sqlMarca = "UPDATE MARCA SET NOMBRE = ? WHERE ID = ?";
        jdbcTemplate.update(sqlMarca,
                vehiculoDTO.marca(),
                marcaId
        );

        // Actualizar el MODELO
        String sqlModelo = "UPDATE MODELO SET NOMBRE = ? WHERE ID = ?";
        jdbcTemplate.update(sqlModelo,
                vehiculoDTO.modelo(),
                modeloId
        );

        // Actualizar el VEHICULO
        String sqlVehiculo = """
            UPDATE VEHICULO
               SET PLACA = ?,
                   ANIO  = ?,
                   COLOR = ?,
                   CLIENTES_ID = ?
             WHERE ID = ?
        """;
        jdbcTemplate.update(sqlVehiculo,
                vehiculoDTO.placa(),
                vehiculoDTO.anio(),
                vehiculoDTO.color(),
                idCliente,
                idVehiculo
        );
    }

    @Transactional(readOnly = true)
    public List<ObtenerVehiculoDTO> listaVehiculos() {

        String sql = """
            SELECT v.ID,
                   v.PLACA,
                   v.ANIO,
                   v.COLOR,
                   mo.NOMBRE AS MODELO,
                   ma.NOMBRE AS MARCA,
                   V.CLIENTES_ID
              FROM VEHICULO v 
              JOIN MODELO mo ON mo.ID = v.MODELO_ID
              JOIN MARCA ma ON ma.ID = mo.MARCA_ID
              WHERE v.ESTADO <> 'INACTIVO'
             ORDER BY v.PLACA
        """;

        return jdbcTemplate.query(sql,
                (rs, rowNum) -> new ObtenerVehiculoDTO(
                        rs.getString("ID"),
                        rs.getString("PLACA"),
                        rs.getInt("ANIO"),
                        rs.getString("COLOR"),
                        rs.getString("MODELO"),
                        rs.getString("MARCA"),
                        rs.getString("CLIENTES_ID")
                )
        );
    }

    @Transactional(readOnly = true)
    public ObtenerVehiculoDTO obtenerVehiculo(String idVehiculo) {

        String sql = """
            SELECT v.ID,
                   v.PLACA,
                   v.ANIO,
                   v.COLOR,
                   mo.NOMBRE AS MODELO,
                   ma.NOMBRE AS MARCA,
                   v.CLIENTES_ID
              FROM VEHICULO v
              JOIN MODELO mo ON mo.ID = v.MODELO_ID
              JOIN MARCA ma ON ma.ID = mo.MARCA_ID
             WHERE v.ID = ? AND v.ESTADO <> 'INACTIVO'
                            
        """;

        try {
            return jdbcTemplate.queryForObject(sql,
                    (rs, rowNum) -> new ObtenerVehiculoDTO(
                            rs.getString("ID"),
                            rs.getString("PLACA"),
                            rs.getInt("ANIO"),
                            rs.getString("COLOR"),
                            rs.getString("MODELO"),
                            rs.getString("MARCA"),
                            rs.getString("CLIENTES_ID")
                    ),
                    idVehiculo
            );
        } catch (Exception e) {
            throw new ResourceNotFoundException("No se encontró vehículo con ID: " + idVehiculo);
        }
    }

    @Transactional
    public void eliminarVehiculo(String idVehiculo) {

        Integer existe = jdbcTemplate.queryForObject(
                "SELECT COUNT(1) FROM VEHICULO WHERE ID = ?",
                Integer.class,
                idVehiculo
        );

        if (existe == null || existe == 0) {
            throw new ResourceNotFoundException("No se encontró vehículo con ID: " + idVehiculo);
        }

        jdbcTemplate.update("UPDATE VEHICULO SET ESTADO = 'INACTIVO' WHERE ID = ?", idVehiculo);

    }
}

