package co.uniquindio.edu.dto.response;

public record ResponseDTO<T>(Boolean error, T mensaje) {
}
