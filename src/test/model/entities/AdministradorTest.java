package test.model.entities;

import model.entities.Administrador;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AdministradorTest {

    @Test
    void getNome_DeveRetornarVerdadeiroQuandoNomeForIgualAoSetado() {
        Administrador adm = new Administrador("Diogo", "Salvasdor", "71996244389",  "diogo", "123");
        Assertions.assertEquals("Diogo", adm.getNome());

    }
}