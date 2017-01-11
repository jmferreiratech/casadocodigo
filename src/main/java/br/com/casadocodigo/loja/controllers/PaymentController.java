package br.com.casadocodigo.loja.controllers;

import br.com.casadocodigo.loja.models.PaymentData;
import br.com.casadocodigo.loja.models.ShoppingCart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import java.math.BigDecimal;
import java.util.concurrent.Callable;

@Controller
@RequestMapping("/payment")
public class PaymentController {

    @Autowired
    private ShoppingCart shoppingCart;
    @Autowired
    private RestTemplate restTemplate;

    @RequestMapping(value = "checkout", method = RequestMethod.POST)
    public Callable<ModelAndView> checkout() {
        return () -> {
            BigDecimal total = shoppingCart.getTotal();
            String uriToPay = "http://book-payment.herokuapp.com/payment";
            try {
                restTemplate.postForObject(uriToPay, new PaymentData(total), String.class);
                return new ModelAndView("redirect:/payment/success");
            } catch (HttpClientErrorException exception) {
                return new ModelAndView("redirect:/payment/error");
            }
        };
    }

    @RequestMapping("/success")
    public String success() {
        return "/payment/success";
    }

    @RequestMapping("/error")
    public String error() {
        return "/payment/error";
    }
}
