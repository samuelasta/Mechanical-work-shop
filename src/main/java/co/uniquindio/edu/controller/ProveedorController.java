package co.uniquindio.edu.controller;

import co.uniquindio.edu.dto.mecanico.ObtenerMecanicoDTO;
import co.uniquindio.edu.dto.proveedor.CrearProveedorDTO;
import co.uniquindio.edu.dto.proveedor.ObtenerProveedorDTO;
import co.uniquindio.edu.dto.response.ResponseDTO;
import co.uniquindio.edu.services.ProvedoresService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/proveedores")
@RequiredArgsConstructor
public class ProveedorController {

    private final ProvedoresService provedoresService;

    @PostMapping()
    public ResponseEntity<ResponseDTO<String>> crearProveedor(@RequestBody CrearProveedorDTO crearProveedorDTO) {
        provedoresService.crearProvedor(crearProveedorDTO);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO<>(false,  "proveedor creado exitosamente" ));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseDTO<String>> actualizarProvedor(@PathVariable String id, @RequestBody CrearProveedorDTO crearProveedorDTO) {
        provedoresService.actualizarProveedor(id, crearProveedorDTO);
        return  ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO<>(false,  "proveedor actualizado exitosamente" ));
    }

    // lista de todos los proveedores de repuestos
    @GetMapping()
    public ResponseEntity<ResponseDTO<List<ObtenerProveedorDTO>>> listaProvedores() {
        List<ObtenerProveedorDTO> provedores = provedoresService.listaProvedores();
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO<>(false, provedores));
    }

    // obtenerlo proveedor por id
    @GetMapping("/{id}")
    public ResponseEntity<ResponseDTO<ObtenerProveedorDTO>> obtenerProveedor(@PathVariable String id) {
        ObtenerProveedorDTO provedor = provedoresService.obtenerProveedor(id);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO<>(false, provedor));
    }




}
