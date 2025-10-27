package co.uniquindio.edu.repository;

import co.uniquindio.edu.dto.mecanico.CrearMecanicoDTO;
import co.uniquindio.edu.dto.mecanico.ObtenerMecanicoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MecanicoRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void crearMecanico(CrearMecanicoDTO crearMecanicoDTO) {
        //crea mecanico
        String sql = "INSERT INTO MECANICO (NOMBRE1,NOMBRE2,APELLIDO1,APELLIDO2,EMAIL,EXPERIENCIA,ESPECIALIZACION) VALUES(?,?,?,?,?,?,?)";
        jdbcTemplate.update(sql,
                crearMecanicoDTO.nombre1(),
                crearMecanicoDTO.nombre2(),
                crearMecanicoDTO.apellido1(),
                crearMecanicoDTO.apellido2(),
                crearMecanicoDTO.email(),
                crearMecanicoDTO.experiencia(),
                crearMecanicoDTO.especializacion()
        );
    }

    public void actualizarMecanico(String mecanicoId, CrearMecanicoDTO crearMecanicoDTO) {
        //actualiza mecanico
        String sql = ("UPDATE MECANICO SET NOMBRE1=? NOMBRE=2=? APELLIDO1=? APELLIDO2=? EMAIL=? EXPERIENCIA=? ESPECIALIZACION=? WHERE ID=?");
        jdbcTemplate.update(sql,
                crearMecanicoDTO.nombre1(),
                crearMecanicoDTO.nombre2(),
                crearMecanicoDTO.apellido1(),
                crearMecanicoDTO.apellido2(),
                crearMecanicoDTO.email(),
                crearMecanicoDTO.experiencia(),
                crearMecanicoDTO.especializacion()
        );
    }

    public List<ObtenerMecanicoDTO> listaMecanicos() {
        //lista mecanicos
        String sql = "SELECT NOMBRE1,NOMBRE2,APELLIDO1,APELLIDO2,EMAIL,EXPERIENCIA,ESPECIALIZACION FROM MECANICO";
        return jdbcTemplate.query(sql,
                (rs, rowNum) -> new ObtenerMecanicoDTO(
                        rs.getString("ID"),
                        rs.getString("NOMBRE1"),
                        rs.getString("NOMBRE2"),
                        rs.getString("APELLIDO1"),
                        rs.getString("APELLIDO2"),
                        rs.getString("EMAIL"),
                        rs.getInt("EXPERIENCIA"),
                        rs.getString("ESPECIALIZACION")
                )
        );
    }

    public ObtenerMecanicoDTO obtenerMecanico(String id) {
        //obtiene mecanico
        String sql = "SELECT ID,NOMBRE1,NOMBRE2,APELLIDO1,APELLIDO2,EMAIL,EXPERIENCIA,ESPECIALIZACION FROM MECANICO WHERE ID=?";
        return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> new ObtenerMecanicoDTO(
                rs.getString("ID"),
                rs.getString("NONBRE1"),
                rs.getString("NOMBRE2"),
                rs.getString("APELLIDO1"),
                rs.getString("APELLIDO2"),
                rs.getString("EMAIL"),
                rs.getInt("EXPERIENCIA"),
                rs.getString("ESPECIALIZACION")
        ));
    }
    public void eliminarMecanico(String id){
        //elimina mecanico
        jdbcTemplate.update("DELETE FROM MECANICO WHERE ID=?",id);
    }

}
