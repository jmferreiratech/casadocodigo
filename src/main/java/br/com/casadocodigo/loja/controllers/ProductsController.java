package br.com.casadocodigo.loja.controllers;

import br.com.casadocodigo.loja.daos.ProductDAO;
import br.com.casadocodigo.loja.models.BookType;
import br.com.casadocodigo.loja.models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.transaction.Transactional;
import javax.validation.Valid;

@Controller
@Transactional
@RequestMapping("/produtos")
public class ProductsController {

    @Autowired
    private ProductDAO productDAO;

    @RequestMapping(method = RequestMethod.POST)
    public ModelAndView save(@Valid Product product, BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return form(product);
        }
        productDAO.save(product);
        redirectAttributes.addFlashAttribute("sucesso", "Produto cadastrado com sucesso");
        return new ModelAndView("redirect:/produtos");
    }

    @RequestMapping("/form")
    public ModelAndView form(Product product) {
        ModelAndView mv = new ModelAndView("products/form");
        mv.addObject("types", BookType.values());
        return mv;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView list() {
        ModelAndView mv = new ModelAndView("products/list");
        mv.addObject("products", productDAO.list());
        return mv;
    }
}
