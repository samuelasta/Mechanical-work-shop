package co.uniquindio.edu.controller;

import co.uniquindio.edu.dto.orden.ActualizarOrdenDTO;
import co.uniquindio.edu.dto.orden.CrearOrdenDTO;
import co.uniquindio.edu.dto.orden.ObtenerOrdenDTO;
import co.uniquindio.edu.dto.response.ResponseDTO;
import co.uniquindio.edu.model.Orden;
import co.uniquindio.edu.services.OrdenesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ordenes")
@RequiredArgsConstructor
public class OrdenesController {

    private final OrdenesService ordenesService;

    @PostMapping
    public ResponseEntity<ResponseDTO<String>> crearOrden(@RequestBody CrearOrdenDTO crearOrdenDTO) {
        ordenesService.crearOrden(crearOrdenDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseDTO<>(false, "orden creada exitosamente"));
    }

    @DeleteMapping("/{idOrden}")
    public ResponseEntity<ResponseDTO<String>> eliminarOrden(@PathVariable String idOrden ){
        ordenesService.eliminarOrden(idOrden);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO<>(false, "orden eliminada exitosamente"));
    }

    @PutMapping("/{idOrden}")
    public ResponseEntity<ResponseDTO<String>> actualizarOrden(@PathVariable String idOrden, @RequestBody ActualizarOrdenDTO actualizarOrdenDTO) {
        ordenesService.actualizarOrden(idOrden, actualizarOrdenDTO);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO<>(false, "orden actualizada exitosamente"));
    }

    @GetMapping("/{idOrden}")
    public ResponseEntity<ResponseDTO<ObtenerOrdenDTO>> obtenerOrden(@PathVariable String idOrden) {
        ObtenerOrdenDTO orden = ordenesService.obtenerOrden(idOrden);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO<>(false, orden));
    }

    @GetMapping()
    public ResponseEntity<ResponseDTO<List<ObtenerOrdenDTO>>> obtenerOrdenes() {
        List<ObtenerOrdenDTO> list = ordenesService.listaOrdenes();
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO<>(false, list));
    }
}
