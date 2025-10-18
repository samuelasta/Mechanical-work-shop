package co.uniquindio.edu;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import co.uniquindio.edu.config.TestConexion;

@SpringBootApplication
public class MechanicalApplication implements CommandLineRunner {

    @Autowired
    private TestConexion testConexion;

	public static void main(String[] args) {
		SpringApplication.run(MechanicalApplication.class, args);
	}

    @Override
    public void run(String... args) throws Exception {
        testConexion.probarConexion();
    }
}
