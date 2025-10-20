package co.uniquindio.edu.controller;

import co.uniquindio.edu.dto.mecanico.CrearMecanicoDTO;
import co.uniquindio.edu.dto.response.ResponseDTO;
import co.uniquindio.edu.services.MecanicoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/mecanicos")
@RequiredArgsConstructor
public class MecanicoController {

    private final MecanicoService mecanicoService;

    @PostMapping()
    public ResponseEntity<ResponseDTO<String>> crearMecanico(@RequestBody CrearMecanicoDTO crearMecanicoDTO) {
        mecanicoService.crearMecanico(crearMecanicoDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseDTO<>(false, "mecanico creado exitosamente"));
    }


}
