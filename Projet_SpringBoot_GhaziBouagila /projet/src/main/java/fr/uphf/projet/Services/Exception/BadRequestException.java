package fr.uphf.projet.Services.Exception;

public class BadRequestException extends ClientException{
    public BadRequestException(String message, int codeError) {
        super(message, codeError);

    }

}
