package co.uniquindio.edu.controller;

import co.uniquindio.edu.dto.repuesto.CrearRepuestoDTO;
import co.uniquindio.edu.dto.repuesto.ObtenerRepuestoDTO;
import co.uniquindio.edu.dto.response.ResponseDTO;
import co.uniquindio.edu.dto.servicio.CrearServicioDTO;
import co.uniquindio.edu.dto.servicio.ObtenerServicioDTO;
import co.uniquindio.edu.services.ServiciosService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/servicios")
@RequiredArgsConstructor
public class ServiciosController {

    private final ServiciosService serviciosService;

    @PostMapping()
    public ResponseEntity<ResponseDTO<String>> crearServicio(@RequestBody CrearServicioDTO crearServicioDTO) {
        serviciosService.crearServicio(crearServicioDTO);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO<>(false,  "servicio creado exitosamente" ));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseDTO<String>> actualizarServicio(@PathVariable String id, @RequestBody CrearServicioDTO crearServicioDTO) {
        serviciosService.actualizarServicio(id, crearServicioDTO);
        return  ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO<>(false,  "servicio actualizado exitosamente" ));
    }

    // lista de todos los repuestos
    @GetMapping()
    public ResponseEntity<ResponseDTO<List<ObtenerServicioDTO>>> listaServicios() {
        List<ObtenerServicioDTO> servicios = serviciosService.listaServicios();
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO<>(false, servicios));
    }

    // obtener servicio por id
    @GetMapping("/{id}")
    public ResponseEntity<ResponseDTO<ObtenerServicioDTO>> obtenerServicio(@PathVariable String id) {
        ObtenerServicioDTO servicio = serviciosService.obtenerServicio(id);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO<>(false, servicio));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDTO<String>> eliminarServicio(@PathVariable String id) {
        serviciosService.eliminarServicio(id);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO<>(false,"servicio eliminado"));
    }

}
