package com.pbl.sibbiblioteca.utils;

import com.pbl.sibbiblioteca.model.entities.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe que contém as funções estáticas de carregar e salvar os dados do programa em arquivos.
 */
public class ControleArmazenamentoArquivoDAO {

    /**
     * Lê a classe recebida e envia o nome do arquivo de acordo com a classe.
     * @param classe Classe recebida.
     * @return Nome do arquivo.
     * @param <T> Tipo da classe.
     */
    public static <T> String criarNomeDoArquivo(Class<T> classe) {
        if(classe == Administrador.class){
            return "dadosDAOAdministrador";
        }
        else if(classe == Bibliotecario.class){
            return "dadosDAOBibliotecario";
        }
        else if(classe == Emprestimo.class){
            return "dadosDAOEmprestimo";
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

    /**
     * Função que transforma os dados da memória volátil em binário e salva no arquivo.
     * @param lista Lista que contém objetos do tipo genérico T.
     * @param classe Classe do tipo T. Paramêtro utilizado para designar o nome do arquivo.
     * @param <T> Classe do dado a ser guardado.
     */
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

    /**
     * Função que lê os dados da memória não-volátil e carrega para a memória volátil.
     * @param classe Classe do tipo T. Paramêtro utilizado para designar o nome do arquivo.
     * @return Dados da memória não-volátil em forma volátil.
     * @param <T> Classe do dado a ser guardado.
     */
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
