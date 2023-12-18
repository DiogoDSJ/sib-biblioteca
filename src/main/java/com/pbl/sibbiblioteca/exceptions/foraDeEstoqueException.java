package com.pbl.sibbiblioteca.exceptions;

/**
 * Exception para coisas fora do seu estoque comum.
 */
public class foraDeEstoqueException extends Exception {
    /**
     * Algo está fora do seu estoque normal.
     * @param message Mensagem lançada
     */
    public foraDeEstoqueException(String message) {
        super(message);
    }
}
