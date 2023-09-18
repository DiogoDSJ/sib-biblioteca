package model.entities;

import java.time.LocalDate;

public class Multa {

    private LocalDate dataIncio;
    private LocalDate dataFim;
    private String idMulta;
    private String idUsuario;

    public Multa(LocalDate dataIncio, LocalDate dataFim, String idMulta, String idUsuario) {
        this.dataIncio = dataIncio;
        this.dataFim = dataFim;
        this.idMulta = idMulta;
        this.idUsuario = idUsuario;
    }

    public LocalDate getDataIncio() {
        return dataIncio;
    }

    public void setDataIncio(LocalDate dataIncio) {
        this.dataIncio = dataIncio;
    }

    public LocalDate getDataFim() {
        return dataFim;
    }

    public void setDataFim(LocalDate dataFim) {
        this.dataFim = dataFim;
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
