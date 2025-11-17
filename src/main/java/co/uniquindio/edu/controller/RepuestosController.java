package co.uniquindio.edu.controller;

import co.uniquindio.edu.dto.proveedor.CrearProveedorDTO;
import co.uniquindio.edu.dto.proveedor.ObtenerProveedorDTO;
import co.uniquindio.edu.dto.repuesto.CrearRepuestoDTO;
import co.uniquindio.edu.dto.repuesto.ObtenerRepuestoDTO;
import co.uniquindio.edu.dto.response.ResponseDTO;
import co.uniquindio.edu.services.RepuestosService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/repuestos")
@RequiredArgsConstructor
public class RepuestosController {

    private final RepuestosService repuestosService;

    @PostMapping("/{idProveedor}")
    public ResponseEntity<ResponseDTO<String>> crearRepuesto(@RequestBody CrearRepuestoDTO crearRepuestoDTO, @PathVariable String idProveedor) {
        repuestosService.crearRepuesto(crearRepuestoDTO, idProveedor);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO<>(false,  "repuesto creado exitosamente" ));
    }

    @PutMapping("/{id}/proveedores/{idProveedor}")
    public ResponseEntity<ResponseDTO<String>> actualizarRepuesto(@PathVariable String id, @PathVariable String idProveedor, @RequestBody CrearRepuestoDTO crearRepuestoDTO) {
        repuestosService.actualizarRepuesto(id, crearRepuestoDTO, idProveedor );
        return  ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO<>(false,  "repuesto actualizado exitosamente" ));
    }

    // lista de todos los repuestos
    @GetMapping()
    public ResponseEntity<ResponseDTO<List<ObtenerRepuestoDTO>>> listaRepuestos() {
        List<ObtenerRepuestoDTO> repuestos = repuestosService.listaRepuestos();
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO<>(false, repuestos));
    }

    // obtener repuesto por id
    @GetMapping("/{id}")
    public ResponseEntity<ResponseDTO<ObtenerRepuestoDTO>> obtenerRepuesto(@PathVariable String id) {
        ObtenerRepuestoDTO repuesto = repuestosService.obtenerRepuesto(id);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO<>(false, repuesto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDTO<String>> eliminarRepuesto(@PathVariable String id) {
        repuestosService.eliminarRepuesto(id);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO<>(false,  "repuesto eliminado"));
    }

}
