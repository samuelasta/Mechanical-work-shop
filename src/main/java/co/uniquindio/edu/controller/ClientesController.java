package co.uniquindio.edu.controller;

import co.uniquindio.edu.dto.cliente.CrearClienteDTO;
import co.uniquindio.edu.dto.cliente.ObtenerClienteDTO;
import co.uniquindio.edu.dto.orden.ObtenerOrdenDTO;
import co.uniquindio.edu.dto.response.ResponseDTO;
import co.uniquindio.edu.dto.servicio.ObtenerServicioDTO;
import co.uniquindio.edu.services.ClientesService;
import co.uniquindio.edu.services.OrdenesService;
import co.uniquindio.edu.services.ServiciosService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clientes")
@RequiredArgsConstructor
public class ClientesController {

    private final ClientesService clienteService;
    private final OrdenesService ordenesService;
    private final ServiciosService serviciosService;

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

    // listar todas las ordenes del cliente
    @GetMapping("/{id}/ordenes")
    public ResponseEntity<ResponseDTO<List<ObtenerOrdenDTO>>> listaOrdenesPorCliente(@PathVariable String id){
        List<ObtenerOrdenDTO> ordenes = ordenesService.listaOrdenesPorCliente(id);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO<>(false,  ordenes));
    }

    // obtener lista de servicios  y repuestos que le han hecho a su vehiculo (por seguridad no se puede mostrar el mecanico y debe ser el dueño)
    @GetMapping("/{idCliente}/vehiculos/{placaVehiculo}/servicios-repuestos")
    public ResponseEntity<ResponseDTO<List<ObtenerServicioDTO>>> listaServiciosPorVehiculo(@PathVariable String idCliente, @PathVariable String placaVehiculo){
        List<ObtenerServicioDTO> servicios = serviciosService.listaServiciosPorVehiculo(idCliente, placaVehiculo);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO<>(false,  servicios));
    }

}
