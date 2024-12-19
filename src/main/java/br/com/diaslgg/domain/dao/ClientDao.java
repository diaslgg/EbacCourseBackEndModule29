package br.com.diaslgg.domain.dao;

import br.com.diaslgg.domain.dao.interfaces.IClientDao;
import br.com.diaslgg.domain.entity.Client;
import br.com.diaslgg.infrastructure.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClientDao implements IClientDao {

    @Override
    public Integer cadastrar(Client client) throws SQLException {
        Connection connection = null;
        PreparedStatement stm = null;
        try {
            connection = ConnectionFactory.getConnection();
            String sql = getSqlInsert();
            stm = connection.prepareStatement(sql);
            adicionarParametrosInsert(stm, client);
            return stm.executeUpdate();
        } finally {
            closeConnection(connection, stm, null);
        }
    }


    @Override
    public Integer atualizar(Client client) throws SQLException {
        Connection connection = null;
        PreparedStatement stm = null;
        try {
            connection = ConnectionFactory.getConnection();
            String sql = getSqlUpdate();
            stm = connection.prepareStatement(sql);
            adicionarParametrosUpdate(stm, client);
            return stm.executeUpdate();
        } finally {
            closeConnection(connection, stm, null);
        }
    }

    @Override
    public Client buscar(String codigo) throws SQLException {
        Connection connection = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        Client client = null;
        try {
            connection = ConnectionFactory.getConnection();
            String sql = getSqlSelect();
            stm = connection.prepareStatement(sql);
            adicionarParametrosSelect(stm, codigo);
            rs = stm.executeQuery();

            if (rs.next()) {
                client = new Client();
                Long id = rs.getLong("id");
                String name = rs.getString("name");
                String cd = rs.getString("code");
                client.setId(id);
                client.setName(name);
                client.setCode(cd);
            }
        } finally {
            closeConnection(connection, stm, rs);
        }
        return client;
    }

    @Override
    public Integer excluir(Client client) throws SQLException {
        Connection connection = null;
        PreparedStatement stm = null;
        try {
            connection = ConnectionFactory.getConnection();
            String sql = getSqlDelete();
            stm = connection.prepareStatement(sql);
            adicionarParametrosDelete(stm, client);
            return stm.executeUpdate();
        } finally {
            closeConnection(connection, stm, null);
        }
    }

    @Override
    public List<Client> buscarTodos() throws SQLException {
        Connection connection = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        List<Client> list = new ArrayList<>();
        Client client = null;
        try {
            connection = ConnectionFactory.getConnection();
            String sql = getSqlSelectAll();
            stm = connection.prepareStatement(sql);
            rs = stm.executeQuery();

            while (rs.next()) {
                client = new Client();
                Long id = rs.getLong("id");
                String name = rs.getString("name");
                String cd = rs.getString("code");
                client.setId(id);
                client.setName(name);
                client.setCode(cd);
                list.add(client);
            }
        } finally {
            closeConnection(connection, stm, rs);
        }
        return list;
    }

    private String getSqlInsert() {
        return "INSERT INTO tb_clients (id, name, code) " +
                "VALUES (nextval('sq_client'),?,?)";
    }

    private void adicionarParametrosInsert(PreparedStatement stm, Client client) throws SQLException {
        stm.setString(1, client.getName());
        stm.setString(2, client.getCode());
    }

    private String getSqlUpdate() {
        return "UPDATE tb_clients " +
                "SET name = ?, code = ? " +
                "WHERE id = ?";
    }

    private void adicionarParametrosUpdate(PreparedStatement stm, Client cliente) throws SQLException {
        stm.setString(1, cliente.getName());
        stm.setString(2, cliente.getCode());
        stm.setLong(3, cliente.getId());
    }

    private String getSqlDelete() {
        return "DELETE FROM tb_clients " +
                "WHERE code = ?";
    }

    private void adicionarParametrosDelete(PreparedStatement stm, Client client) throws SQLException {
        stm.setString(1, client.getCode());
    }

    private String getSqlSelect() {
        return "SELECT * FROM tb_clients " +
                "WHERE code = ?";
    }

    private void adicionarParametrosSelect(PreparedStatement stm, String code) throws SQLException {
        stm.setString(1, code);
    }

    private String getSqlSelectAll() {
        return "SELECT * FROM tb_clients";
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
