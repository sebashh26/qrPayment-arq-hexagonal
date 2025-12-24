package com.mitocode.qrpayment.infraestructure.in.web.exception;

import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;


/**- @Provider: Es una anotación de JAX-RS que registra la clase como un componente que puede ser usado por el framework.
- En este caso, indica que GlobalExceptionMapper es un manejador de excepciones que debe ser reconocido por el contenedor (como Jersey, RESTEasy, etc.).
- ExceptionMapper<T> es una interfaz de JAX-RS que permite interceptar excepciones lanzadas durante el procesamiento de una solicitud HTTP.
- Al implementar ExceptionMapper<Throwable>, esta clase captura cualquier excepción no manejada (porque Throwable es la superclase de todas las excepciones y errores en Java).
- Esto permite centralizar el manejo de errores y devolver respuestas HTTP personalizadas.
- Al migrar se retiro esto
 */
@RestControllerAdvice
public class GlobalExceptionMapper {

	@ExceptionHandler({IllegalArgumentException.class, IllegalStateException.class})
    public ResponseEntity<Map<String, Object>> handleIllegalExceptions(RuntimeException ex, HttpServletRequest request) {
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("timestamp", ZonedDateTime.now());
        response.put("status", HttpStatus.BAD_REQUEST.value());
        response.put("error", "Bad Request");
        response.put("message", ex.getMessage());
        response.put("path", request.getRequestURI());

        return ResponseEntity.badRequest().body(response);
    }

//    @ExceptionHandler(InvalidRequestException.class)
//    public ResponseEntity<ErrorResponse> handleInvalidRequestException( RuntimeException ex, HttpServletRequest request) {
//        ErrorResponse errorResponse = new ErrorResponse
//                (
//                        "01",
//                        "Datos inválidos",
//                        ex.getMessage()
//                );
//
//        return ResponseEntity.badRequest().body(errorResponse);
//    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationErrors(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String message = error.getDefaultMessage();
            errors.put(fieldName, message);
        });

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Map<String, String>> handleConstraintViolations(ConstraintViolationException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getConstraintViolations().forEach(cv -> {
            String path = cv.getPropertyPath().toString();
            String field = path.substring(path.lastIndexOf('.') + 1);
            errors.put(field, cv.getMessage());
        });
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

}
