package br.com.exp7orer.centauri.exceptions;

public class MensagemException extends RuntimeException{

    public MensagemException() {
        super();
    }

    public MensagemException(String message) {
        super(message);
    }

    public MensagemException(String message, Throwable cause) {
        super(message, cause);
    }
}
