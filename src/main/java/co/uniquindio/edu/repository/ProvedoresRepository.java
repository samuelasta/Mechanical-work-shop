package co.uniquindio.edu.repository;

import co.uniquindio.edu.dto.cliente.CrearTelefonoDTO;
import co.uniquindio.edu.dto.proveedor.CrearProveedorDTO;
import co.uniquindio.edu.dto.proveedor.ObtenerProveedorDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Repository
public class ProvedoresRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Transactional
    public void crearProvedor(CrearProveedorDTO crearProveedorDTO) {
        //crea id proveedor
        String proveedorId = java.util.UUID.randomUUID().toString();
        //crear proveedor
        String sqlProveedor = "INSERT INTO PROVEEDORES(ID,NOMBRE,EMAIL) VALUES(?,?,?)";
        jdbcTemplate.update(sqlProveedor,
                proveedorId,
                crearProveedorDTO.nombre(),
                crearProveedorDTO.email());

        String sqlTelefono = "INSERT INTO TELEFONO(ID,TIPO,NUMERO,PROVEEDOR_ID) VALUES(?,?,?,?)";
        //agrega telefono por telefono
        for (CrearTelefonoDTO t : crearProveedorDTO.telefonos()) {
            //crea id telefono
            String telefonoId = java.util.UUID.randomUUID().toString();
            jdbcTemplate.update(sqlTelefono, telefonoId, t.tipo(), t.numero(), proveedorId);
        }
    }

    @Transactional
    public void actualizarProveedor(String id, CrearProveedorDTO crearProveedorDTO) {
        //actualiza proveedor
        String sql = "UPDATE PROVEEDORES SET NOMBRE=?, EMAIL=? WHERE ID=?";
        jdbcTemplate.update(sql, crearProveedorDTO.nombre(), crearProveedorDTO.email(), id);
    }

    public void anhadirTelefono(String proveedorId, String numero, String tipo) {
        //añadir telefono
        String telefonoId = java.util.UUID.randomUUID().toString();
        String sql = "INSERT INTO TELEFONO(ID,PROVEEDOR_ID,NUMERO) VALUES(?,?,?)";
        jdbcTemplate.update(sql, telefonoId, tipo, numero, proveedorId);
    }

    public void actualizarTelefono(String proveedorId, String numero, String newNumero) {
        //cambia telefono
        String sql = "UPDATE TELEFONO SET NUMERO=? WHERE PROVEEDOR_ID=? AND NUMERO=?";
        jdbcTemplate.update(sql, newNumero, proveedorId, numero);
    }

    public void eliminarTelefono(String numero) {
        //elimina telefono
        String sql = "DELETE FROM TELEFONO WHEN NUMERO=?";
        jdbcTemplate.update(sql, numero);
    }

    public List<CrearTelefonoDTO> listarTelefonosPorId(String id) {
        String sql = "SELECT NUMERO, TIPO FROM TELEFONO WHERE PROVEEDOR_ID = ?";
        return jdbcTemplate.query(sql, new Object[]{id}, (rs, rowNum) ->
                new CrearTelefonoDTO(
                        rs.getString("NUMERO"),
                        rs.getString("TIPO")
                )
        );
    }

    public List<ObtenerProveedorDTO> listaProvedores() {
        //listar proveedores
        String sql = "SELECT ID,NOMBRE,EMAIL FROM PROVEEDORES";
        return jdbcTemplate.query(sql, (rs, rowNum) -> new ObtenerProveedorDTO(
                rs.getString("ID"),
                rs.getString("NOMBRE"),
                rs.getString("EMAIL"), null
        ));
    }

    public ObtenerProveedorDTO obtenerProveedor(String id) {
        //obtener proveedor
        String sqlProveedor = "SELECT ID,NOMBRE,EMAIL,TELEFONOS FROM PROVEEDORES WHERE ID=?";
        ObtenerProveedorDTO proveedorRow = jdbcTemplate.queryForObject(sqlProveedor,
                (rs, rowNum) -> new ObtenerProveedorDTO(
                        rs.getString("ID"),
                        rs.getString("NOMBRE"),
                        rs.getString("EMAIL"),
                        Collections.emptyList()
                ),id);
        String sqlTelefonos="SELECT TIPO,NUMERO FROM TELEFONO WHERE PROVEEDOR_ID=?";
        List<CrearTelefonoDTO> telefonos = jdbcTemplate.query(sqlTelefonos,
                (rs, rowNum) -> new CrearTelefonoDTO(
                        rs.getString("TIPO"),
                        rs.getString("NUMERO")
                ),id);
        return new ObtenerProveedorDTO(
                proveedorRow.id(),
                proveedorRow.nombre(),
                proveedorRow.email(),
                telefonos
        );
    }
    public void eliminarProveedor(String id){
        jdbcTemplate.update("DELETE FROM TELEFONO WHERE PROVEEDOR_ID=?",id);
        jdbcTemplate.update("DELETE FROM PROVEEDORES WHERE ID=?",id);
    }

}
