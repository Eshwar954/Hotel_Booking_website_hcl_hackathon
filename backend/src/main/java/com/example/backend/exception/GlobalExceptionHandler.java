package com.example.backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.example.backend.dto.ApiResponseDto;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponseDto> handleNotFound(
            ResourceNotFoundException ex) {

        ApiResponseDto response = new ApiResponseDto();
        response.setMessage(ex.getMessage());

        return new ResponseEntity<>(
                response,
                HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ApiResponseDto> handleUnauthorized(
            UnauthorizedException ex) {

        ApiResponseDto response = new ApiResponseDto();
        response.setMessage(ex.getMessage());

        return new ResponseEntity<>(
                response,
                HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponseDto> handleValidation(
            MethodArgumentNotValidException ex) {

        String errorMessage = "Validation failed";
        if (ex.getBindingResult().getFieldError() != null) {
            errorMessage = ex.getBindingResult()
                    .getFieldError()
                    .getDefaultMessage();
        }

        ApiResponseDto response = new ApiResponseDto();
        response.setMessage(errorMessage);

        return new ResponseEntity<>(
                response,
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponseDto> handleOther(
            Exception ex) {

        ApiResponseDto response = new ApiResponseDto();
        response.setMessage("Something went wrong");

        return new ResponseEntity<>(
                response,
                HttpStatus.INTERNAL_SERVER_ERROR);
    }
}