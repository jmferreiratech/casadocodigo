package br.com.casadocodigo.loja.models;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

@Component
@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class ShoppingCart {

    private Map<ShoppingItem, Integer> items = new LinkedHashMap<>();

    public void add(ShoppingItem item) {
        items.put(item, getQuantity(item) + 1);
    }

    public Collection<ShoppingItem> getList() {
        return items.keySet();
    }

    public BigDecimal getTotal() {
        BigDecimal total = BigDecimal.ZERO;
        for (ShoppingItem item : items.keySet()) {
            total = total.add(getTotal(item));
        }
        return total;
    }

    public BigDecimal getTotal(ShoppingItem item) {
        return item.getTotal(getQuantity(item));
    }

    public Integer getQuantity() {
        return items.values().stream().reduce(0, (next, accumulator) -> next + accumulator);
    }

    public Integer getQuantity(ShoppingItem item) {
        if (!items.containsKey(item)) {
            items.put(item, 0);
        }
        return items.get(item);
    }

    public void remove(ShoppingItem shoppingItem) {
        items.remove(shoppingItem);
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }
}
