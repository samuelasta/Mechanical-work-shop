package co.uniquindio.edu.controller;

import co.uniquindio.edu.dto.mecanico.CrearMecanicoDTO;
import co.uniquindio.edu.dto.mecanico.ObtenerMecanicoDTO;
import co.uniquindio.edu.dto.response.ResponseDTO;
import co.uniquindio.edu.services.MecanicosService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/mecanicos")
@RequiredArgsConstructor
public class MecanicoController {

    private final MecanicosService mecanicoService;

    @PostMapping()
    public ResponseEntity<ResponseDTO<String>> crearMecanico(@RequestBody CrearMecanicoDTO crearMecanicoDTO) {
        mecanicoService.crearMecanico(crearMecanicoDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseDTO<>(false, "mecanico creado exitosamente"));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseDTO<String>> actualizarMecanico(@PathVariable String id, @RequestBody CrearMecanicoDTO crearMecanicoDTO) {
        mecanicoService.actualizarMecanico(crearMecanicoDTO);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO<>(false, "mecanico actualizado exitosamente"));
    }

    // devuelve la lista de todos los mecanicos del taller
    @GetMapping()
    public ResponseEntity<ResponseDTO<List<ObtenerMecanicoDTO>>> listaMecanicos() {
        List<ObtenerMecanicoDTO> mecanicos = mecanicoService.listaMecanicos();
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO<>(false, mecanicos));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDTO<ObtenerMecanicoDTO>> obtenerMecanico(@PathVariable String id) {
        ObtenerMecanicoDTO mecanico = mecanicoService.obtenerMecanico(id);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO<>(false, mecanico));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDTO<String>> eliminarMecanico(@PathVariable String id) {
        mecanicoService.eliminarMecanico(id);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO<>(false,  "mecanico eliminado exitosamente" ));
    }

}
