package test.model.entities;

import dao.emprestimo.EmprestimoDAOList;
import dao.leitor.LeitorDAOList;
import dao.livro.LivroDAOList;
import model.entities.Emprestimo;
import model.entities.Leitor;
import model.entities.Livro;
import model.entities.enums.StatusConta;
import org.junit.jupiter.api.Test;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import static org.junit.jupiter.api.Assertions.*;

class EmprestimoTest {

    @Test
    public void TestarSeOUsuarioExiste_EsseTesteDevePassar() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        LeitorDAOList listleitor = new LeitorDAOList();
        Leitor leitorteste = new Leitor("Diogo", "Salvasdor", "71996244389", "diogo", "123");
        listleitor.create(leitorteste);

        assertInstanceOf(Leitor.class, listleitor.findByPk(leitorteste.getId())); // Nao deve falhar

        //Emprestimo emprestimo = new Emprestimo(sdf.parse("20/11/2023"), sdf.parse("29/11/2023"), leitorteste.getId(), "30");
        //listemprestimo.create(emprestimo);

    }

    @Test
    public void TestarSeOLivroExiste() {
        LivroDAOList listlivro = new LivroDAOList();

        Livro livro = new Livro(0, "Diogo", "Taoke", "Ple", "Terror");
        listlivro.create(livro);

        assertInstanceOf(Livro.class, listlivro.findByPk(livro.getId()));

    }

    @Test
    public void TestarSeOUsuarioEstaBloqueado() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        LeitorDAOList listleitor = new LeitorDAOList();
        Leitor leitorteste = new Leitor("Diogo", "Salvasdor", "71996244389", "diogo", "123");
        listleitor.create(leitorteste);

        assertNotEquals(StatusConta.BLOQUEADA, leitorteste.getStatusDaConta());

    }

    @Test
    public void TestarSeOUsuarioNaoUltrapassouOLimiteDeEmprestimos() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        LeitorDAOList listleitor = new LeitorDAOList();
        Leitor leitorteste = new Leitor("Diogo", "Salvasdor", "71996244389", "diogo", "123");
        listleitor.create(leitorteste);

        assertTrue(leitorteste.getNumeroDeEmprestimos() > 0);
    }


    



    @Test
    public void TestarSeOUsuarioNaoExiste() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        EmprestimoDAOList listemprestimo = new EmprestimoDAOList();

        LeitorDAOList listleitor = new LeitorDAOList();
        Leitor leitorteste = new Leitor("Diogo", "Salvasdor", "71996244389", "diogo", "123");
        listleitor.create(leitorteste);

        assertInstanceOf(Leitor.class, listleitor.findByPk(-1)); // Tem que falhar

    }

}