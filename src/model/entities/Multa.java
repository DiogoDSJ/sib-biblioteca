package model.entities;

import java.time.LocalDate;

/**
 * Classe multa que será utilizada para atribuir multa ao usuários.
 * A classe contém os atributos de data de inicio da multa, data fim da multa, id da multa que será gerado pelo DAO
 * e o id do usuário.
 */
public class Multa {

    private LocalDate dataIncio;
    private LocalDate dataFim;
    private String idMulta;
    private String idUsuario;

    /**
     * Construtor da classe multa
     * @param dataIncio data inicio da multa.
     * @param dataFim data fim da multa.
     * @param idUsuario id do usuário à ser multado.
     */
    public Multa(LocalDate dataIncio, LocalDate dataFim, String idUsuario) {
        this.dataIncio = dataIncio;
        this.dataFim = dataFim;
        this.idUsuario = idUsuario;
    }

    public LocalDate getDataIncio() {
        return dataIncio;
    }

    public LocalDate getDataFim() {
        return dataFim;
    }

    public void aumentarMulta(int dias) {
        this.dataFim = this.dataFim.plusDays(dias);
    }

    public String getIdMulta() {
        return idMulta;
    }

    public void setIdMulta(String idMulta) {
        this.idMulta = idMulta;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }
}
