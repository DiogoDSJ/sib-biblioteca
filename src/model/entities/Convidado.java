package model.entities;

import dao.DAO;
import exceptions.naoEncontradoException;

import java.io.Serializable;
import java.util.List;

/**
 * Classe convidado que será utilizado pelos usuários sem cadastro para o método de pesquisa.
 */
public class Convidado implements Serializable {
    public Convidado() {
    }

    /**
     * Busca um livro no DAO de livro um livro pelo atributo ISBN do livro e retorna o objeto Livro encontrado.
     * @param isbn ISBN do livro à ser buscado.
     * @return retorna o objeto livro que contém o paramêtro da busca.
     * @throws naoEncontradoException Caso a busca não retorne em nada.
     */
    public Livro findByIsbn(String isbn) throws naoEncontradoException {
        Livro resulBusca = DAO.getLivroDAO().findByIsbn(isbn);
        if(resulBusca == null) throw new naoEncontradoException("A busca não retornou em nada.");
        return resulBusca;
    }
    /**
     * Busca livros no DAO de livro um livro tendo como paramêtro de busca o atributo titulo do livro e retorna uma lista de Livros com
     * esse paramêtro semelhante.
     * @param titulo titulo do livro à ser buscado.
     * @return retorna uma lista de livros que contenham o paramêtro da busca.
     * @throws naoEncontradoException Caso a busca não retorne em nada.
     */
    public List<Livro> findByTitulo(String titulo) throws naoEncontradoException{
        List<Livro> resulBusca = DAO.getLivroDAO().findByTitulo(titulo);
        if(resulBusca == null) throw new naoEncontradoException("A busca não retornou em nada.");
        return resulBusca;
    }
    /**
     * Busca livros no DAO de livro um livro tendo como paramêtro de busca o atributo autor do livro e retorna uma lista de Livros com
     * esse paramêtro semelhante.
     * @param autor autor do livro à ser buscado.
     * @return retorna uma lista de livros que contenham o paramêtro da busca.
     * @throws naoEncontradoException Caso a busca não retorne em nada.
     */
    public List<Livro> findByAutor(String autor) throws naoEncontradoException{
        List<Livro> resulBusca = DAO.getLivroDAO().findByAutor(autor);
        if(resulBusca == null) throw new naoEncontradoException("A busca não retornou em nada.");
        return resulBusca;
    }
    /**
     * Busca livros no DAO de livro um livro tendo como paramêtro de busca o atributo categoria do livro e retorna uma lista de Livros com
     * esse paramêtro semelhante.
     * @param categoria categoria do livro à ser buscado.
     * @return retorna uma lista de livros que contenham o paramêtro da busca.
     * @throws naoEncontradoException Caso a busca não retorne em nada.
     */
    public List<Livro> findByCategoria(String categoria) throws naoEncontradoException{
        List<Livro> resulBusca = DAO.getLivroDAO().findByCategoria(categoria);
        if(resulBusca == null) throw new naoEncontradoException("A busca não retornou em nada.");
        return resulBusca;
    }
    /**
     * Busca livros no DAO de livro um livro tendo como paramêtro de busca o atributo anoDePublicacao do livro e retorna uma lista de Livros com
     * esse paramêtro semelhante.
     * @param anoDePublicacao anoDePublicacao do livro à ser buscado.
     * @return retorna uma lista de livros que contenham o paramêtro da busca.
     * @throws naoEncontradoException Caso a busca não retorne em nada.
     */
    public List<Livro> findByAnoDePublicao(String anoDePublicacao) throws naoEncontradoException{
        List<Livro> resulBusca = DAO.getLivroDAO().findByAnoDePublicao(anoDePublicacao);
        if(resulBusca == null) throw new naoEncontradoException("A busca não retornou em nada.");
        return resulBusca;
    }
    /**
     * Busca livros no DAO de livro um livro tendo como paramêtro de busca o atributo editora do livro e retorna uma lista de Livros com
     * esse paramêtro semelhante.
     * @param editora editora do livro à ser buscado.
     * @return retorna uma lista de livros que contenham o paramêtro da busca.
     * @throws naoEncontradoException Caso a busca não retorne em nada.
     */
    public List<Livro> findByEditora(String editora)throws naoEncontradoException{
        List<Livro> resulBusca = DAO.getLivroDAO().findByEditora(editora);
        if(resulBusca == null) throw new naoEncontradoException("A busca não retornou em nada.");
        return resulBusca;
    }
}
