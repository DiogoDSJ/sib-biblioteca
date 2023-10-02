package exceptions;

/**
 * Exception lançada quando um livro que está reservado sofre uma tentativa de remoção ou já esta
 * reservado para outro usuário.
 */
public class livroReservadoException extends Exception{
    /**
     * Exception lançada quando um livro que está reservado sofre uma tentativa de remoção ou já esta
     * reservado para outro usuário.
     * @param message Mensagem lançada.
     */
    public livroReservadoException(String message) {
        super(message);
    }
}
