package br.com.guilhermesegatto.gestao_vagas.exceptions;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;
import java.util.ArrayList;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.http.HttpStatus;

@ControllerAdvice
public class ExceptionHandlerController {
    
    private MessageSource messageSource;

    public ExceptionHandlerController(MessageSource messageSource) {
        this.messageSource = messageSource;
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<ErrorMessageDTO>> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
       List<ErrorMessageDTO> dto = new ArrayList<>();
       
        e.getBindingResult().getFieldErrors().forEach(error -> {
           String message = messageSource.getMessage(error, LocaleContextHolder.getLocale());
           ErrorMessageDTO errorMessage = new ErrorMessageDTO(error.getField(), message);
           dto.add(errorMessage);
       });

       return new ResponseEntity<>(dto, HttpStatus.BAD_REQUEST);
    }
}
