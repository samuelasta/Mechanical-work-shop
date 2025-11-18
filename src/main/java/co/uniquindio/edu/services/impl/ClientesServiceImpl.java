package co.uniquindio.edu.services.impl;

import co.uniquindio.edu.dto.cliente.CrearClienteDTO;
import co.uniquindio.edu.dto.cliente.LoginDTO;
import co.uniquindio.edu.dto.cliente.ObtenerClienteDTO;
import co.uniquindio.edu.dto.factura.ObtenerFacturaDTO;
import co.uniquindio.edu.repository.ClienteRepository;
import co.uniquindio.edu.services.ClientesService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@Service
public class ClientesServiceImpl implements ClientesService {

    private final ClienteRepository clienteRepository;

    @Override
    public void crearCliente(CrearClienteDTO CrearClienteDTO) {

        clienteRepository.crearCliente(CrearClienteDTO);
    }

    @Override
    public void eliminarCliente(String id) {

        clienteRepository.eliminarCliente(id);
    }

    @Override
    public void actualizarCliente(String id, CrearClienteDTO CrearClienteDTO) {

        clienteRepository.actualizarCliente(id, CrearClienteDTO);
    }

    @Override
    public ObtenerClienteDTO obtenerCliente(String id) {
        return clienteRepository.obtenerCliente(id);
    }

    @Override
    public Boolean login(LoginDTO loginDTO) {
        return Objects.equals(loginDTO.username(), "ADMIN") && Objects.equals(loginDTO.password(), "ADMIN");
    }

}
