package test.model.entities;

import dao.emprestimo.EmprestimoDAOList;
import dao.leitor.LeitorDAOList;
import model.entities.Leitor;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import static org.junit.jupiter.api.Assertions.*;

class LeitorTest {

    @Test
    public void TestarSeODaoEstaGerandoeBuscandoOsIdsCorretamente() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        EmprestimoDAOList listemprestimo = new EmprestimoDAOList();

        LeitorDAOList listleitor = new LeitorDAOList();
        Leitor leitorteste = new Leitor("Diogo", "Salvasdor", "71996244389", "diogo", "123");
        listleitor.create(leitorteste);

        assertEquals(leitorteste, listleitor.findByPk(leitorteste.getId()));

    }
}