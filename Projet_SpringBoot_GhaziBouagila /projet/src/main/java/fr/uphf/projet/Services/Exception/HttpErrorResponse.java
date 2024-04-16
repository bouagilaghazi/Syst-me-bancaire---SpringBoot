package fr.uphf.projet.Services.Exception;

public class HttpErrorResponse {
    private String message;
    private String errorCode;

    public HttpErrorResponse(String message, String errorCode) {
        this.message = message;
        this.errorCode = errorCode;
    }

    // Getters et setters

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }
}
