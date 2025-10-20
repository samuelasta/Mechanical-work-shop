package co.uniquindio.edu.controller;

import co.uniquindio.edu.dto.cliente.CrearClienteDTO;
import co.uniquindio.edu.dto.cliente.ObtenerClienteDTO;
import co.uniquindio.edu.dto.response.ResponseDTO;
import co.uniquindio.edu.exception.ValueConflictException;
import co.uniquindio.edu.services.ClienteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/clientes")
@RequiredArgsConstructor
public class ClientesController {

    private final ClienteService clienteService;

    @PostMapping
    public ResponseEntity<ResponseDTO<String>> crearCliente(@RequestBody CrearClienteDTO crearClienteDTO){
        clienteService.crearCliente(crearClienteDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body( new ResponseDTO<>(false, "Cliente Creado"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDTO<String>> eliminarCliente(@PathVariable String id){
        clienteService.eliminarCliente(id);
        return ResponseEntity.status(HttpStatus.OK).body( new ResponseDTO<>(false, "Cliente Eliminado"));
    }

    @PutMapping
    public ResponseEntity<ResponseDTO<String>> actualizarCliente(@RequestBody CrearClienteDTO crearClienteDTO){
        clienteService.actualizarCliente(crearClienteDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body( new ResponseDTO<>(false, "Cliente Creado"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDTO<ObtenerClienteDTO>> obtenerCliente(@PathVariable String id){
        ObtenerClienteDTO cliente = clienteService.obtenerCliente(id);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO<>(false, cliente));
    }

}
