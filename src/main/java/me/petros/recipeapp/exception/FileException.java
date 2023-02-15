package me.petros.recipeapp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class FileException  extends RuntimeException{
    public FileException(){super("Проблема при чтении файла");}

    public FileException(String message) {
        super(message);
    }
}
