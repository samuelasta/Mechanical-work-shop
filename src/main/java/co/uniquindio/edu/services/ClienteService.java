package co.uniquindio.edu.services;

import co.uniquindio.edu.dto.cliente.CrearClienteDTO;
import co.uniquindio.edu.dto.cliente.ObtenerClienteDTO;

public interface ClienteService {

    void crearCliente(CrearClienteDTO CrearClienteDTO);
    void eliminarCliente(String id);
    void actualizarCliente(CrearClienteDTO CrearClienteDTO);
    ObtenerClienteDTO obtenerCliente(String id);



}
