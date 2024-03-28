package de.aittr.gr26_proj_fin.exception_handlink;

public class UserAlreadyExistsException extends RuntimeException{

    public UserAlreadyExistsException(String message) {
        super(message);
    }

}
