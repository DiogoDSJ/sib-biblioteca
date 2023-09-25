package model.entities;

import java.time.LocalDate;

public class Multa {

    private LocalDate dataIncio;
    private LocalDate dataFim;
    private String idMulta;
    private String idUsuario;

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
