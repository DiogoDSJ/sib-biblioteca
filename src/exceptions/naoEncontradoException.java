package exceptions;

/**
 * Quando uma função de busca não encontra o objeto.
 */
public class naoEncontradoException extends Exception{
    /**
     * Quando uma função de busca não encontra o objeto.
     * @param message Mensagem lançada.
     */
    public naoEncontradoException(String message) {
        super(message);
    }
}
