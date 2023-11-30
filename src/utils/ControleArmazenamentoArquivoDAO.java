package utils;

import model.entities.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ControleArmazenamentoArquivoDAO {

    public static <T> String criarNomeDoArquivo(Class<T> classe) {
        if(classe == Administrador.class){
            return "dadosDAOAdministrador";
        }
        else if(classe == Bibliotecario.class){
            return "dadosDAOBibliotecario";
        }
        else if(classe == Emprestimo.class){
            return "dadosDAOEmprestimor";
        }
        else if(classe == Leitor.class){
            return "dadosDAOLeitor";
        }
        else if(classe == Livro.class){
            return "dadosDAOLivro";
        }
        else if(classe == Multa.class){
            return "dadosDAOMulta";
        }
        else if(classe == Reserva.class){
            return "dadosDAOReserva";
        }
        else{
            return null;
        }
    }

    public static <T> void guardarDados(List<T> lista, Class<T> classe) {
        String nomeDaPasta = criarNomeDoArquivo(classe);
        File dadosDAOPasta = new File("dadosDAO/" + nomeDaPasta);
        dadosDAOPasta.mkdirs();
        File dadosDAO = new File(dadosDAOPasta.getAbsolutePath(), nomeDaPasta+".dat");


        try {
            ObjectOutputStream obj = new ObjectOutputStream(new FileOutputStream(dadosDAO));
            obj.writeObject(lista);
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> ArrayList<T> carregarDados(Class <T> classe){
        String nomeDaPasta = criarNomeDoArquivo(classe);
        File dadosDAOPasta = new File("dadosDAO/" + nomeDaPasta);
        File dadosDAO = new File(dadosDAOPasta.getAbsolutePath(), nomeDaPasta+".dat");

        if(!dadosDAO.exists()){
            return new ArrayList<T>();
        }
        try{
            ObjectInputStream objLeitura = new ObjectInputStream(new FileInputStream(dadosDAO));
            return (ArrayList<T>) objLeitura.readObject();
        }
        catch (IOException | ClassNotFoundException e){
            e.printStackTrace();
        }
        return new ArrayList<T>();
    }
}
