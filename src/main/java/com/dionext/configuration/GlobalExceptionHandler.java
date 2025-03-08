package com.dionext.configuration;


import com.dionext.hiki.services.HikingLandPageCreatorService;
import com.dionext.utils.exceptions.BaseExceptionHandler;
import com.dionext.utils.exceptions.ResourceFindException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler extends BaseExceptionHandler
{
    private HikingLandPageCreatorService hikingLandPageElementService;

    @Autowired
    public void setHikingLandPageElementService(HikingLandPageCreatorService hikingLandPageElementService) {
        this.hikingLandPageElementService = hikingLandPageElementService;
    }

    @ExceptionHandler(value = {ResourceFindException.class, NoHandlerFoundException.class, NoResourceFoundException.class})
    public final ResponseEntity<Object> handleNotFoundExceptions(Exception ex, HttpServletRequest request) {
        return processException(ex, request, HttpStatus.NOT_FOUND, hikingLandPageElementService);
    }
    @ExceptionHandler()
    public final ResponseEntity<Object> handleAllExceptions(Exception ex, HttpServletRequest request) {
        return processException(ex, request, HttpStatus.INTERNAL_SERVER_ERROR, hikingLandPageElementService);
    }


}
