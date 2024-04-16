package fr.uphf.projet.Services.Exception;

public class NoContentException extends ClientException{
    public NoContentException(String message, int codeError) {
        super(message,204);
    }
}
