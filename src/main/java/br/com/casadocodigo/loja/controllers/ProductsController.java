package br.com.casadocodigo.loja.controllers;

import br.com.casadocodigo.loja.daos.ProductDAO;
import br.com.casadocodigo.loja.infra.FileSaver;
import br.com.casadocodigo.loja.models.BookType;
import br.com.casadocodigo.loja.models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
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
    @Autowired
    private FileSaver fileSaver;

    @RequestMapping(method = RequestMethod.POST)
    @CacheEvict(value = "books", allEntries = true)
    public ModelAndView save(MultipartFile summary, @Valid Product product, BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return form(product);
        }
        String webPath = fileSaver.write("uploaded-image", summary);
        product.setSummaryPath(webPath);
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
    @Cacheable(value = "books")
    public ModelAndView list() {
        ModelAndView mv = new ModelAndView("products/list");
        mv.addObject("products", productDAO.list());
        return mv;
    }

    @RequestMapping("/{id}")
    public ModelAndView show(@PathVariable("id") Integer id) {
        ModelAndView modelAndView = new ModelAndView("products/show");
        Product product = productDAO.find(id);
        modelAndView.addObject("product", product);
        return modelAndView;
    }
}
