package org.doif.projectv.common.exception;

import lombok.extern.slf4j.Slf4j;
import org.doif.projectv.common.response.CommonResponse;
import org.doif.projectv.common.response.ResponseCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class CustomExceptionHandler {

//    @Override
//    protected ResponseEntity<Object> handleMethodArgumentNotValid(
//            MethodArgumentNotValidException ex, HttpHeaders headers,
//            HttpStatus status, WebRequest request) {
//
//        List<ObjectError> allErrors = ex.getBindingResult().getAllErrors();
//        for (ObjectError error : allErrors) {
//            log.error("error: {}", error);
//            // 코드 정보를 확인할 수 있다.
//            log.error("error: {}", Arrays.toString(error.getCodes()));
//        }
//        return super.handleMethodArgumentNotValid(ex, headers, status, request);
//    }
//
//    @Override
//    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
//        log.info(ex.getMessage());
//        return super.handleExceptionInternal(ex, body, headers, status, request);
//    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<CommonResponse> handleValidationError(MethodArgumentNotValidException e) {
        List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
        Map<String, String> validationMap = new HashMap<>();

        for (FieldError fieldError : fieldErrors) {
            validationMap.put(fieldError.getField(), fieldError.getDefaultMessage());
        }

        return ResponseEntity.badRequest().body(new CommonResponse(ResponseCode.VALIDATION, validationMap));
    }
}
