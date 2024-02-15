package com.pbl.sibbiblioteca.controller.TelaEditarObjeto;

import com.pbl.sibbiblioteca.exceptions.naoEncontradoException;
import com.pbl.sibbiblioteca.exceptions.objetoInexistenteException;
import com.pbl.sibbiblioteca.model.entities.Administrador;
import com.pbl.sibbiblioteca.model.entities.Usuario;
import com.pbl.sibbiblioteca.model.entities.enums.Cargo;
import com.pbl.sibbiblioteca.utils.TelaController;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class TelaEdicaoUsuarioController {
    @javafx.fxml.FXML
    private TextField nomeField;
    @javafx.fxml.FXML
    private Button salvarNomeButton;
    @javafx.fxml.FXML
    private TextField enderecoField;

    public void setNomeField(String nomeField) {
        this.nomeField.setText(nomeField);
    }

    public void setEnderecoField(String enderecoField) {
        this.enderecoField.setText(enderecoField);
    }

    public void setTelefoneField(String telefoneField) {
        this.telefoneField.setText(telefoneField);
    }

    public void setUsuarioField(String usuarioField) {
        this.usuarioField.setText(usuarioField);
    }

    public void setSenhaField(String senhaField) {
        this.senhaField.setText(senhaField);
    }

    @javafx.fxml.FXML
    private Button salvarEnderecoButton;
    @javafx.fxml.FXML
    private TextField telefoneField;
    @javafx.fxml.FXML
    private Button salvarTelefoneButton;
    @javafx.fxml.FXML
    private TextField usuarioField;
    @javafx.fxml.FXML
    private Button salvarUsuarioButton;
    @javafx.fxml.FXML
    private TextField senhaField;
    @javafx.fxml.FXML
    private Button salvarSenhaButton;
    @javafx.fxml.FXML
    private Button cancelarButton;
    private Administrador administrador;
    private Usuario usuario;

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }



    public void setAdministrador(Administrador administrador) {
        this.administrador = administrador;
    }



    @javafx.fxml.FXML
    public void setSalvarNomeButton(ActionEvent actionEvent) {
        try {
            if(usuario.getCargo().equals(Cargo.LEITOR)){
                administrador.trocarNomeDoLeitor(usuario.getUsuario(), nomeField.getText());
            }
            else if(usuario.getCargo().equals(Cargo.BIBLIOTECARIO)){
                administrador.trocarNomeDoBibliotecario(usuario.getUsuario(), nomeField.getText());
            }
            else if(usuario.getCargo().equals(Cargo.ADMINISTRADOR)){
                administrador.trocarNomeDoAdministrador(usuario.getUsuario(), nomeField.getText());
            }
        }
        catch (naoEncontradoException | objetoInexistenteException e){
            TelaController.gerarAlertaErro("Erro", "Objeto não existe.");
        }
        TelaController.gerarAlertaOk("Operação realizada", "O nome do usuário foi trocado.");
    }

    @javafx.fxml.FXML
    public void setSalvarEnderecoButton(ActionEvent actionEvent) {
        try {
            if(usuario.getCargo().equals(Cargo.LEITOR)){
                administrador.trocarEnderecoDoLeitor(usuario.getUsuario(), enderecoField.getText());
            }
            else if(usuario.getCargo().equals(Cargo.BIBLIOTECARIO)){
                administrador.trocarEnderecoDoBibliotecario(usuario.getUsuario(), enderecoField.getText());
            }
            else if(usuario.getCargo().equals(Cargo.ADMINISTRADOR)){
                administrador.trocarEnderecoDoAdministrador(usuario.getUsuario(), enderecoField.getText());
            }
        }
        catch (naoEncontradoException | objetoInexistenteException e){
            TelaController.gerarAlertaErro("Erro", "Objeto não existe.");
        }
        TelaController.gerarAlertaOk("Operação realizada", "O endereço do usuário foi trocado.");
    }

    @javafx.fxml.FXML
    public void setSalvarTelefoneButton(ActionEvent actionEvent) {
        try {
            if(usuario.getCargo().equals(Cargo.LEITOR)){
                administrador.trocarTelefoneDoLeitor(usuario.getUsuario(), telefoneField.getText());
            }
            else if(usuario.getCargo().equals(Cargo.BIBLIOTECARIO)){
                administrador.trocarTelefoneDoBibliotecario(usuario.getUsuario(), telefoneField.getText());
            }
            else if(usuario.getCargo().equals(Cargo.ADMINISTRADOR)){
                administrador.trocarTelefoneDoAdministrador(usuario.getUsuario(), telefoneField.getText());
            }
        }
        catch (naoEncontradoException | objetoInexistenteException e){
            TelaController.gerarAlertaErro("Erro", "Objeto não existe.");
        }
        TelaController.gerarAlertaOk("Operação realizada", "O telefone do usuário foi trocado.");
    }

    @javafx.fxml.FXML
    public void setSalvarUsuarioButton(ActionEvent actionEvent) {
        try {
            if(usuario.getCargo().equals(Cargo.LEITOR)){
                administrador.trocarUsuarioDoLeitor(usuario.getUsuario(), usuarioField.getText());
            }
            else if(usuario.getCargo().equals(Cargo.BIBLIOTECARIO)){
                administrador.trocarUsuarioDoBibliotecario(usuario.getUsuario(), usuarioField.getText());
            }
            else if(usuario.getCargo().equals(Cargo.ADMINISTRADOR)){
                administrador.trocarUsuarioDoAdministrador(usuario.getUsuario(), usuarioField.getText());
            }
            TelaController.gerarAlertaOk("Operação realizada", "O usuário do usuário foi trocado.");
        }
        catch (naoEncontradoException | objetoInexistenteException e){
            TelaController.gerarAlertaErro("Erro", "Objeto não existe.");
        }
    }

    @javafx.fxml.FXML
    public void setSalvarSenhaButton(ActionEvent actionEvent) {
        try {
            if(usuario.getCargo().equals(Cargo.LEITOR)){
                administrador.trocarSenhaDoLeitor(usuario.getUsuario(), senhaField.getText());
            }
            else if(usuario.getCargo().equals(Cargo.BIBLIOTECARIO)){
                administrador.trocarSenhaDoBibliotecario(usuario.getUsuario(), senhaField.getText());
            }
            else if(usuario.getCargo().equals(Cargo.ADMINISTRADOR)){
                administrador.trocarSenhaDoAdministrador(usuario.getUsuario(), senhaField.getText());
            }
            TelaController.gerarAlertaOk("Operação realizada", "A senha do usuário foi trocada.");
        }
        catch (naoEncontradoException | objetoInexistenteException e){
            TelaController.gerarAlertaErro("Erro", "Objeto não existe.");
        }
    }

    @javafx.fxml.FXML
    public void setCancelarButton(ActionEvent actionEvent) {
        Stage stage = TelaController.retornarStage(actionEvent);
        stage.close();
    }
}
