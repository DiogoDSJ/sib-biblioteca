package exceptions;

/**
 * Usuario bloqueado tenta realizar alguma ação.
 */
public class usuarioBloqueadoException extends Exception{
    /**
     * Usuario bloqueado tenta realizar alguma ação.
     * @param message Mensagem a ser lançada.
     */
    public usuarioBloqueadoException(String message) {
        super(message);
    }
}
