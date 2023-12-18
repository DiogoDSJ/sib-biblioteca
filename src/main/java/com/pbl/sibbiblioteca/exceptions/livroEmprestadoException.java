package com.pbl.sibbiblioteca.exceptions;

/**
 * Exception lançada quando um livro que está emprestado sofre uma tentativa de remoção ou já está
 * emprestado para outro usuário
 */
public class livroEmprestadoException extends Exception{
    /**
     * Exception lançada quando um livro que está emprestado sofre uma tentativa de remoção ou já está
     * emprestado para outro usuário
     * @param message Mensagem lançada.
     */
    public livroEmprestadoException(String message) {
        super(message);
    }
}
