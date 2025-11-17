package co.uniquindio.edu.controller;

import co.uniquindio.edu.dto.CrearDiagnosticoDTO;
import co.uniquindio.edu.dto.mecanico.ObtenerMecanicoOrdenDTO;
import co.uniquindio.edu.dto.mecanico.RolDTO;
import co.uniquindio.edu.dto.orden.*;
import co.uniquindio.edu.dto.response.ResponseDTO;
import co.uniquindio.edu.dto.servicio.DetalleServicioMecanicoDTO;
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

    @PostMapping("/{idVehiculo}/vehiculo")
    public ResponseEntity<ResponseDTO<String>> crearOrden(@PathVariable String idVehiculo, @RequestBody CrearOrdenDTO crearOrdenDTO) {
        ordenesService.crearOrden(idVehiculo, crearOrdenDTO);
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

    //asignar un mecanico a la orden (ROL, ya sea supervisor o mecanico)
    @PatchMapping("/{idOrden}/mecanicos/{idMecanico}")
    public ResponseEntity<ResponseDTO<String>> asignarMecanico(@PathVariable String idOrden,
                                                               @PathVariable String idMecanico,
                                                               @RequestBody RolDTO rolDTO) {
        ordenesService.asignarMecanico(idOrden, idMecanico, rolDTO);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO<>(false, "mecanico asignado exitosamente"));
    }

    //eliminar un mecanico de una orden
    @DeleteMapping("/{idOrden}/mecanicos/{idMecanico}")
    public ResponseEntity<ResponseDTO<String>> eliminarMecanico(@PathVariable String idOrden, @PathVariable String idMecanico){
        ordenesService.eliminarMecanico(idOrden, idMecanico);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO<>(false, "mecanico eliminado exitosamente"));
    }

    // obtener los mecanicos de una orden
    @GetMapping("/{idOrden}/mecanicos")
    public ResponseEntity<ResponseDTO<List<ObtenerMecanicoOrdenDTO>>>  obtenerMecanicosPorOrden(@PathVariable String idOrden){
        List<ObtenerMecanicoOrdenDTO> mecanicos = ordenesService.obtenerMecanicosPorOrden(idOrden);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO<>(false , mecanicos));
    }

    // registrar el diagnostico tanto inicial como final, mismo para dar el final
    @PostMapping("/{idOrden}/diagnosticos")
    public ResponseEntity<ResponseDTO<String>> registrarDiagnosticos(@PathVariable String idOrden,
                                                                     @RequestBody CrearDiagnosticoDTO crearDiagnosticoDTO){
        ordenesService.registrarDiagnosticos(idOrden, crearDiagnosticoDTO);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO<>(false, "diagnostico registrado exitosamente"));
    }

    // obtener el diagnostico de la orden
    @GetMapping("/{idOrden}/diagnosticos")
    public ResponseEntity<ResponseDTO<CrearDiagnosticoDTO>> obtenerDiagnosticos(@PathVariable String idOrden){
        CrearDiagnosticoDTO diagnostico = ordenesService.obtenerDiagnostico(idOrden);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO<>(false, diagnostico));
    }

    // Registrar servicios (se asigna que mecanico lo hizo y en que orden fue)
    @PostMapping("/{idOrden}/mecanicos/{idMecanico}/servicios/{idServicio}")
    public ResponseEntity<ResponseDTO<String>> registrarServicios(@PathVariable String idOrden,
                                                                  @PathVariable String idMecanico,
                                                                  @PathVariable String idServicio,
                                                                  @RequestBody DetalleServicioMecanicoDTO detalleServicioMecanicoDTO){
        ordenesService.registrarServicio(idOrden, idMecanico, idServicio, detalleServicioMecanicoDTO);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO<>(false, "servicio registrado en la orden exitosamente"));

    }

    // Actualizar rol u horas del mecánico en un servicio de una orden
    @PutMapping("/{idOrden}/mecanicos/{idMecanico}/servicios/{idServicio}")
    public ResponseEntity<ResponseDTO<String>> actualizarDetalleServicio(@PathVariable String idOrden,
                                                                         @PathVariable String idMecanico,
                                                                         @PathVariable String idServicio,
                                                                         @RequestBody DetalleServicioMecanicoDTO detalleServicioMecanicoDTO) {
        ordenesService.actualizarDetalleServicio(idOrden, idMecanico, idServicio, detalleServicioMecanicoDTO);
        return ResponseEntity.ok(new ResponseDTO<>(false, "detalle del servicio actualizado correctamente"));
    }

    // obtener el detalle de la orden (servicio, mecanico, horastrabajadas, rol y fecha de asignación)
    @GetMapping("/{idOrden}/detalle")
    public ResponseEntity<ResponseDTO<List<DetalleOrdenDTO>>> obtenerDetalleOrden(@PathVariable String idOrden){
        List<DetalleOrdenDTO> detalle = ordenesService.obtenerDetalleOrden(idOrden);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO<>(false, detalle));
    }

    @PostMapping("/{idOrden}/repuestos/{idRepuesto}/servicios/{idServicio}")
    public ResponseEntity<ResponseDTO<String>> asignarRepuestoServicio(@PathVariable String idRepuesto,
                                                                                     @PathVariable String idServicio,
                                                                                     @PathVariable String idOrden,
                                                                                     @RequestBody RepuestosServicioDTO repuestosServicioDTO){
        ordenesService.asignarRepuestos(idRepuesto, idServicio, idOrden, repuestosServicioDTO);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO<>(false, "Repuestos asignados correctamente"));
    }

    @DeleteMapping("/{idOrden}/repuestos/{idRepuesto}/servicios/{idServicio}")
    public ResponseEntity<ResponseDTO<String>> eliminarRepuestoOrden(@PathVariable String idRepuesto, @PathVariable String idServicio, @PathVariable String idOrden){
        ordenesService.eliminarRepuesto(idRepuesto, idServicio, idOrden);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO<>(false, "Repuesto eliminado correctamente"));
    }

    @GetMapping("/{idOrden}/servicios/{idServicio}")
    public ResponseEntity<ResponseDTO<List<ObtenerRepuestoOrdenDTO>>>  obtenerRepuestos(@PathVariable String idOrden, @PathVariable String idServicio){
        List<ObtenerRepuestoOrdenDTO> repuestos = ordenesService.obtenerRepuestos(idOrden, idServicio);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO<>(false, repuestos));
    }

    @PatchMapping("/{idOrden}/repuestos/{idRepuesto}/servicios/{idServicio}")
    public ResponseEntity<ResponseDTO<String>> actualizarRepuesto(@PathVariable String idRepuesto, @PathVariable String idServicio, @PathVariable String idOrden, @RequestBody RepuestosServicioDTO repuestosServicioDTO) {
        ordenesService.actualizarRepuestoOrdenServicio(idRepuesto, idServicio, idOrden, repuestosServicioDTO);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO<>(false, "Repuesto actualizado correctamente"));
    }




}
