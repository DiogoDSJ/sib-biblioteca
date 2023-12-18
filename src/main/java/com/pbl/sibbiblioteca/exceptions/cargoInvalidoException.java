package com.pbl.sibbiblioteca.exceptions;

/**
 * Exceção lançada quando cargo é invalido.
 */
public class cargoInvalidoException extends Exception{
    /**
     * O cargo é invalido.
     * @param message mensagem lançada
     */
    public cargoInvalidoException(String message) {
        super(message);
    }
}
