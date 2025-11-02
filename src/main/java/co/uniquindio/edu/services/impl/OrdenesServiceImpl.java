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
import co.uniquindio.edu.repository.OrdenesRepository;
import co.uniquindio.edu.services.OrdenesService;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class OrdenesServiceImpl implements OrdenesService {

    private final OrdenesRepository ordenesRepository;

    @Override
    public void crearOrden(String idVehiculo, CrearOrdenDTO crearOrdenDTO) {

        ordenesRepository.crearOrden(idVehiculo, crearOrdenDTO);

    }

    @Override
    public void eliminarOrden(String id) {
        ordenesRepository.eliminarOrden(id);
    }

    @Override
    public void actualizarOrden(String id, ActualizarOrdenDTO actualizarOrdenDTO) {
        ordenesRepository.actualizarOrden(id, actualizarOrdenDTO);
    }

    @Override
    public ObtenerOrdenDTO obtenerOrden(String idOrden) {
        return ordenesRepository.obtenerOrden(idOrden);
    }

    @Override
    public List<ObtenerOrdenDTO> listaOrdenes() {
        return ordenesRepository.obtenerTodasLasOrdenes();
    }

    @Override
    public void asignarMecanico(String idOrden, String idMecanico, RolDTO rolDTO) {
        ordenesRepository.asignarMecanico(idOrden, idMecanico, rolDTO);
    }

    @Override
    public void eliminarMecanico(String idOrden, String idMecanico) {
        ordenesRepository.eliminarMecanico(idOrden, idMecanico);
    }

    @Override
    public List<ObtenerMecanicoOrdenDTO> obtenerMecanicosPorOrden(String idOrden) {
        return ordenesRepository.obtenerMecanicosPorOrden(idOrden);
    }

    @Override
    public void registrarDiagnosticos(String idDiagnostico, CrearDiagnosticoDTO crearDiagnosticoDTO) {
        ordenesRepository.registrarDiagnosticos(idDiagnostico, crearDiagnosticoDTO);
    }

    @Override
    public CrearDiagnosticoDTO obtenerDiagnostico(String idDiagnostico) {
        return ordenesRepository.obtenerDiagnostico(idDiagnostico);
    }

    // (agregar en la relación ternaria)
    @Override
    public void registrarServicio(String idOrden, String idMecanico, String idServicio, DetalleServicioMecanicoDTO detalleServicioMecanicoDTO) {
            ordenesRepository.registrarServicio(idOrden, idMecanico, idServicio, detalleServicioMecanicoDTO);
    }

    // editar los datos de esa relación ternaria
    @Override
    public void actualizarDetalleServicio(String idOrden, String idMecanico, String idServicio, DetalleServicioMecanicoDTO detalleServicioMecanicoDTO) {
            ordenesRepository.actualizarDetalleServicio(idOrden, idMecanico, idServicio, detalleServicioMecanicoDTO);
    }

    // devueve la lista de todos los servicios(el mecanico que los hizo, rol, horas trabajadas y fechaAsignacion)
    @Override
    public List<DetalleOrdenDTO> obtenerDetalleOrden(String idOrden) {
        return ordenesRepository.obtenerDetalleOrden(idOrden);
    }

    @Override
    public List<ObtenerOrdenDTO> listaOrdenesPorCliente(String idCliente) {
        return ordenesRepository.listaOrdenesPorCliente(idCliente);
    }
}
