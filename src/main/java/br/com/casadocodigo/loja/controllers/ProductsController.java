package br.com.casadocodigo.loja.controllers;

import br.com.casadocodigo.loja.daos.ProductDAO;
import br.com.casadocodigo.loja.models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.transaction.Transactional;

@Controller
@Transactional
public class ProductsController {

    @Autowired
    private ProductDAO productDAO;

    @RequestMapping("/produtos")
    @Transactional
    public String save(Product product) {
        productDAO.save(product);
        return "products/ok";
    }

    @RequestMapping("/produtos/form")
    public String form() {
        return "products/form";
    }
}
