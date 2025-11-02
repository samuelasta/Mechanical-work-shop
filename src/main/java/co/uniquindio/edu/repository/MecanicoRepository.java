package co.uniquindio.edu.repository;

import co.uniquindio.edu.dto.mecanico.CrearMecanicoDTO;
import co.uniquindio.edu.dto.mecanico.ObtenerMecanicoDTO;
import co.uniquindio.edu.exception.ResourceNotFoundException;
import co.uniquindio.edu.model.enums.TipoEspecializacion;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@RequiredArgsConstructor
@Repository
public class MecanicoRepository {

    private final JdbcTemplate jdbcTemplate;

    @Transactional
    public void crearMecanico(CrearMecanicoDTO crearMecanicoDTO) {

        //crea mecanico
        String mecanicoId = java.util.UUID.randomUUID().toString();
        String sqlMecanico = "INSERT INTO MECANICO (ID, NOMBRE1,NOMBRE2,APELLIDO1,APELLIDO2,EMAIL,EXPERIENCIA, ESTADO) VALUES(?,?,?,?,?,?,?,?)";
        jdbcTemplate.update(sqlMecanico,
                mecanicoId,
                crearMecanicoDTO.nombre1(),
                crearMecanicoDTO.nombre2(),
                crearMecanicoDTO.apellido1(),
                crearMecanicoDTO.apellido2(),
                crearMecanicoDTO.email(),
                crearMecanicoDTO.experiencia(),
                crearMecanicoDTO.estado()
        );


        String sqlEspecializacion = "INSERT INTO ESPECIALIZACION(ID, NOMBRE, MECANICO_ID) VALUES(?,?,?)";
        for(TipoEspecializacion tipoEspecializacion : crearMecanicoDTO.especializacion()){
            String especializacionId = java.util.UUID.randomUUID().toString();
            jdbcTemplate.update(sqlEspecializacion,
                    especializacionId,
                    tipoEspecializacion.toString(),
                    mecanicoId);
        }

    }

    @Transactional
    public void actualizarMecanico(String mecanicoId, CrearMecanicoDTO crearMecanicoDTO) {

        String sqlMecanico = "UPDATE MECANICO SET NOMBRE1=?, NOMBRE2=?, APELLIDO1=?, APELLIDO2=?, EXPERIENCIA=?, EMAIL=? WHERE ID=?";
        jdbcTemplate.update(sqlMecanico,
                crearMecanicoDTO.nombre1(),
                crearMecanicoDTO.nombre2(),
                crearMecanicoDTO.apellido1(),
                crearMecanicoDTO.apellido2(),
                crearMecanicoDTO.experiencia(),
                crearMecanicoDTO.email(),
                mecanicoId);

        if(!crearMecanicoDTO.especializacion().isEmpty()){
            jdbcTemplate.update("DELETE FROM ESPECIALIZACION WHERE MECANICO_ID=?", mecanicoId);
            for(TipoEspecializacion t : crearMecanicoDTO.especializacion()){
                String especializacionId = java.util.UUID.randomUUID().toString();
                jdbcTemplate.update("INSERT INTO ESPECIALIZACION (ID, NOMBRE, MECANICO_ID) VALUES(?,?,?)"
                , especializacionId, t.toString(), mecanicoId);
            }
        }
    }

    // Hacemos un soft delete
    @Transactional
    public void eliminarMecanico(String id){
        //elimina mecanico
      Integer count = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM  MECANICO WHERE MECANICO_ID=?", Integer.class, id);

      if(count == 0 || count == null){
          throw new ResourceNotFoundException("no existe este mecanico");
      }

      jdbcTemplate.update("UPDATE MECANICO SET ESTADO=? WHERE MECANICO_ID=?", "INACTIVO",id);

    }

    @Transactional(readOnly = true)
    public ObtenerMecanicoDTO obtenerMecanico(String id) {

        try {
            // Obtener los datos del mecánico
            String sqlMecanico = """
            SELECT ID, NOMBRE1, NOMBRE2, APELLIDO1, APELLIDO2, EXPERIENCIA, EMAIL
            FROM MECANICO
            WHERE ID = ? AND ESTADO <> 'INACTIVO'
        """;

            // Mapear directamente a un record parcial (sin la lista todavía)
            var mecanicoBase = jdbcTemplate.queryForObject(
                    sqlMecanico,
                    (rs, rowNum) -> new Object[]{
                            rs.getString("ID"),
                            rs.getString("NOMBRE1"),
                            rs.getString("NOMBRE2"),
                            rs.getString("APELLIDO1"),
                            rs.getString("APELLIDO2"),
                            rs.getString("EXPERIENCIA"),
                            rs.getString("EMAIL"),
                    },
                    id
            );

            // Obtener las especializaciones del mecánico
            String sqlEspecializaciones = """
            SELECT NOMBRE
            FROM ESPECIALIZACION
            WHERE MECANICO_ID = ?
        """;

            List<TipoEspecializacion> especializaciones = jdbcTemplate.query(
                    sqlEspecializaciones,
                    (rs, rowNum) -> TipoEspecializacion.valueOf(rs.getString("NOMBRE").toUpperCase()),
                    id
            );

            // Construir el record con todos los datos
            return new ObtenerMecanicoDTO(
                    (String) mecanicoBase[0],
                    (String) mecanicoBase[1],
                    (String) mecanicoBase[2],
                    (String) mecanicoBase[3],
                    (String) mecanicoBase[4],
                    (String) mecanicoBase[5],
                    (int) mecanicoBase[6],
                    especializaciones
            );

        } catch (Exception e) {
            throw new ResourceNotFoundException("No se encontró el mecánico con ID: " + id);
        }
    }

   /** public List<ObtenerMecanicoDTO> listaMecanicos() {
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
    }*/

}
