package de.tobchen.health.questobyonnaire.rest.advices;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import de.tobchen.health.questobyonnaire.rest.exceptions.IllegalQuestionnaireStatusChangeException;

@RestControllerAdvice
public class QuestionnaireAdvice {
    @ResponseBody
    @ExceptionHandler(IllegalQuestionnaireStatusChangeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String illegalQuestionnaireStatusChangeHandler(IllegalQuestionnaireStatusChangeException e)
    {
        return e.getMessage();
    }
}
