package co.uniquindio.edu.controller;

import co.uniquindio.edu.dto.dashboard.DashBoardDTO;
import co.uniquindio.edu.dto.response.ResponseDTO;
import co.uniquindio.edu.services.ReportesService;
import co.uniquindio.edu.services.ServiciosService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/dashboard")
public class DashBoardController {

    private final ReportesService reportesService;

    public DashBoardController(ReportesService reportesService) {
        this.reportesService = reportesService;
    }

    @GetMapping()
    public ResponseEntity<ResponseDTO<DashBoardDTO>> obtenerDatos(){

        DashBoardDTO dashBoardDTO = reportesService.obtenerDatos();
        return ResponseEntity.status(HttpStatus.OK).body(new  ResponseDTO<>(false, dashBoardDTO));
    }
}
