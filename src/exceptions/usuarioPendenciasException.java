package exceptions;

/**
 * Usuario com pendências tenta realizar alguma ação.
 */
public class usuarioPendenciasException extends Exception{
    /**
     * Usuario com pendências tenta realizar alguma ação.
     * @param message Mensagem a ser lançada.
     */
    public usuarioPendenciasException(String message) {
        super(message);
    }
}
