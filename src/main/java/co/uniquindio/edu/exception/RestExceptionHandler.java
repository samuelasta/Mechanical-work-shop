package co.uniquindio.edu.exception;

import co.uniquindio.edu.dto.response.ResponseDTO;
import co.uniquindio.edu.dto.response.ValidationDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseDTO<String>> generalExceptionHandler (Exception e){
        return ResponseEntity.internalServerError().body( new ResponseDTO<>(true, e.getMessage()) );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseDTO<List<ValidationDTO>>> validationExceptionHandler (MethodArgumentNotValidException ex ) {
        List<ValidationDTO> errors = new ArrayList<>();
        BindingResult results = ex.getBindingResult();
        for (FieldError e: results.getFieldErrors()) {
            errors.add( new ValidationDTO(e.getField(), e.getDefaultMessage()) );
        }
        return ResponseEntity.badRequest().body( new ResponseDTO<>(true, errors) );
    }

    // excepciones personalizadas

    // 400
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ResponseDTO<String>> handleBadRequestException (BadRequestException ex){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body( new ResponseDTO<>(true, ex.getMessage()) );
    }

    // 403
    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<ResponseDTO<String>> handleForbiddenException (Exception ex){
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body( new ResponseDTO<>(true, ex.getMessage()) );
    }

    // 404
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ResponseDTO<String>> handleResourceNotFoundException(Exception ex){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body( new ResponseDTO<>(true, ex.getMessage()) );
    }

    //401
    @ExceptionHandler(UnauthoricedException.class)
    public ResponseEntity<ResponseDTO<String>> handleUnauthorizedException(UnauthoricedException ex){
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body( new ResponseDTO<>(true, ex.getMessage()) );
    }

    //409
    @ExceptionHandler(ValueConflictException.class)
    public ResponseEntity<ResponseDTO<String>> handleValueConflictException(ValueConflictException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body( new ResponseDTO<>(true, ex.getMessage()) );
    }

}
