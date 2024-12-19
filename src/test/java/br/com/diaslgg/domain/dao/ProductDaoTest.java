package br.com.diaslgg.domain.dao;

import br.com.diaslgg.domain.dao.interfaces.IProductDao;
import br.com.diaslgg.domain.entity.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ProductDaoTest {

    private IProductDao productDao;

    @BeforeEach
    void setUp(){
        productDao = new ProductDao();
    }

    @Test
    void cadastrarTest() throws Exception {

        Product product = new Product("Martelo", "50", 2000L);
        Integer countCad = productDao.cadastrar(product);
        assertEquals(1, (int) countCad);

        Product productDb = productDao.buscar("50");
        assertNotNull(productDb);
        assertEquals(product.getCode(), productDb.getCode());
        assertEquals(product.getName(), productDb.getName());
        assertEquals(product.getQuantity(), productDb.getQuantity());

        Integer countDel = productDao.excluir(productDb);
        assertEquals(1, (int) countDel);
    }

    @Test
    public void buscarTest() throws Exception {

        Product product = new Product("Prego", "51", 1000000L);
        Integer countCad = productDao.cadastrar(product);
        assertEquals(1,countCad);

        Product productDb = productDao.buscar("51");
        assertNotNull(productDb);
        assertEquals(product.getCode(), productDb.getCode());
        assertEquals(product.getName(), productDb.getName());
    assertEquals(product.getQuantity(), productDb.getQuantity());

        Integer countDel = productDao.excluir(productDb);
        assertEquals(1,countDel);
    }

    @Test
    public void excluirTest() throws Exception {

        Product product = new Product("Serrote", "52", 49L);
        Integer countCad = productDao.cadastrar(product);
        assertEquals(1, countCad);

        Product productDb = productDao.buscar("52");
        assertNotNull(productDb);
        assertEquals(product.getCode(), productDb.getCode());
        assertEquals(product.getName(), productDb.getName());
        assertEquals(product.getQuantity(), productDb.getQuantity());

        Integer countDel = productDao.excluir(productDb);
        assertEquals(1, countDel);
    }

    @Test
    public void buscarTodosTest() throws Exception {

        Product product1 = new Product("Martelo", "50", 2000L);
        Integer countCad = productDao.cadastrar(product1);
        assertEquals(1, countCad);

        Product product2 = new Product("Prego", "51", 1000000L);
        Integer countCad2 = productDao.cadastrar(product2);
        assertEquals(1, countCad2);

        List<Product> listProducts = productDao.buscarTodos();
        assertNotNull(listProducts);
        assertEquals(2, listProducts.size());

        int countDel = 0;
        for (Product prod : listProducts) {
            productDao.excluir(prod);
            countDel++;
        }
        assertEquals(listProducts.size(), countDel);

        listProducts = productDao.buscarTodos();
        assertEquals(0, listProducts.size());
    }

    @Test
    public void atualizarTest() throws Exception {

        Product product1 = new Product("Martelo", "50", 2000L);
        Integer countCad = productDao.cadastrar(product1);
        assertEquals(1, countCad);

        Product productDb = productDao.buscar("50");
        assertNotNull(productDb);
        assertEquals(product1.getCode(), productDb.getCode());
        assertEquals(product1.getName(), productDb.getName());
        assertEquals(product1.getQuantity(), productDb.getQuantity());

        productDb.setCode("1050");
        productDb.setName("Materlo Importado");
        Integer countUpdate = productDao.atualizar(productDb);
        assertEquals(1, countUpdate);

        Product productDb2 = productDao.buscar("50");
        assertNull(productDb2);

        Product productDb3 = productDao.buscar("1050");
        assertNotNull(productDb3);
        assertEquals(productDb.getId(), productDb3.getId());
        assertEquals(productDb.getCode(), productDb3.getCode());
        assertEquals(productDb.getName(), productDb3.getName());
    assertEquals(productDb.getQuantity(), productDb3.getQuantity());

        List<Product> listProducts = productDao.buscarTodos();
        for (Product prod : listProducts) {
            productDao.excluir(prod);
        }
    }

}