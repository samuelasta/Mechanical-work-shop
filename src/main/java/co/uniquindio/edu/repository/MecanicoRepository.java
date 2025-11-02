package co.uniquindio.edu.repository;

import co.uniquindio.edu.dto.mecanico.CrearMecanicoDTO;
import co.uniquindio.edu.dto.mecanico.ObtenerMecanicoDTO;
import co.uniquindio.edu.exception.BadRequestException;
import co.uniquindio.edu.exception.ResourceNotFoundException;
import co.uniquindio.edu.model.enums.TipoEspecializacion;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
                "ACTIVO"
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
      Integer count = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM  MECANICO WHERE ID=?", Integer.class, id);

      if(count == null || count == 0){
          throw new ResourceNotFoundException("no existe este mecanico");
      }

      jdbcTemplate.update("UPDATE MECANICO SET ESTADO=? WHERE ID=?", "INACTIVO",id);

    }

    @Transactional(readOnly = true)
    public ObtenerMecanicoDTO obtenerMecanico(String id) {
        try {
            // Consulta principal del mecánico
            String sqlMecanico = """
            SELECT ID, NOMBRE1, NOMBRE2, APELLIDO1, APELLIDO2, EXPERIENCIA, EMAIL
            FROM MECANICO
            WHERE ID = ? AND ESTADO <> 'INACTIVO'
        """;

            // Mapea directamente al DTO base
            ObtenerMecanicoDTO mecanico = jdbcTemplate.queryForObject(
                    sqlMecanico,
                    (rs, rowNum) -> new ObtenerMecanicoDTO(
                            rs.getString("ID"),
                            rs.getString("NOMBRE1"),
                            rs.getString("NOMBRE2"),
                            rs.getString("APELLIDO1"),
                            rs.getString("APELLIDO2"),
                            rs.getString("EMAIL"),
                            rs.getInt("EXPERIENCIA"),
                            new ArrayList<>() // se llena más abajo
                    ),
                    id
            );

            // Consulta las especializaciones del mecánico
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

            // Asigna las especializaciones al DTO (crea una copia con la lista actualizada)
            return new ObtenerMecanicoDTO(
                    mecanico.id(),
                    mecanico.nombre1(),
                    mecanico.nombre2(),
                    mecanico.apellido1(),
                    mecanico.apellido2(),
                    mecanico.email(),
                    mecanico.experiencia(),
                    especializaciones
            );

        } catch (Exception e) {
            throw new BadRequestException("Error al consultar el mecánico: " + e.getMessage());
        }
    }


    @Transactional(readOnly = true)
    public List<ObtenerMecanicoDTO> listarMecanicos() {

        String sql = """
        SELECT ID, NOMBRE1, NOMBRE2, APELLIDO1, APELLIDO2, EXPERIENCIA, EMAIL
        FROM MECANICO
        WHERE ESTADO <> 'INACTIVO'
        ORDER BY APELLIDO1, NOMBRE1
    """;

        // Consulta base
        List<ObtenerMecanicoDTO> mecanicos = jdbcTemplate.query(sql, (rs, rowNum) -> {
            // Por cada fila, construye el DTO base (sin especializaciones aún)
            return new ObtenerMecanicoDTO(
                    rs.getString("ID"),
                    rs.getString("NOMBRE1"),
                    rs.getString("NOMBRE2"),
                    rs.getString("APELLIDO1"),
                    rs.getString("APELLIDO2"),
                    rs.getString("EMAIL"),
                    rs.getInt("EXPERIENCIA"),
                    new ArrayList<>() // luego se llena con especializaciones
            );
        });

        // Obtener especializaciones de todos los mecánicos (opcional)
        String sqlEsp = """
        SELECT MECANICO_ID, NOMBRE
        FROM ESPECIALIZACION
    """;

        Map<String, List<TipoEspecializacion>> mapaEspecializaciones = new HashMap<>();

        jdbcTemplate.query(sqlEsp, (rs) -> {
            String mecId = rs.getString("MECANICO_ID");
            String nombre = rs.getString("NOMBRE").toUpperCase();

            mapaEspecializaciones
                    .computeIfAbsent(mecId, k -> new ArrayList<>())
                    .add(TipoEspecializacion.valueOf(nombre));
        });

        // Asigna las especializaciones correspondientes
        return mecanicos.stream()
                .map(m -> new ObtenerMecanicoDTO(
                        m.id(),
                        m.nombre1(),
                        m.nombre2(),
                        m.apellido1(),
                        m.apellido2(),
                        m.email(),
                        m.experiencia(),
                        mapaEspecializaciones.getOrDefault(m.id(), List.of())
                ))
                .toList();
    }


}
