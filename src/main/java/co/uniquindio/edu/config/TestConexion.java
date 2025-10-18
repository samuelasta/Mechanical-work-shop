package co.uniquindio.edu.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class TestConexion {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void probarConexion() {
        try {
            // 1️⃣ Prueba básica
            String resultado = jdbcTemplate.queryForObject(
                    "SELECT 'Conexión exitosa con Oracle' FROM dual",
                    String.class
            );
            System.out.println(resultado);

            // 2️⃣ Prueba con tus tablas reales
            Integer totalClientes = jdbcTemplate.queryForObject(
                    "SELECT COUNT(*) FROM CLIENTES",
                    Integer.class
            );
            System.out.println("Clientes registrados: " + totalClientes);

        } catch (Exception e) {
            System.err.println("⚠️ Error al probar conexión o consulta:");
            e.printStackTrace();
        }
    }
}
