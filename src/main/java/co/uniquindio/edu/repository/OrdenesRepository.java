package co.uniquindio.edu.repository;

import co.uniquindio.edu.dto.CrearDiagnosticoDTO;
import co.uniquindio.edu.dto.mecanico.ObtenerMecanicoOrdenDTO;
import co.uniquindio.edu.dto.mecanico.RolDTO;
import co.uniquindio.edu.dto.orden.ActualizarOrdenDTO;
import co.uniquindio.edu.dto.orden.CrearOrdenDTO;
import co.uniquindio.edu.dto.orden.DetalleOrdenDTO;
import co.uniquindio.edu.dto.orden.ObtenerOrdenDTO;
import co.uniquindio.edu.dto.servicio.DetalleServicioMecanicoDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class OrdenesRepository {

    private final JdbcTemplate jdbcTemplate;



    public void crearOrden(CrearOrdenDTO crearOrdenDTO) {

    }


    public void eliminarOrden(String id) {

    }


    public void actualizarOrden(String id, ActualizarOrdenDTO actualizarOrdenDTO) {

    }


    public ObtenerOrdenDTO obtenerOrden(String idOrden) {
        return null;
    }


    public List<ObtenerOrdenDTO> listaOrdenes() {
        return List.of();
    }


    public void asignarMecanico(String idOrden, String idMecanico, RolDTO rolDTO) {

    }


    public void eliminarMecanico(String idOrden, String idMecanico) {

    }


    public List<ObtenerMecanicoOrdenDTO> obtenerMecanicosPorOrden(String idOrden) {
        return List.of();
    }


    public void registrarDiagnosticos(String idDiagnostico, CrearDiagnosticoDTO crearDiagnosticoDTO) {

    }


    public CrearDiagnosticoDTO obtenerDiagnostico(String idDiagnostico) {
        return null;
    }

    // (agregar en la relación ternaria)

    public void registrarServicio(String idOrden, String idMecanico, String idServicio, DetalleServicioMecanicoDTO detalleServicioMecanicoDTO) {

    }

    // editar los datos de esa relación ternaria

    public void actualizarDetalleServicio(String idOrden, String idMecanico, String idServicio, DetalleServicioMecanicoDTO detalleServicioMecanicoDTO) {

    }

    // devueve la lista de todos los servicios(el mecanico que los hizo, rol, horas trabajadas y fechaAsignacion)

    public List<DetalleOrdenDTO> obtenerDetalleOrden(String idOrden) {
        return List.of();
    }


    public List<ObtenerOrdenDTO> listaOrdenesPorCliente(String idCliente) {
        return List.of();
    }
}
