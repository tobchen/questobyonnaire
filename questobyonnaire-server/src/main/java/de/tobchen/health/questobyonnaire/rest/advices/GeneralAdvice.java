package de.tobchen.health.questobyonnaire.rest.advices;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import de.tobchen.health.questobyonnaire.rest.exceptions.EntityNotFoundException;
import de.tobchen.health.questobyonnaire.rest.exceptions.IllegalIdException;
import de.tobchen.health.questobyonnaire.rest.exceptions.IllegalSearchArgumentException;
import de.tobchen.health.questobyonnaire.rest.exceptions.IllegalSearchParameterException;
import de.tobchen.health.questobyonnaire.rest.exceptions.JsonSerializationException;

@RestControllerAdvice
public class GeneralAdvice {
    @ResponseBody
    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String questionnaireNotFoundHandler(EntityNotFoundException e)
    {
        return e.getMessage();
    }
    
    @ResponseBody
    @ExceptionHandler(IllegalSearchArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String illegalSearchArgumentHandler(IllegalSearchArgumentException e)
    {
        return e.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(IllegalSearchParameterException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String illegalSearchParameterHandler(IllegalSearchParameterException e)
    {
        return e.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(JsonSerializationException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String serializationExceptionHandler(JsonSerializationException e)
    {
        return e.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(IllegalIdException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String illegalIdHandler(IllegalIdException e)
    {
        return e.getMessage();
    }
}
