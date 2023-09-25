package model.entities;

import dao.DAO;
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
                leitor.bloquearConta();
                return true;
            }
        }
        if(DAO.getMultaDAO().findByIdMutuario(leitor.getId()) == null){
            leitor.desbloquearConta();
        }
        return false;
    }

    public static boolean checarSeHaAtrasoEmprestimo(Emprestimo emprestimo){
        return emprestimo.getDataFim().isAfter(LocalDate.now());
    }

    public static void updateMultas(){
        for(Multa objIterator : DAO.getMultaDAO().findMany()){
            if(!objIterator.getDataFim().isBefore(LocalDate.now())){
                DAO.getMultaDAO().delete(objIterator);
            }
        }
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


}
