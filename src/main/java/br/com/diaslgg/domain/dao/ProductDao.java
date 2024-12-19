package br.com.diaslgg.domain.dao;

import br.com.diaslgg.domain.dao.interfaces.IProductDao;
import br.com.diaslgg.domain.entity.Product;
import br.com.diaslgg.infrastructure.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductDao implements IProductDao {

    @Override
    public Integer cadastrar(Product product) throws SQLException {
        Connection connection = null;
        PreparedStatement stm = null;
        try {
            connection = ConnectionFactory.getConnection();
            String sql = getSqlInsert();
            stm = connection.prepareStatement(sql);
            adicionarParametrosInsert(stm, product);
            return stm.executeUpdate();
        } finally {
            closeConnection(connection, stm, null);
        }
    }

    @Override
    public Integer atualizar(Product product) throws SQLException {
        Connection connection = null;
        PreparedStatement stm = null;
        try {
            connection = ConnectionFactory.getConnection();
            String sql = getSqlUpdate();
            stm = connection.prepareStatement(sql);
            adicionarParametrosUpdate(stm, product);
            return stm.executeUpdate();
        } finally {
            closeConnection(connection, stm, null);
        }
    }

    @Override
    public Product buscar(String codigo) throws SQLException {
        Connection connection = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        Product product = null;
        try {
            connection = ConnectionFactory.getConnection();
            String sql = getSqlSelect();
            stm = connection.prepareStatement(sql);
            adicionarParametrosSelect(stm, codigo);
            rs = stm.executeQuery();

            if (rs.next()) {
                product = new Product();
                Long id = rs.getLong("id");
                String name = rs.getString("name");
                String cd = rs.getString("code");
                Long quantity = rs.getLong("quantity");
                product.setId(id);
                product.setName(name);
                product.setCode(cd);
                product.setQuantity(quantity);
            }
        } finally {
            closeConnection(connection, stm, rs);
        }
        return product;
    }

    @Override
    public Integer excluir(Product product) throws SQLException {
        Connection connection = null;
        PreparedStatement stm = null;
        try {
            connection = ConnectionFactory.getConnection();
            String sql = getSqlDelete();
            stm = connection.prepareStatement(sql);
            adicionarParametrosDelete(stm, product);
            return stm.executeUpdate();
        } finally {
            closeConnection(connection, stm, null);
        }
    }

    @Override
    public List<Product> buscarTodos() throws SQLException {
        Connection connection = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        List<Product> list = new ArrayList<>();
        Product product = null;
        try {
            connection = ConnectionFactory.getConnection();
            String sql = getSqlSelectAll();
            stm = connection.prepareStatement(sql);
            rs = stm.executeQuery();

            while (rs.next()) {
                product = new Product();
                Long id = rs.getLong("id");
                String name = rs.getString("name");
                String cd = rs.getString("code");
                Long quantity = rs.getLong("quantity");
                product.setId(id);
                product.setName(name);
                product.setCode(cd);
                product.setQuantity(quantity);
                list.add(product);
            }
        } finally {
            closeConnection(connection, stm, rs);
        }
        return list;
    }

    private String getSqlInsert() {
        return "INSERT INTO tb_products (id, name, code, quantity) " +
                "VALUES (nextval('sq_product'),?,?,?)";
    }

    private void adicionarParametrosInsert(PreparedStatement stm, Product product) throws SQLException {
        stm.setString(1, product.getName());
        stm.setString(2, product.getCode());
        stm.setLong(3, product.getQuantity());
    }

    private String getSqlUpdate() {
        return "UPDATE tb_products " +
                "SET name = ?, code = ?, quantity = ? " +
                "WHERE id = ?";
    }

    private void adicionarParametrosUpdate(PreparedStatement stm, Product product) throws SQLException {
        stm.setString(1, product.getName());
        stm.setString(2, product.getCode());
        stm.setLong(3, product.getQuantity());
        stm.setLong(4, product.getId());
    }

    private String getSqlDelete() {
        return "DELETE FROM tb_products " +
                "WHERE code = ?";
    }

    private void adicionarParametrosDelete(PreparedStatement stm, Product product) throws SQLException {
        stm.setString(1, product.getCode());
    }

    private String getSqlSelect() {
        return "SELECT * FROM tb_products " +
                "WHERE code = ?";
    }

    private void adicionarParametrosSelect(PreparedStatement stm, String code) throws SQLException {
        stm.setString(1, code);
    }

    private String getSqlSelectAll() {
        return "SELECT * FROM tb_products";
    }

    private void closeConnection(Connection connection, PreparedStatement stm, ResultSet rs) {
        try {
            if (rs != null && !rs.isClosed()) {
                rs.close();
            }
            if (stm != null && !stm.isClosed()) {
                stm.close();
            }
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
    }
}
