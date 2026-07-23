package com.ngo.exception;

import com.ngo.common.dto.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

@RestControllerAdvice
public class GlobalExceptionHandler {

   @ExceptionHandler(MethodArgumentNotValidException.class)
public ResponseEntity<ApiResponse<Void>> handleValidationException(
        MethodArgumentNotValidException ex) {

    String errorMessage = ex.getBindingResult()
            .getFieldErrors()
            .get(0)
            .getDefaultMessage();

    return ResponseEntity.badRequest()
            .body(
                    new ApiResponse<>(
                            false,
                            errorMessage,
                            null
                    )
            );
}
}