package exceptions;

/**
 * Quando um leitor já tem reservado ou emprestado um livro e tenta realizar as mesma uma reserva ou empréstimo do mesmo.
 */
public class objetoDuplicadoException extends Exception{
    /**
     * Quando um leitor já tem reservado ou emprestado um livro e tenta realizar as mesma uma reserva ou empréstimo do mesmo.
     * @param message mensagem a ser lançada.
     */
    public objetoDuplicadoException(String message) {
        super(message);
    }
}
