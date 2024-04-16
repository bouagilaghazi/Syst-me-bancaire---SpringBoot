package fr.uphf.projet.Services.Exception;

public class ClientException extends RuntimeException{
    private int errorCode;
    public ClientException(String message, int codeError) {
        super(message);
        this.errorCode= codeError;
    }
    public int getErrorCode() {
        return errorCode;
    }
}
