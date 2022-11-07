package pyankov.springcourse.librarywebapp.controllers;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice
public class ErrorPageController {
    @ExceptionHandler(NoHandlerFoundException.class)
    public ModelAndView handle(Exception exception) {
        ModelAndView modelAndView = new ModelAndView();
        String errorPageName = exception.getMessage().substring(exception.getMessage().indexOf('/'));
        modelAndView.addObject("errorPageName", errorPageName);
        modelAndView.setViewName("/errors/error404");

        return modelAndView;
    }
}
