package com.pbl.sibbiblioteca.view;

import com.pbl.sibbiblioteca.exceptions.foraDeEstoqueException;
import com.pbl.sibbiblioteca.model.entities.Sistema;
import com.pbl.sibbiblioteca.utils.TelaController;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

public class SibApplication extends Application {

    public static void main(String[] args) {
        launch(args);
    }


    @Override
    public void start(Stage program) throws IOException, foraDeEstoqueException {
        /* Caso queira testar um empréstimo vencido ou reserva vencido, siga essas instruções, crie o empréstimo usando
        uma conta de bibliotecário/administrador e após isso, logue na conta do Leitor, olhe o id do empréstimo/reserva
        e substitua embaixo. Lembrando que as reservas vencidas são apagadas ao início do programa. */
        //DAO.getEmprestimoDAO().findByPk("IdEmprestimo").setDataFim(LocalDate.now().minusDays(int DiasParaSubtrair));
        //DAO.getReservaDAO().findByPk("idReserva").setDataFimReserva(LocalDate.now().minusDays(int DiasParaSubtrair));
        Sistema.atualizarReservas();
        Sistema.ativarReservasLivros();
        Sistema.updateMultas();
        TelaController.StageBuilder(program, TelaController.StageFXMLLoader("TelaDeInicio.fxml"));
        program.show();
    }
}
