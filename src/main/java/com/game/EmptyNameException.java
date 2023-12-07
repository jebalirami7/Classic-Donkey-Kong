package main.java.com.game;

public class EmptyNameException extends Exception {
    public EmptyNameException() {
        super("Error: The 'name' field cannot be empty.");
    }
}