package co.uniquindio.edu.services.impl;

import co.uniquindio.edu.dto.CrearDiagnosticoDTO;
import co.uniquindio.edu.dto.mecanico.ObtenerMecanicoDTO;
import co.uniquindio.edu.dto.mecanico.ObtenerMecanicoOrdenDTO;
import co.uniquindio.edu.dto.mecanico.RolDTO;
import co.uniquindio.edu.dto.orden.ActualizarOrdenDTO;
import co.uniquindio.edu.dto.orden.CrearOrdenDTO;
import co.uniquindio.edu.dto.orden.DetalleOrdenDTO;
import co.uniquindio.edu.dto.orden.ObtenerOrdenDTO;
import co.uniquindio.edu.dto.servicio.DetalleServicioMecanicoDTO;
import co.uniquindio.edu.model.enums.Rol;
import co.uniquindio.edu.services.OrdenesService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrdenesServiceImpl implements OrdenesService {

    @Override
    public void crearOrden(String idVehiculo, CrearOrdenDTO crearOrdenDTO) {



    }

    @Override
    public void eliminarOrden(String id) {

    }

    @Override
    public void actualizarOrden(String id, ActualizarOrdenDTO actualizarOrdenDTO) {

    }

    @Override
    public ObtenerOrdenDTO obtenerOrden(String idOrden) {
        return null;
    }

    @Override
    public List<ObtenerOrdenDTO> listaOrdenes() {
        return List.of();
    }

    @Override
    public void asignarMecanico(String idOrden, String idMecanico, RolDTO rolDTO) {

    }

    @Override
    public void eliminarMecanico(String idOrden, String idMecanico) {

    }

    @Override
    public List<ObtenerMecanicoOrdenDTO> obtenerMecanicosPorOrden(String idOrden) {
        return List.of();
    }

    @Override
    public void registrarDiagnosticos(String idDiagnostico, CrearDiagnosticoDTO crearDiagnosticoDTO) {

    }

    @Override
    public CrearDiagnosticoDTO obtenerDiagnostico(String idDiagnostico) {
        return null;
    }

    // (agregar en la relación ternaria)
    @Override
    public void registrarServicio(String idOrden, String idMecanico, String idServicio, DetalleServicioMecanicoDTO detalleServicioMecanicoDTO) {

    }

    // editar los datos de esa relación ternaria
    @Override
    public void actualizarDetalleServicio(String idOrden, String idMecanico, String idServicio, DetalleServicioMecanicoDTO detalleServicioMecanicoDTO) {

    }

    // devueve la lista de todos los servicios(el mecanico que los hizo, rol, horas trabajadas y fechaAsignacion)
    @Override
    public List<DetalleOrdenDTO> obtenerDetalleOrden(String idOrden) {
        return List.of();
    }

    @Override
    public List<ObtenerOrdenDTO> listaOrdenesPorCliente(String idCliente) {
        return List.of();
    }
}
