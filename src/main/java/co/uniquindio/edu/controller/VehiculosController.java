package co.uniquindio.edu.controller;

import co.uniquindio.edu.dto.response.ResponseDTO;
import co.uniquindio.edu.dto.vehiculo.CrearVehiculoDTO;
import co.uniquindio.edu.dto.vehiculo.ObtenerVehiculoDTO;
import co.uniquindio.edu.services.VehiculosService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vehiculos")
@RequiredArgsConstructor
public class VehiculosController {

    private final VehiculosService vehiculosService;

    @PostMapping()
    public ResponseEntity<ResponseDTO<String>> crearVehiculo(@RequestBody CrearVehiculoDTO crearVehiculoDTO) {
        vehiculosService.crearVehiculo(crearVehiculoDTO);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO<>(false,  "vehiculo creado exitosamente" ));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseDTO<String>> actualizarVehiculo(@PathVariable String id, @RequestBody CrearVehiculoDTO crearVehiculoDTO) {
        vehiculosService.actualizarVehiculo(id, crearVehiculoDTO);
        return  ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO<>(false,  "repuesto actualizado exitosamente" ));
    }

    // lista de todos los vehiculos
    @GetMapping()
    public ResponseEntity<ResponseDTO<List<ObtenerVehiculoDTO>>> listaVehiculos() {
        List<ObtenerVehiculoDTO> vehiculos = vehiculosService.listaVehiculos();
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO<>(false, vehiculos));
    }

    // obtener vehiculo por id
    @GetMapping("/{id}")
    public ResponseEntity<ResponseDTO<ObtenerVehiculoDTO>> obtenerVehiculo(@PathVariable String id) {
        ObtenerVehiculoDTO vehiculo = vehiculosService.obtenerVehiculo(id);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO<>(false, vehiculo));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDTO<String>> eliminarVehiculo(@PathVariable String id) {
        vehiculosService.eliminarVehiculo(id);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO<>(false,  "vehiculo eliminado exitosamente" ));
    }

}
