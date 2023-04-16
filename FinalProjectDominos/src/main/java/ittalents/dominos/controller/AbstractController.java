package ittalents.dominos.controller;

import ittalents.dominos.model.DTOs.ErrorDTO;
import ittalents.dominos.model.entities.User;
import ittalents.dominos.model.exceptions.BadRequestException;
import ittalents.dominos.model.exceptions.NotFoundException;
import ittalents.dominos.model.exceptions.UnauthorizedException;
import ittalents.dominos.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public abstract class AbstractController {
    @Autowired
    protected UserService userService;

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDTO handleBadRequest(Exception e){
        return generateErrorDTO(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UnauthorizedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorDTO handleUnauthorized(Exception e){
        return generateErrorDTO(e.getMessage(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorDTO handleNotFound(Exception e){
        return generateErrorDTO(e.getMessage(), HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorDTO handleRest(Exception e) {
        return generateErrorDTO(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ErrorDTO generateErrorDTO(Object o, HttpStatus s) {
        return ErrorDTO.builder()
                .msg(o)
                .time(LocalDateTime.now())
                .status(s.value())
                .build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorDTO handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return generateErrorDTO(errors, HttpStatus.BAD_REQUEST);
    }

    protected boolean isAdminLoggedIn(HttpSession session) {
        if (!isUserLoggedIn(session)) {
            throw new UnauthorizedException("You have to log in first");
        }
        int userId = (int) session.getAttribute("LOGGED_ID");
        if (!userService.findLoggedUser(userId).isAdmin()) {
            return false;
        }
        return true;
    }

    protected boolean isUserLoggedIn(HttpSession session) {
        if (!isThereLoggedInUser(session)) {
            return false;
        }
        int userId = (int) session.getAttribute("LOGGED_ID");
        if (userService.findLoggedUser(userId) == null) {
            return false;
        }
        return true;
    }

    private boolean isThereLoggedInUser(HttpSession session) {
        if (session.getAttribute("LOGGED") == null) {
            return false;
        }
        return true;
    }

}

