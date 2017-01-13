package br.com.casadocodigo.loja.daos;

import br.com.casadocodigo.loja.builders.ProductBuilder;
import br.com.casadocodigo.loja.conf.DataSourceConfigurationTest;
import br.com.casadocodigo.loja.conf.JPAConfiguration;
import br.com.casadocodigo.loja.models.BookType;
import br.com.casadocodigo.loja.models.Product;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("test")
@ContextConfiguration(classes = {DataSourceConfigurationTest.class, ProductDAO.class, JPAConfiguration.class})
public class ProductDAOTest {

    @Autowired
    private ProductDAO dao;

    @Transactional
    @Test
    public void shouldSumAllPricesOfEachBookPerType() {
        List<Product> printedBooks = ProductBuilder.newProduct(BookType.PRINTED, BigDecimal.TEN).more(4).buildAll();
        printedBooks.forEach(dao::save);
        List<Product> ebooks = ProductBuilder.newProduct(BookType.EBOOK, BigDecimal.TEN).more(4).buildAll();
        ebooks.forEach(dao::save);

        BigDecimal value = dao.sumPricesPerType(BookType.PRINTED);
        Assert.assertEquals(new BigDecimal(50).setScale(2), value);
    }
}