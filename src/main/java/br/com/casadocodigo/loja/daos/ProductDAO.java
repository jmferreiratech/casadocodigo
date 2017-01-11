package br.com.casadocodigo.loja.daos;

import br.com.casadocodigo.loja.models.Product;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class ProductDAO {

    @PersistenceContext
    private EntityManager manager;

    public void save(Product product) {
        manager.persist(product);
    }

    public List<Product> list() {
        return manager.createQuery("select distinct(p) from Product p join fetch p.prices",
                Product.class).getResultList();
    }

    public Product find(Integer id) {
        TypedQuery<Product> query = manager.createQuery(
                "select distinct(p) from Product p join fetch p.prices where p.id=:id", Product.class);
        return query.setParameter("id", id).getSingleResult();
    }
}
