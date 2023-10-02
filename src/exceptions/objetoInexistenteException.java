package exceptions;

/**
 * Objeto que não exista tentar se chamado em alguma função.
 */
public class objetoInexistenteException extends Exception{
    /**
     * Objeto que não exista tentar se chamado em alguma função.
     * @param message Mensagem a ser lançada
     */
    public objetoInexistenteException(String message) {
        super(message);
    }
}
