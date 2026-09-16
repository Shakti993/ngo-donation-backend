package com.ngo.exception;

import com.ngo.common.dto.ApiResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.MethodArgumentNotValidException;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class GlobalExceptionHandler {


    /*
     * Bean validation errors.
     *
     * Examples:
     * - missing campaignId
     * - invalid email
     * - donation amount below minimum
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Void>>handleValidationException(MethodArgumentNotValidException ex)
     {

        String errorMessage =
            ex.getBindingResult()
                .getFieldErrors()
                .get(0)
                .getDefaultMessage();


        return ResponseEntity.badRequest().body(new ApiResponse<>(
                    false,
                    errorMessage,
                    null
                )
            );
    }


    /*
     * Business rule violations.
     *
     * Examples:
     * - campaign inactive
     * - campaign not accepting donations
     */
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ApiResponse<Void>>handleBusinessException(BusinessException ex )
     {
        return ResponseEntity.badRequest().body(new ApiResponse<>(false,ex.getMessage(),null));
    }


    /*
     * Resource lookup failures.
     *
     * For now IllegalArgumentException is used
     * for missing resources.
     *
     * We can introduce a dedicated
     * ResourceNotFoundException later if needed.
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiResponse<Void>>handleIllegalArgumentException(IllegalArgumentException ex) {

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse<>(
                    false,
                    ex.getMessage(),
                    null
                )
            );
    }

}