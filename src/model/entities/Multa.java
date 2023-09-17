package model.entities;

import java.util.Date;

public class Multa {

    private Date dataIncio;
    private Date dataFim;
    private String idMulta;
    private String idUsuario;

    public Multa(Date dataIncio, Date dataFim, String idMulta, String idUsuario) {
        this.dataIncio = dataIncio;
        this.dataFim = dataFim;
        this.idMulta = idMulta;
        this.idUsuario = idUsuario;
    }

    public Date getDataIncio() {
        return dataIncio;
    }

    public void setDataIncio(Date dataIncio) {
        this.dataIncio = dataIncio;
    }

    public Date getDataFim() {
        return dataFim;
    }

    public void setDataFim(Date dataFim) {
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
