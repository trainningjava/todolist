package br.com.tarefa.todolist.erros;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@ControllerAdvice
public class ExceptionHandlerController {

    @ExceptionHandler(value = {MethodArgumentTypeMismatchException.class})
    public ResponseEntity<ErrorMessage> errorHandleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex) {
        ErrorMessage message = new ErrorMessage(
                HttpStatus.NOT_FOUND.value(),
                new Date(),
                ex.getName(),
                ex.getMessage());

        return new ResponseEntity<ErrorMessage>(message, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    public ResponseEntity<List<ErrorMessage>> errorHandleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        List<ErrorMessage> errorMessageList = new ArrayList<>();
        ex.getBindingResult().getFieldErrors().forEach( e -> {
                ErrorMessage message = new ErrorMessage(
                    HttpStatus.BAD_REQUEST.value(),
                    new Date(),
                    e.getField(),
                    e.getDefaultMessage());
                errorMessageList.add(message);

            }
        );
        return new ResponseEntity<>(errorMessageList, HttpStatus.BAD_REQUEST);
    }


}
