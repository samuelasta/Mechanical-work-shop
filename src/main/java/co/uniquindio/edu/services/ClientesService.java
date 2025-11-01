package co.uniquindio.edu.services;

import co.uniquindio.edu.dto.cliente.CrearClienteDTO;
import co.uniquindio.edu.dto.cliente.ObtenerClienteDTO;
import co.uniquindio.edu.dto.factura.ObtenerFacturaDTO;

import java.util.List;

public interface ClientesService {

    void crearCliente(CrearClienteDTO CrearClienteDTO);
    void eliminarCliente(String id);
    void actualizarCliente(String id, CrearClienteDTO CrearClienteDTO);
    ObtenerClienteDTO obtenerCliente(String id);




}
