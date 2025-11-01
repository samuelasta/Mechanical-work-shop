package co.uniquindio.edu.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class RepuestosRepository {

    private final JdbcTemplate jdbcTemplate;
}
