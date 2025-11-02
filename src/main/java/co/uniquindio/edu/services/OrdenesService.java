package co.uniquindio.edu.services;

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
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface OrdenesService {

    void crearOrden(String idVehiculo, CrearOrdenDTO crearOrdenDTO);
    void eliminarOrden(String idOrden);
    void actualizarOrden(String idOrden, ActualizarOrdenDTO actualizarOrdenDTO);
    ObtenerOrdenDTO obtenerOrden(String idOrden);
    List<ObtenerOrdenDTO> listaOrdenes();
    void asignarMecanico(String idOrden, String idMecanico, RolDTO rolDTO);
    void eliminarMecanico(String idOrden, String idMecanico);
    List<ObtenerMecanicoOrdenDTO> obtenerMecanicosPorOrden(String idOrden);
    void registrarDiagnosticos(String idDiagnostico, CrearDiagnosticoDTO crearDiagnosticoDTO);
    CrearDiagnosticoDTO obtenerDiagnostico(String idDiagnostico);
    void registrarServicio(String idOrden, String idMecanico, String idServicio, DetalleServicioMecanicoDTO detalleServicioMecanicoDTO);
    void actualizarDetalleServicio(String idOrden, String idMecanico, String idServicio, DetalleServicioMecanicoDTO detalleServicioMecanicoDTO);
    List<DetalleOrdenDTO>  obtenerDetalleOrden(String idOrden);
    List<ObtenerOrdenDTO> listaOrdenesPorCliente(String idCliente);
}
