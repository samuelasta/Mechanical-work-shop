package co.uniquindio.edu.model;


import java.time.LocalDateTime;
import java.util.List;

public class Cliente {

    private String id;
    private String nombre1;
    private String nombre2;
    private String apellido1;
    private String apellido2;
    private Direccion direccion;
    private String email;
    private LocalDateTime fechaRegistro;
    private List<Telefono> telefonos;

}
