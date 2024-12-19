package br.com.diaslgg.domain.dao.interfaces;

import br.com.diaslgg.domain.entity.Product;

import java.sql.SQLException;
import java.util.List;

public interface IProductDao {

    Integer cadastrar(Product product) throws SQLException;

    Integer atualizar(Product product) throws SQLException;

    Product buscar(String code) throws SQLException;

    List<Product> buscarTodos() throws SQLException;

    Integer excluir(Product product) throws SQLException;
}
