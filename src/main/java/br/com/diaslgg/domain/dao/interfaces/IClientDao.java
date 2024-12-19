package br.com.diaslgg.domain.dao.interfaces;

import br.com.diaslgg.domain.entity.Client;

import java.sql.SQLException;
import java.util.List;

public interface IClientDao {

    Integer cadastrar(Client client) throws SQLException;

    Integer atualizar(Client client) throws SQLException;

    Client buscar(String code) throws SQLException;

    List<Client> buscarTodos() throws SQLException;

    Integer excluir(Client client) throws SQLException;
}
