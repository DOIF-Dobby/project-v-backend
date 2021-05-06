package org.doif.projectv.common.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

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
    public ResponseEntity<Object> handleError(MethodArgumentNotValidException e) {
        List<ObjectError> allErrors = e.getBindingResult().getAllErrors();

        return ResponseEntity.badRequest().body(allErrors);
    }
}
