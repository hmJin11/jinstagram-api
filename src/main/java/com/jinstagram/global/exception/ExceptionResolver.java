package com.jinstagram.global.exception;

import com.jinstagram.global.common.Result;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.hibernate.service.spi.ServiceException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.nio.file.AccessDeniedException;

@RestControllerAdvice
@ResponseBody
public class ExceptionResolver {


    @ExceptionHandler(DataIntegrityViolationException.class)
    public Result defaultErrorHandler(HttpServletRequest request, HttpServletResponse response, DataIntegrityViolationException e) {
        e.printStackTrace();
        return new Result(String.valueOf(HttpStatus.BAD_REQUEST.value()),"duplicated",e);
    }
    @ExceptionHandler(ServiceException.class)
    public Result defaultErrorHandler(HttpServletRequest request, HttpServletResponse response, ServiceException e) {
        e.printStackTrace();
        return new Result(String.valueOf(HttpStatus.BAD_REQUEST.value()),e.getMessage(),"");
    }
    @ExceptionHandler(IllegalArgumentException.class)
    public Result defaultErrorHandler(HttpServletRequest request, HttpServletResponse response, IllegalArgumentException e) {
        e.printStackTrace();
        return new Result(String.valueOf(HttpStatus.BAD_REQUEST.value()),e.getMessage(),"");
    }
    @ExceptionHandler(AccessDeniedException.class)
    public Result defaultErrorHandler(HttpServletRequest request, HttpServletResponse response, AccessDeniedException e) {
        e.printStackTrace();
        return new Result(String.valueOf(HttpStatus.UNAUTHORIZED.value()),e.getMessage(),"");
    }
    @ExceptionHandler(Exception.class)
    public Result defaultErrorHandler(HttpServletRequest request, HttpServletResponse response, Exception e) {
        e.printStackTrace();
        return new Result(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()),e.getMessage(),"");
    }
}
