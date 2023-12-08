package main.java.com.game.models.exceptions;

public class EmptyNameException extends Exception {
    public EmptyNameException() {
        super("Error: The 'name' field cannot be empty.");
    }
}