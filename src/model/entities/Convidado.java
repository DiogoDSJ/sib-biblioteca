package model.entities;

import dao.DAO;
import exceptions.naoEncontradoException;

import java.util.List;

public class Convidado {
    public Convidado() {
    }

    public Livro findByIsbn(String isbn) throws naoEncontradoException {
        Livro resulBusca = DAO.getLivroDAO().findByIsbn(isbn);
        if(resulBusca == null) throw new naoEncontradoException("A busca não retornou em nada.");
        return resulBusca;
    }

    public List<Livro> findByTitulo(String titulo) throws naoEncontradoException{
        List<Livro> resulBusca = DAO.getLivroDAO().findByTitulo(titulo);
        if(resulBusca == null) throw new naoEncontradoException("A busca não retornou em nada.");
        return resulBusca;
    }

    public List<Livro> findByAutor(String autor) throws naoEncontradoException{
        List<Livro> resulBusca = DAO.getLivroDAO().findByAutor(autor);
        if(resulBusca == null) throw new naoEncontradoException("A busca não retornou em nada.");
        return resulBusca;
    }

    public List<Livro> findByCategoria(String categoria) throws naoEncontradoException{
        List<Livro> resulBusca = DAO.getLivroDAO().findByCategoria(categoria);
        if(resulBusca == null) throw new naoEncontradoException("A busca não retornou em nada.");
        return resulBusca;
    }

    public List<Livro> findByAnoDePublicao(String anoDePublicacao) throws naoEncontradoException{
        List<Livro> resulBusca = DAO.getLivroDAO().findByAnoDePublicao(anoDePublicacao);
        if(resulBusca == null) throw new naoEncontradoException("A busca não retornou em nada.");
        return resulBusca;
    }

    public List<Livro> findByEditora(String editora)throws naoEncontradoException{
        List<Livro> resulBusca = DAO.getLivroDAO().findByEditora(editora);
        if(resulBusca == null) throw new naoEncontradoException("A busca não retornou em nada.");
        return resulBusca;
    }
}
