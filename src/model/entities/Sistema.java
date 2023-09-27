package model.entities;

import dao.DAO;
import exceptions.foraDeEstoqueException;
import model.entities.Emprestimo;
import model.entities.Leitor;
import model.entities.Multa;

import java.time.LocalDate;
import java.util.List;

public class Sistema {


    public static int calcularDiasDaMulta(Emprestimo emprestimo){
        LocalDate dataAtual = LocalDate.now();
        LocalDate dataFim = emprestimo.getDataFim();
        if (dataFim.isAfter(dataAtual)){
            return dataAtual.compareTo(dataFim);
        }
        return 0;
    }


    public static void aplicarMulta(Leitor leitor){
        List<Emprestimo> emprestimoListLeitor = DAO.getEmprestimoDAO().findByIdMutuario(leitor.getId());
        int diasAtraso = 0;
        for (Emprestimo obj: emprestimoListLeitor) {
            if(checarSeHaAtrasoEmprestimo(obj)){
                diasAtraso += calcularDiasDaMulta(obj);
                DAO.getEmprestimoDAO().delete(obj);
                leitor.bloquearConta();
            }
        }
        diasAtraso *= 2;
        if(diasAtraso > 0 && DAO.getMultaDAO().findByIdMutuario(leitor.getId()) != null){
            DAO.getMultaDAO().create(new Multa(LocalDate.now(), LocalDate.now().plusDays(diasAtraso), leitor.getId()));
        }
        else if(DAO.getMultaDAO().findByIdMutuario(leitor.getId()) == null){
            DAO.getMultaDAO().findByIdMutuario(leitor.getId()).aumentarMulta(diasAtraso);
        }
    }

    //public List<Emprestimo> checarEmprestimosUsuario(Leitor leitor){}
    public static boolean checarSeHaAtrasoLeitor(Leitor leitor){
        List<Emprestimo> emprestimoList = DAO.getEmprestimoDAO().findByIdMutuario(leitor.getId());
        for (Emprestimo obj: emprestimoList) {
            if(checarSeHaAtrasoEmprestimo(obj)){
                return true;
            }
        }
        return DAO.getMultaDAO().findByIdMutuario(leitor.getId()) != null;
    }

    public static boolean checarSeHaAtrasoEmprestimo(Emprestimo emprestimo){
        return emprestimo.getDataFim().isAfter(LocalDate.now());
    }

    public static void updateMultas(){
        for(Multa objIterator : DAO.getMultaDAO().findMany()){
            if(!objIterator.getDataFim().isBefore(LocalDate.now())){
                DAO.getLeitorDAO().findByPk(objIterator.getIdUsuario()).desbloquearConta();
                DAO.getMultaDAO().delete(objIterator);
            }
        }
    }


    public static int getOrdemReserva(Leitor leitor, Livro livro){
        int contadora = 0;
        for (Reserva obj : DAO.getReservaDAO().findMany()){
            if(obj.getIdLivro().equals(livro.getIsbn())){
                contadora++;
                if(obj.getIdReservante().equals(leitor.getId())){
                    break;
                }
            }
        }
        return contadora;
    }

    public static boolean checarSeAReservaDoUsuarioOPermitePegarOLivro(Leitor leitor, Livro livro){
        int quantidade = Integer.parseInt(livro.getQuantidade());
        int ordemReserva = getOrdemReserva(leitor, livro);
        int numeroReservasLivro = numeroReservasLivro(livro);
        if(ordemReserva == 0) { // usuário não tem reserva
            return quantidade > numeroReservasLivro;
        }
        return (ordemReserva <= quantidade && ordemReserva > numeroReservasLivro);


    }

    public static int numeroReservasLivro(Livro livro){
        int contadora = 0;
        for (Reserva obj : DAO.getReservaDAO().findMany()){
            if(obj.getIdLivro().equals(livro.getIsbn())){
                contadora++;
            }
        }
        return contadora;
    }


/*    public void removerEmprestimo(){
        for (Emprestimo obj: DAO.getEmprestimoDAO().findMany()) {
            if(!checarSeHaAtrasoEmprestimo(obj)){
                DAO.getEmprestimoDAO().delete(obj);
            }
            else if(checarSeHaAtrasoLeitor(DAO.getLeitorDAO().findByPk(obj.getIdMutuario()))){
                // aplicar multa
                aplicarMulta(DAO.getLeitorDAO().findByPk(obj.getIdMutuario()));
            }

        }
    }*/

    public static int getQuantidadeLivrosEmprestados(){
        return DAO.getEmprestimoDAO().findMany().size();
    }


    /*
    atualizar reservas tem 3 etapas.

    1) ativar as reservas quando um livro novo ser liberado
    2) apagar as reservas esgostadas.

    */

    public static void ativarReservasLivros (){
        for (Livro livro: DAO.getLivroDAO().findMany()) {
            int unidadesLivro = Integer.parseInt(livro.getQuantidade());
            int contador = 0;
            for (Reserva reserva : DAO.getReservaDAO().findMany()) {
                if (contador == unidadesLivro) {
                    break;
                }
                if (reserva.getIdLivro().equals(livro.getIsbn())) {
                    reserva.setDataInicioReserva(LocalDate.now());
                    reserva.setDataFimReserva(reserva.getDataInicioReserva().plusDays(2));
                    contador++;
                }
            }
        }

    }

    public static void atualizarReservas() throws foraDeEstoqueException {
        for (Reserva reserva : DAO.getReservaDAO().findMany()) {
            if(reserva.getDataFimReserva().isAfter(LocalDate.now())){
                DAO.getReservaDAO().delete(reserva);
                DAO.getLeitorDAO().findByPk(reserva.getIdReserva()).adicionarUmaReserva();
            }
        }
    }


}
