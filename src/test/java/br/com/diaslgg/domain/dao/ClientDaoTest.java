package br.com.diaslgg.domain.dao;

import br.com.diaslgg.domain.dao.interfaces.IClientDao;
import br.com.diaslgg.domain.entity.Client;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ClientDaoTest {

    private IClientDao clientDao;

    @BeforeEach
    void setUp() {
        clientDao = new ClientDao();
    }

    @Test
    void cadastrarTest() throws Exception {

        Client client = new Client("Luis Dias", "1050");
        Integer countCad = clientDao.cadastrar(client);
        assertEquals(1, (int) countCad);

        Client clienteDb = clientDao.buscar("1050");
        assertNotNull(clienteDb);
        assertEquals(client.getCode(), clienteDb.getCode());
        assertEquals(client.getName(), clienteDb.getName());

        Integer countDel = clientDao.excluir(clienteDb);
        assertEquals(1, (int) countDel);
    }

    @Test
    public void buscarTest() throws Exception {

        Client client = new Client("Théo Dias", "1051");
        Integer countCad = clientDao.cadastrar(client);
        assertEquals(1,countCad);

        Client clientDb = clientDao.buscar("1051");
        assertNotNull(clientDb);
        assertEquals(client.getCode(), clientDb.getCode());
        assertEquals(client.getName(), clientDb.getName());

        Integer countDel = clientDao.excluir(clientDb);
        assertEquals(1,countDel);
    }

    @Test
    public void excluirTest() throws Exception {

        Client client = new Client("Luísa Salvati", "1052");
        Integer countCad = clientDao.cadastrar(client);
        assertEquals(1, countCad);

        Client clienteBD = clientDao.buscar("1052");
        assertNotNull(clienteBD);
        assertEquals(client.getCode(), clienteBD.getCode());
        assertEquals(client.getName(), clienteBD.getName());

        Integer countDel = clientDao.excluir(clienteBD);
        assertEquals(1, countDel);
    }

    @Test
    public void buscarTodosTest() throws Exception {

        Client client1 = new Client("Luis Dias", "1051");
        Integer countCad = clientDao.cadastrar(client1);
        assertEquals(1, countCad);

        Client client2 = new Client("Théo de Pasquali", "1052");
        Integer countCad2 = clientDao.cadastrar(client2);
        assertEquals(1, countCad2);

        List<Client> listClients = clientDao.buscarTodos();
        assertNotNull(listClients);
        assertEquals(2, listClients.size());

        int countDel = 0;
        for (Client cli : listClients) {
            clientDao.excluir(cli);
            countDel++;
        }
        assertEquals(listClients.size(), countDel);

        listClients = clientDao.buscarTodos();
        assertEquals(0, listClients.size());

    }

    @Test
    public void atualizarTest() throws Exception {

        Client client = new Client("Luis Dias", "1051");
        Integer countCad = clientDao.cadastrar(client);
        assertEquals(1, countCad);

        Client clientDb = clientDao.buscar("1051");
        assertNotNull(clientDb);
        assertEquals(client.getCode(), clientDb.getCode());
        assertEquals(client.getName(), clientDb.getName());

        clientDb.setCode("1000");
        clientDb.setName("Luis Giraldes");
        Integer countUpdate = clientDao.atualizar(clientDb);
        assertEquals(1, countUpdate);

        Client clienteDb2 = clientDao.buscar("1051");
        assertNull(clienteDb2);

        Client clientDb3 = clientDao.buscar("1000");
        assertNotNull(clientDb3);
        assertEquals(clientDb.getId(), clientDb3.getId());
        assertEquals(clientDb.getCode(), clientDb3.getCode());
        assertEquals(clientDb.getName(), clientDb3.getName());

        List<Client> listClients = clientDao.buscarTodos();
        for (Client cli : listClients) {
            clientDao.excluir(cli);
        }
    }
}